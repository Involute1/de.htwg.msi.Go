package de.htwg.scala.kafka

import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util
import java.util.Properties
import scala.collection.JavaConverters._

object SgfDataKafkaConsumer extends App {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something")

  val consumer = new KafkaConsumer[String, String](props)
  val TOPIC = "GameData"

  consumer.subscribe(util.Collections.singletonList(TOPIC))

  while (true) {
    println("Polling..")
    val records = consumer.poll(100)
    for (record <- records.asScala) {
      println(record)
    }
  }
}
