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
    val testParser = new ExternalDSLParser
    val dir = "src/test/resources/sgf"
    val sinkToFutureSeq = Sink.seq[SgfData]
    implicit val materializer = Materializer.createMaterializer(ActorSystem("SgfStreamTest"))
    val testDataStream = SgfDataStream(testParser, dir, sinkToFutureSeq, materializer)
    val result = Await.result(testDataStream.getResult, Duration.Inf)

    "read one valid sgfData object from a path with one valid, one invalid and a non sgf-file" in {
      result.toString mustBe "Done"
    }
  })
}
