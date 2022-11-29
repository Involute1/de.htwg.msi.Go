package de.htwg.msi.controller

import akka.NotUsed
import akka.stream.scaladsl.{Flow, RunnableGraph, Sink, Source}
import de.htwg.msi.model.SgfData
import de.htwg.msi.util.Constants

import java.nio.file.{FileSystems, Files, Path}
import java.util.stream
import scala.jdk.StreamConverters.*

case class SgfDataStream(externalDSLParser: ExternalDSLParser, dir: String, sink: Sink[SgfData, NotUsed]){

  def getGraph: RunnableGraph[NotUsed] = {
    
    val path: Path = FileSystems.getDefault.getPath(dir)
    val sgfFiles: List[Path] = Files.list(path).toScala(List)
      .filter(file => Files.isRegularFile(file))
      .filter(file => file.endsWith(Constants.sgfFileExtension))

    val sgfFileSource: Source[Path, NotUsed] = Source(sgfFiles)

    val sgfFileTextExtraction: Flow[Path, String, NotUsed] = Flow.fromFunction(path => Files.readString(path))

    val sgfDataExtraction: Flow[String, SgfData, NotUsed] = Flow.fromFunction(fileText => externalDSLParser.parseDSL(fileText))
      .filter(_.isLeft)
      .map(_.left.toOption.get)

    sgfFileSource.via(sgfFileTextExtraction).via(sgfDataExtraction).to(sink)

  }


}

