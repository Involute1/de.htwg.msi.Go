package de.htwg.scala.kafka

import de.htwg.msi.controller.ExternalDSLParser
import de.htwg.msi.util.SparkUtil
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util
import java.util.Properties
import scala.collection.JavaConverters._
import scala.util.control.Breaks.{break, breakable}

object SgfDataKafkaConsumer extends App {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")

  val consumer = new KafkaConsumer[String, String](props)
  val TOPIC = "GameData"

  consumer.subscribe(util.Collections.singletonList(TOPIC))

  val spark = SparkUtil.getSparkSession
  spark.sparkContext.setLogLevel("WARN")
  val parser = new ExternalDSLParser

  while (true) {
    val records = consumer.poll(java.time.Duration.ofSeconds(1))
    val recordValues = records.asScala.toList.map(record => record.value())

    import spark.implicits._
    breakable {
      if(recordValues.isEmpty) break

      val sgfDataSet = spark.createDataset(recordValues)
        .map(content => parser.parseDSL(content))
        .filter(parsedFile => parsedFile.isLeft)
        .map(parsedFile => parsedFile.left.get)

      val basicStatsMoves = sgfDataSet
        .map(sgfData => sgfData.moves.size)
        .summary()

      val boardSizeUsage = sgfDataSet.map(sgfData => (sgfData.gameData.size, 1))
        .groupBy($"_1").count()
        .sort($"count".desc)

      val whitePlayers = sgfDataSet.map(sgfData => sgfData.gameData.pw)
      val blackPlayers = sgfDataSet.map(sgfData => sgfData.gameData.pb)
      val playerMatchCount = blackPlayers
        .union(whitePlayers)
        .map(player => (player, 1))
        .groupBy($"_1").count()
        .sort($"count".desc)


      basicStatsMoves.show()
      boardSizeUsage.show()
      playerMatchCount.show()
    }

//    println("Polling..")
//    val records = consumer.poll(100)
//    for (record <- records.asScala) {
//      println(record)
//    }
  }
}
