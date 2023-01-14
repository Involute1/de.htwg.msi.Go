package de.htwg.msi

import de.htwg.msi.controller.ExternalDSLParser
import de.htwg.msi.util.FileHandling
import org.apache.spark.sql.SparkSession

object SparkExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SparkExample").config("spark.master", "local").getOrCreate()

    val dir = "src/test/resources/sgf"
    val fileContents = FileHandling.getFilesContentFrom(dir)
    val parser = new ExternalDSLParser

    import spark.implicits._
    val result = spark.createDataset(fileContents)
      .map(content => parser.parseDSL(content))
      .filter(parsedFile => parsedFile.isLeft)
      .map(parsedFile => parsedFile.left)

    println(result.count())


//    val logData = spark.read.textFile("./README.md").cache()
//    val numAs = logData.filter(line => line.contains("a")).count()
//    val numBs = logData.filter(line => line.contains("b")).count()
//    println(s"Lines with a: $numAs, Lines with b: $numBs")

    spark.stop()

  }
}
