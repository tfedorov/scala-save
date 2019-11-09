package com.tfedorov.utils

import java.io.FileNotFoundException

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import org.scalatest.tagobjects.Disk

import scala.util._

class FileUtilsSpec extends FlatSpec {

  ".readAllFile" should "reads file content as string" in {

    val result = FileUtils.readAllFile("./src/test/resources/singleFileFolder/File2Read.txt")

    result should be(Success("7 bytes"))
  }

  it should "returns fail on not existed file" in {

    val result = FileUtils.readAllFile("./src/test/resources/NotExist.txt")

    result.isFailure should be(true)
    result.failed.get.getClass should be(classOf[FileNotFoundException])
    result.failed.get.getMessage should be(".\\src\\test\\resources\\NotExist.txt (The system cannot find the file specified)")
  }

  ".readBytes" should "reads file's bytes" in {

    val result = FileUtils.readBytes("./src/test/resources/singleFileFolder/File2Read.txt")

    result.get should be(Array(55, 32, 98, 121, 116, 101, 115))
  }

  it should "fails on not existed file" in {
    val result = FileUtils.readBytes("./src/test/resources/NotExist.txt")

    result.isFailure should be(true)
  }

  ".fileNames" should "returns files name in directory" in {

    val filesPath: Seq[String] = FileUtils.fileNames("./src/test/resources/singleFileFolder")

    filesPath.size should be(1)
  }

  it should "returns file name for file" in {

    val filesPath: Seq[String] = FileUtils.fileNames("./src/test/resources/singleFileFolder/File2Read.txt")

    filesPath.size should be(1)
  }

  it should "fails on not existed file" in {

    val filesPath: Seq[String] = FileUtils.fileNames("./src/test/resources/NotExistDir")

    filesPath should be(Seq("./src/test/resources/NotExistDir"))
  }

  ".exist" should "checks existed file" in {

    val result = FileUtils.exist("./src/test/resources/singleFileFolder/File2Read.txt")

    result should be(true)
  }

  it should "checks not existed file" in {

    val result = FileUtils.exist("./src/test/resources/NotExist.txt")

    result should be(false)
  }

  ".shortName" should "returns existed names" in {

    val result = FileUtils.shortName("./src/test/resources/singleFileFolder/File2Read.txt")

    result should be(Some("File2Read.txt"))
  }

  it should "returns NOT existed names" in {

    val result = FileUtils.shortName("./src/test/resources/NotExist.txt")

    result should be(None)
  }

  it should "returns dir names" in {

    val result = FileUtils.shortName(".")

    result should be(Some("scala-save"))
  }

  ".fullName" should "returns NOT existed names" in {

    val result = FileUtils.fullName("./src/test/resources/NotExist.txt")

    result should be(None)
  }

  ignore should "returns existed names" taggedAs Disk in {

    val result = FileUtils.fullName("./src/test/resources/singleFileFolder/File2Read.txt")

    result should be(Some("C:/work/workspace/private/scala-save/src/test/resources/File2Read.txt"))
  }

  ignore should "returns dir names" taggedAs Disk in {

    val result = FileUtils.fullName(".")

    result should be(Some("C:/work/workspace/private/scala-save"))
  }

  ".writeFile" should "write specific file" in {

    FileUtils.writeFile("./src/test/resources/writeFileDirectory/desiredFile.txt", "desired content")

    val expected = FileUtils.readAllFile("./src/test/resources/writeFileDirectory/desiredFile.txt")
    expected should be(Success("desired content"))
  }
}




