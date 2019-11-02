package com.tfedorov.utils

import java.io.{File, PrintWriter}
import java.nio.charset.{Charset, CodingErrorAction}
import java.nio.file.{Files, Paths}

import scala.io.Source
import scala.util.Try

object FileUtils {

  private[this] val DecoderUTF = Charset.forName("UTF-8").newDecoder()
  DecoderUTF.onMalformedInput(CodingErrorAction.IGNORE)

  @deprecated
  def readFile(file: File): Try[String] = readFileLines(file).map[String](_.mkString("\n"))

  @deprecated
  def readFile(path: String): Try[String] = readFileLines(path).map[String](_.mkString("\n"))

  def readBytes(path: String): Array[Byte] = Files.readAllBytes(Paths.get(path))

  def readBytes(file: File): Array[Byte] = Files.readAllBytes(file.toPath)

  def resourceFullPath(path: String): String = this.getClass.getClassLoader.getResource(path).getPath

  def readResource(path: String): Try[String] = readFile(resourceFullPath(path))

  @deprecated
  def readResourceLines(path: String): Iterator[String] = Source.fromFile(resourceFullPath(path)).getLines

  def notExist(path: String): Boolean = !exist(path)

  def exist(path: String): Boolean = new java.io.File(path).exists

  def fullPath(path: String): String = new java.io.File(path).getAbsolutePath

  @deprecated
  def readFileLines(path: String): Try[Iterator[String]] =
    Try {
      Source.fromFile(path)("UTF-8").getLines
    }

  @deprecated
  def readFileLines(file: File): Try[Iterator[String]] =
    Try {
      Source.fromFile(file)("UTF-8").getLines
    }

  def writeFile(path: String, content: String): PrintWriter = {
    new PrintWriter(path) {
      write(content)
      close()
    }
  }

  def shortName(filePath: String): String = new File(filePath).getName

  def concatPath(basePath: String, file: String): String = {
    new File(basePath, file).getPath
  }

  def concatPaths(paths: Seq[String]): String = {
    val base = paths.head
    paths.tail.foldLeft(base)(concatPath)
  }

  def fileName(path: String): String = {
    new File(path).getName
  }

  def listOfResourceFiles(resourceDir: String): Seq[String] = {
    val dirAbsolute = resourceFullPath(resourceDir)
    listOfFiles(dirAbsolute)
  }

  def listOfFiles(sourceFileOrDir: String): Seq[String] = {

    if (!isDir(sourceFileOrDir))
      return Seq(sourceFileOrDir)

    new File(sourceFileOrDir).listFiles.filter(_.isFile).map(_.getAbsolutePath)
  }

  def isDir(dir: String): Boolean = {
    val file: File = new File(dir)
    file.exists && file.isDirectory
  }

}
