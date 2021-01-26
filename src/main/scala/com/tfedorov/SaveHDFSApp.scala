package com.tfedorov


import com.tfedorov.utils.FileUtils._
import com.tfedorov.utils.HDFSUtils
import com.tfedorov.utils.HDFSUtils._
import org.apache.hadoop.fs.FileSystem

object SaveHDFSApp extends App {

  val conf = HDFSUtils.config(
    "fs.defaultFS" -> "hdfs://localhost:9000"
    , "fs.hdfs.impl" -> "org.apache.hadoop.hdfs.DistributedFileSystem")
  val fs = FileSystem.get(conf)

  val localSource = resourceFullPath("fileSource/file2HDFS.txt").asHDFSPath
  val hdfsDest = "/Hdfs3SinkConnector".asHDFSPath
  //fs.copyFromLocalFile(localSource, hdfsDest)

  val localDest = fullName("src/main/resources/fileDestination/").get.asHDFSPath
  val hdfsSource = "/Hdfs3SinkConnector/file2HDFS.txt".asHDFSPath
  fs.copyToLocalFile(hdfsSource, localDest)
}
