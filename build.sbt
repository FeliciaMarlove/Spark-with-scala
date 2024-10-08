ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"
lazy val root = (project in file("."))
  .settings(
    name := "scala-with-spark"
  )

val sparkVersion = "3.5.0"
val scalaTestVersion = "3.2.17"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
)
