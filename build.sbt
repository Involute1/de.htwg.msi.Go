ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val AkkaVersion = "2.7.0"
lazy val root = (project in file("."))
  .settings(
    name := "Go",
    coverageEnabled := true
  )
  .settings(
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
    libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    libraryDependencies += "org.apache.kafka" %% "kafka" % "3.3.1",
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "4.0.0",
    libraryDependencies += "com.typesafe.akka" %% "akka-discovery" % AkkaVersion
  )

