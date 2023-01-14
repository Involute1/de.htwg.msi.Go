package de.htwg.msi.controller

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.Sink
import de.htwg.msi.model.SgfData
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class SgfDataStreamTest extends AnyWordSpec {
  "SgfDataStream".should({
    val dir = "src/test/resources/sgf"
    implicit val materializer = Materializer.createMaterializer(ActorSystem("SgfStreamTest"))
    val testDataStream = SgfDataStream(dir, materializer)
    val result = Await.result(testDataStream.toKafka, Duration.Inf)

    "return Done when kafka is running correctly" in {
      result.toString mustBe "Done"
    }
  })
}
