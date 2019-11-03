package com.tfedorov.utils

import java.io.FileNotFoundException

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import scala.util._

class FileUtilsSpec extends FlatSpec {

  ".readAllFile" should "reads file content as string" in {

    val result = FileUtils.readAllFile("./src/test/resources/File2Read.txt")

    result should be(Success("7 bytes"))
  }

  it should "returns fail on not existed file" in {

    val result = FileUtils.readAllFile("./src/test/resources/NotExist.txt")

    result.isFailure should be(true)
    result.failed.get.getClass should be(classOf[FileNotFoundException])
    result.failed.get.getMessage should be(".\\src\\test\\resources\\NotExist.txt (The system cannot find the file specified)")
  }

  ".readBytes" should "reads file's bytes" in {

    val result = FileUtils.readBytes("./src/test/resources/File2Read.txt")

    result.get should be(Array(55, 32, 98, 121, 116, 101, 115))
  }

  it should "fails on not existed file" in {
    val result = FileUtils.readBytes("./src/test/resources/NotExist.txt")

    result.isFailure should be(true)
  }

  ".listOfFiles" should "reads files name in directory" in {

    val filesPath: Seq[String] = FileUtils.listOfFiles("./src/test/resources")

    filesPath.size should be(1)
  }

  it should "fails on not existed file" in {

    val filesPath: Seq[String] = FileUtils.listOfFiles("./src/test/resources/NotExistDir")

    filesPath should be(Seq("./src/test/resources/NotExistDir"))
  }

  ".fullPath" should "reads existed files" in {

    val result: String = FileUtils.fullPath(".")

    result.contains("scala-save") should be(true)
  }

  it should "reads not existed files" in {

    val result: String = FileUtils.fullPath("./src/test/resources/NotExistDir")

    result.contains("scala-save") should be(true)
  }

  ".exist" should "checks existed file" in {

    val result = FileUtils.exist("./src/test/resources/File2Read.txt")

    result should be(true)
  }

  it should "checks not existed file" in {

    val result = FileUtils.exist("./src/test/resources/NotExist.txt")

    result should be(false)
  }
}




