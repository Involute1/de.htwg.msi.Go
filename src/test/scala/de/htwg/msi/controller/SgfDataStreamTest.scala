package de.htwg.msi.controller

import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.Sink
import de.htwg.msi.model.{Move, SgfData, SgfGameData}
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
      result.size mustBe 1
    }

    "input of valid sgf-file is correct" in {
      result.head mustBe SgfData(SgfGameData(19, "MIss", "petgo3"), List(Move("B", "pp"), Move("W", "dp"), Move("B", "dd")))
    }

  })
}
