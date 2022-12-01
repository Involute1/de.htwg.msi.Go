package de.htwg.msi.controller

import de.htwg.msi.model.{Move, SgfData, SgfGameData}
import de.htwg.msi.util.Constants

import java.nio.file.{FileSystems, Files, Path}
import scala.concurrent.duration.Duration
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}
import scala.jdk.StreamConverters.*

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}


class SgfDataStreamTest extends AnyWordSpec {
  "SgfDataStream" should {
    val testParser = ExternalDSLParser()
    val dir = "src/test/resources/sgf"
    val sinkToFutureSeq = Sink.seq[SgfData]
    implicit val materializer = Materializer.createMaterializer(ActorSystem("SgfStreamTest"))
    val testDataStream = SgfDataStream(testParser, dir, sinkToFutureSeq, materializer)
    val result = Await.result(testDataStream.getResult, Duration.Inf)

    "read one valid sgfData object from a path with one valid, one invalid and a non sgf-file" in {
      result.size shouldBe 1
    }

    "input of valid sgf-file is correct" in {
      result.head shouldBe SgfData(SgfGameData(19, "MIss", "petgo3"), List(Move("B", "pp"), Move("W", "dp"), Move("B", "dd")))
    }

  }
}
