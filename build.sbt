import sbt.Keys._
import sbt._

name := "scala-save"

version := "0.1"

scalaVersion := "2.12.3"
val catsVersion = "1.0.1"
libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-common" % "2.7.1",
  "org.apache.hadoop" % "hadoop-hdfs" % "2.7.1",
  "org.typelevel" %% "cats-effect" % "3.4.8" % Test,
  "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  "com.github.pathikrit" %% "better-files" % "3.8.0" % Test,
  "org.junit.jupiter" % "junit-jupiter-api" % "5.7.0-RC1" % Test,
  "org.typelevel" %% "cats-core" % catsVersion % Test,
  "org.typelevel" %% "cats-free" % catsVersion % Test,
  "org.typelevel" %% "cats-mtl-core" % "0.2.1" % Test,
  "org.typelevel" %% "simulacrum" % "1.0.0" % Test,
  "org.apache.parquet" % "parquet-common" % "1.11.1",
  "org.apache.parquet" % "parquet-avro" % "1.11.1"
)

scalacOptions += "-Ypartial-unification"
lazy val manifestSettings = Seq(
  packageOptions in(Compile, packageBin) +=
    Package.ManifestAttributes(
      "git_last_commit" -> git.gitHeadCommit.value.toString,
      "git_last_message" -> git.gitHeadMessage.value.toString.replaceAll("\n", ""))
)

lazy val root = Project(id = "root", base = file(".")).settings(manifestSettings: _*)