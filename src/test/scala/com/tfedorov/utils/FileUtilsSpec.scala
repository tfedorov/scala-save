package com.tfedorov.utils

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import scala.util._

class FileUtilsSpec extends FlatSpec {

  ".readFile" should "reads file content as string" in {

    val result = FileUtils.readFile("./src/test/resources/File2Read.txt")

    result should be(Success("7 bytes"))
  }

  it should "returns fail on not existed file" in {

    val result = FileUtils.readFile("./src/test/resources/NotExist.txt")

    //result should be(Failure(new java.io.FileNotFoundException(".\\src\\test\\resources\\NotExist.txt (The system cannot find the file specified)")))
    result.isFailure should be(true)
  }

  ".readBytes" should "reads file's bytes" in {

    val result = FileUtils.readBytes("./src/test/resources/File2Read.txt")

    result.length should be(7)
  }

  it should "fails on not existed file" in {
    intercept[java.nio.file.NoSuchFileException] {
      FileUtils.readBytes("./src/test/resources/NotExist.txt")
    }
  }

  ".listOfFiles" should "reads files name in directory" in {

    val filesPath: Seq[String] = FileUtils.listOfFiles("./src/test/resources")

    filesPath.size should be(1)
  }

  it should "fails on not existed file" in {

    val filesPath: Seq[String] = FileUtils.listOfFiles("./src/test/resources/NotExistDir")

    filesPath should be(Seq("./src/test/resources/NotExistDir"))
  }

  ".fullPath" should "read files" in {

    val result: String = FileUtils.fullPath(".")

    result.contains("scala-save") should be(true)
  }

}




