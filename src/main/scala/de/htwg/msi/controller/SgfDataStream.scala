package de.htwg.msi.controller

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import de.htwg.msi.model.SgfData
import de.htwg.msi.util.Constants

import java.nio.file.{FileSystems, Files, Path}
import scala.jdk.CollectionConverters.CollectionHasAsScala


case class SgfDataStream[T](externalDSLParser: ExternalDSLParser, dir: String, sink: Sink[SgfData, T], materializer: Materializer) {

  def getResult: T = {
    val path: Path = FileSystems.getDefault.getPath(dir)
    val sgfFiles: List[Path] = Files.list(path)
      .filter(file => Files.isRegularFile(file))
      .filter(file => file.getFileName.toString.matches(Constants.sgfFileExtensionRegex))
      .toList.asScala.toList

    val sgfFileSource: Source[Path, NotUsed] = Source(sgfFiles)

    val sgfFileTextExtraction: Flow[Path, String, NotUsed] = Flow.fromFunction(path => Files.readString(path))

    val sgfDataExtraction: Flow[String, SgfData, NotUsed] = Flow.fromFunction(fileText => externalDSLParser.parseDSL(fileText))
      .filter(_.isLeft)
      .map(_.left.toOption.get)

    sgfFileSource.via(sgfFileTextExtraction).via(sgfDataExtraction).runWith(sink)(materializer)

  }


}