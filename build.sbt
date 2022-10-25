ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.0"

import org.scoverage.coveralls.Imports.CoverallsKeys._
lazy val root = (project in file("."))
  .settings(
    name := "Go",
    coverageEnabled := true,
    coverallsToken := Some("uEVPhMEA8k14APuYbjGTeQCdcUqbYhGzp")
  )
  .settings(
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.14",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % "test"
  )

