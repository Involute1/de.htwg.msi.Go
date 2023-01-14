package de.htwg.msi.util

import java.nio.file.{FileSystems, Files, Path}
import scala.jdk.CollectionConverters.CollectionHasAsScala

object FileHandling {

  def getFileListFrom(dir: String): List[Path] = {
    val path: Path = FileSystems.getDefault.getPath(dir)
    Files.list(path)
      .filter(file => Files.isRegularFile(file))
      .filter(file => file.getFileName.toString.matches(Constants.sgfFileExtensionRegex))
      .toList.asScala.toList
  }

  def readFileListContent(fileList: List[Path]): List[String] = {
    fileList.map(path => Files.readString(path))
  }

  def getFilesContentFrom(dir: String): List[String] = {
    readFileListContent(getFileListFrom(dir))
  }

}
