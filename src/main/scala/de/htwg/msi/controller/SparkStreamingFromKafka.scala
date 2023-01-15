package de.htwg.msi.controller

import de.htwg.msi.util.SparkUtil
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.JavaConverters._
import collection.JavaConverters._

object SparkStreamingFromKafka extends App {

  val spark = SparkUtil.getSparkSession
  val streamingContext = new StreamingContext(spark.sparkContext, Seconds(1))

  val TOPIC = "GameData"
  val topics = asJavaCollection(Array(TOPIC))

  val scalaKafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "something",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )
  val kafkaParams = scalaKafkaParams.asJava

  val stream = KafkaUtils.createDirectStream(streamingContext, PreferConsistent, Subscribe[String, String](topics, kafkaParams))

  stream.map(record => record.value)
    .foreachRDD(rdd => println(rdd.toString()))
//
//  val dataFrame = spark.readStream
//    .format("kafka")
//    .option("kafka.bootstrap.servers", "localhost:9092")
//    .option("subscribe", TOPIC)
//    .option("startingOffsets", "earliest")
//    .load()
//
//  dataFrame.printSchema()

//  spark.stop()

}
