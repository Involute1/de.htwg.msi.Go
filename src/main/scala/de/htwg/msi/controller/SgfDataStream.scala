package de.htwg.msi.controller

import akka.NotUsed
import akka.stream.scaladsl.Source

import java.nio.file.{FileSystems, Files, Path}
import java.util.stream
import scala.jdk.StreamConverters.*


case class SgfDataStream(externalDSLParser: ExternalDSLParser, path: String){
  val dir: Path = FileSystems.getDefault.getPath(path)
  val sgfFiles: List[Path] = Files.list(dir).toScala(List)
                                    .filter(file => Files.isRegularFile(file))
                                    .filter(file => file.endsWith(".sgf"))

  val source: Source[Path, NotUsed] = Source(sgfFiles)

  //TODO: Paths => String in files

  //TODO: String in files => SgfData


}

