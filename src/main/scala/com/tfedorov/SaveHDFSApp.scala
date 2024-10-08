package com.tfedorov


import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

object SaveHDFSApp extends App {

  val conf = new Configuration()
  conf.set("fs.defaultFS", "hdfs://localhost:9000")
  conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem")
  val fs = FileSystem.get(conf)

  def mkPath(str: String): Path = new Path(str)

  val localSource = mkPath("/Users/tfedorov/IdeaProjects/tmp/tmp.txt")
  val hdfsDest = mkPath("/Hdfs3SinkConnector")
  //fs.copyFromLocalFile(localSource, hdfsDest)

  val localDest = mkPath("/Users/tfedorov/IdeaProjects/tmp/")
  val hdfsSource = mkPath("/Hdfs3SinkConnector/tmp.txt")
  fs.copyToLocalFile(hdfsSource, localDest)
}
