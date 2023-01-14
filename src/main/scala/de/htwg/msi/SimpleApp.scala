package de.htwg.msi

import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("Simple Application").config("spark.master", "local").getOrCreate()

    val logData = spark.read.textFile("./README.md").cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")

    spark.stop()
  }
}