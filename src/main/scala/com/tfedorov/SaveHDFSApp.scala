package com.tfedorov


import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

object SaveHDFSApp extends App {

  val conf = new Configuration()
  conf.set("fs.defaultFS", "hdfs://localhost:9000")
  conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem")
  //ßconf.set("dfs.client.use.datanode.hostname", "true")
  conf.set("dfs.datanode.use.datanode.hostname", "true")
  val fs = FileSystem.get(conf)

  def mkPath(str: String): Path = new Path(str)

  val localSource = mkPath("/Users/tfedorov/IdeaProjects/tmp/pod.yaml")
  val hdfsDest = mkPath("/Hdfs3SinkConnector")
  fs.copyFromLocalFile(localSource, hdfsDest)

  val localDest = mkPath("/Users/tfedorov/IdeaProjects/")
  val hdfsSource = mkPath("/input/f1.txt")
  //fs.copyToLocalFile(hdfsSource,localDest)
}
