package de.htwg.msi

import de.htwg.msi.controller.ExternalDSLParser
import de.htwg.msi.util.{Constants, FileHandling, SparkUtil}
import org.apache.spark.sql.SparkSession

object SparkExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSparkSession
    spark.sparkContext.setLogLevel("WARN")

    val dir = Constants.massiveSgfFileLocation
    val fileContents = FileHandling.getFilesContentFrom(dir)
    val parser = new ExternalDSLParser

    import spark.implicits._
    val sgfDataSet = spark.createDataset(fileContents)
      .map(content => parser.parseDSL(content))
      .filter(parsedFile => parsedFile.isLeft)
      .map(parsedFile => parsedFile.left.get)

    sgfDataSet.show()

//    val count = sgfDataSet.count()

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


    spark.stop()

  }
}
