package com.tfedorov.utils

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path

object HDFSUtils {
  implicit def str2PathCreator(path: String): _PathCreator = new _PathCreator(path)

  class _PathCreator(str: String) {
    implicit def asHDFSPath: Path = new Path(str)
  }

  def config(keyVals: (String, String)*): Configuration = {
    val result = new Configuration()
    keyVals.foreach(kv => result.set(kv._1, kv._2))
    result
  }
}
