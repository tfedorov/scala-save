package com.tfedorov.utils

import java.nio.file.NoSuchFileException
import java.util.Date

import better.files.File._
import better.files._
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import scala.collection.immutable

/**
 * http://fruzenshtein.com/scala-work-with-files-folders/
 **/
class BetterFilesSpec extends FlatSpec {

  ".toFile" should "converts to file" in {
    val file: File = "./src/test/resources/writeFileDirectory/desiredFile.txt".toFile

    file.exists should be(true)
  }

  it should "converts to Not existed file" in {
    val notExistedFile = "./src/test/resources/NotExist.txt".toFile

    notExistedFile.exists should be(false)
  }

  ".createIfNotExists" should "creates a file" in {
    val fileName = new Date().getTime
    val newFile = (temp + "/" + fileName)
      .toFile
      .createIfNotExists()

    newFile.exists should be(true)
  }

  ".delete" should "deletes a file" in {
    val fileName = new Date().getTime
    val newFile = (temp + "/" + fileName)
      .toFile
      .createIfNotExists()

    newFile.delete()

    newFile.exists should be(false)
  }

  ".contentAsString" should "reads file content as string" in {
    val existedFile = "./src/test/resources/singleFileFolder/File2Read.txt".toFile

    val actualContent = existedFile.contentAsString

    actualContent should be("7 bytes")
  }

  it should "returns fail on not existed file" in {
    intercept[NoSuchFileException] {
      val notExistedFile = "./src/test/resources/NotExist.txt".toFile

      notExistedFile.contentAsString
    }
  }

  ".byteArray" should "reads file's bytes" in {
    val existedFile = "./src/test/resources/singleFileFolder/File2Read.txt".toFile

    val actualContent = existedFile.byteArray

    actualContent should be(Array(55, 32, 98, 121, 116, 101, 115))
  }

  it should "fails on not existed file" in {
    intercept[NoSuchFileException] {
      val notExistedFile = "./src/test/resources/NotExist.txt".toFile

      notExistedFile.byteArray
    }
  }

  ".name" should "returns files name in directory" in {
    val existedFile = "./src/test/resources/singleFileFolder/File2Read.txt".toFile

    val actualContent = existedFile.name

    actualContent should be("File2Read.txt")
  }

  ".path" should "returns existed names" in {
    val existedFile = "./src/test/resources/singleFileFolder/File2Read.txt".toFile

    val actualPath: String = existedFile.path.toString

    actualPath should be(
      "C:\\work\\workspace\\private\\scala-save\\src\\test\\resources\\singleFileFolder\\File2Read.txt")
  }

  it should "returns NOT existed names" in {
    val existedFile = "./src/test/resources/NotExist.txt".toFile

    val actualPath: String = existedFile.path.toString

    actualPath should be("C:\\work\\workspace\\private\\scala-save\\src\\test\\resources\\NotExist.txt")
  }

  ".writeFile" should "writes specific file" in {
    val file: File = "./src/test/resources/writeFileDirectory/desiredFile.txt"
      .toFile
      .createIfNotExists()

    file.write("desired content")

    file.contentAsString should be("desired content")
  }

  "root" should "creates short cuts" in {

    val actualFiles = (root / "Users/").path.toString

    println(actualFiles)
    actualFiles.nonEmpty should be(true)
  }

  "home" should "creates short cuts" in {

    val actualFiles = home.path.toString

    println(actualFiles)
    actualFiles.nonEmpty should be(true)
  }

  ".zipTo" should "compares 2 file names" in {
    val fileName1 = new Date().getTime
    val fileName2 = new Date().getTime + 100

    val actualResult: immutable.Seq[(Char, Char)] = (temp + "/" + fileName1).zip(temp + "/" + fileName2)

    actualResult.exists(ch => !ch._1.equals(ch._2)) should be(true)
  }

}




