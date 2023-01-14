package de.htwg.msi.controller

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.{DiscoverySupport, Producer}
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.ConfigFactory
import de.htwg.msi.model.SgfData
import de.htwg.msi.util.{Constants, FileHandling}
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import java.nio.file.{FileSystems, Files, Path}
import scala.concurrent.Future
import scala.jdk.CollectionConverters.CollectionHasAsScala


case class SgfDataStream(dir: String, materializer: Materializer) {

  def toKafka: Future[Done] = {
    val sgfFiles = FileHandling.getFileListFrom(dir)

    val sgfFileSource: Source[Path, NotUsed] = Source(sgfFiles)

    val sgfFileTextExtraction: Flow[Path, String, NotUsed] = Flow.fromFunction(path => Files.readString(path))


    val discoveryConfigSection =
      s"""
            // #discovery-service
            discovery-producer: $${akka.kafka.producer} {
              service-name = "kafka1"
              resolve-timeout = 10 ms
            }
            // #discovery-service
            akka.discovery.method = config
            akka.discovery.config.services = {
              kafka1 = {
                endpoints = [
                  { host = "localhost", port = 9092 }
                ]
              }
            }
            """
    val config = ConfigFactory
      .parseString(discoveryConfigSection)
      .withFallback(ConfigFactory.load())
      .resolve()
    implicit val actorSystem = ActorSystem("test", config)
    val producerConfig = config.getConfig("discovery-producer")
    val settings = ProducerSettings(producerConfig, new StringSerializer, new StringSerializer)
      .withEnrichAsync(DiscoverySupport.producerBootstrapServers(producerConfig))


    sgfFileSource
      .via(sgfFileTextExtraction)
      .map(value => new ProducerRecord[String, String]("GameData", value))
      .runWith(Producer.plainSink(settings))(materializer)
  }


}