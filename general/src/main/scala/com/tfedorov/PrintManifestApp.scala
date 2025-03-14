package com.tfedorov

import com.tfedorov.utils.FileUtils

import java.util.jar.JarFile

import scala.collection.JavaConverters._
/**
 * The application that reads and processes manifest files. It shows only related to git manifest entries
 */
object PrintManifestApp extends App {

  //Thread.currentThread.getContextClassLoader.getResources(JarFile.MANIFEST_NAME).asScala.toSeq.foreach(println)
  FileUtils.readAllManifests().map(_.split("\n").filter(_.contains("git")).distinct.mkString("\n")).foreach(println)

}
