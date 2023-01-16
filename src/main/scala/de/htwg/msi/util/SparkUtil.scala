package de.htwg.msi.util

import org.apache.spark.sql.SparkSession

object SparkUtil {

  def getSparkSession: SparkSession = {
    SparkSession.builder()
      .appName("Spark")
      .config("spark.master", "local[*]")
      .getOrCreate()
  }

}
