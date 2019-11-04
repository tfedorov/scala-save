package com.tfedorov.utils

import java.io.{File, PrintWriter}
import java.nio.charset.{Charset, CodingErrorAction}
import java.nio.file.{Files, Paths}

import scala.io.{BufferedSource, Source}
import scala.util.Try

object FileUtils {

  private[this] val DecoderUTF = Charset.forName("UTF-8").newDecoder()
  DecoderUTF.onMalformedInput(CodingErrorAction.IGNORE)

  private case class _ClosableLines(private var lines: Iterator[String], private val source: BufferedSource) {

    def processAndClose[R](processFunc: Iterator[String] => R): R = {
      try {
        processFunc(lines)
      }
      finally {
        source.close()
      }
    }
  }

  private def makeTextFunc(lines: Iterator[String]) = lines.mkString("\n")

  def readAllFile(file: File): Try[String] =
    readFileLines(file).map(closable => closable.processAndClose(makeTextFunc))

  def readAllFile(path: String): Try[String] =
    readFileLines(path).map(closable => closable.processAndClose(makeTextFunc))

  def readBytes(path: String): Try[Array[Byte]] = Try {
    Files.readAllBytes(Paths.get(path))
  }

  def readBytes(file: File): Try[Array[Byte]] = Try {
    Files.readAllBytes(file.toPath)
  }

  def resourceFullPath(path: String): String = this.getClass.getClassLoader.getResource(path).getPath

  def readResource(path: String): Try[String] = readAllFile(resourceFullPath(path))

  def notExist(path: String): Boolean = !exist(path)

  def exist(path: String): Boolean = new java.io.File(path).exists

  private def readFileLines(path: String): Try[_ClosableLines] =
    Try {
      val source: BufferedSource = Source.fromFile(path)("UTF-8")
      val iterator = source.getLines
      _ClosableLines(iterator, source)
    }

  private def readFileLines(file: File): Try[_ClosableLines] =
    Try {
      val source: BufferedSource = Source.fromFile(file)("UTF-8")
      val iterator = source.getLines
      _ClosableLines(iterator, source)
    }

  def writeFile(path: String, content: String): PrintWriter = {
    new PrintWriter(path) {
      write(content)
      close()
    }
  }

  def shortName(filePath: String): Option[String] = {
    fullName(filePath).map(fullNamePath => new File(fullNamePath).getName)
  }


  def fullName(filePath: String): Option[String] = {
    if (notExist(filePath))
      return None
    Some(new File(filePath).getCanonicalPath)
  }

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
    fileNames(dirAbsolute)
  }

  def fileNames(sourceFileOrDir: String): Seq[String] = {

    if (!isDir(sourceFileOrDir))
      return Seq(sourceFileOrDir)

    new File(sourceFileOrDir).listFiles.filter(_.isFile).map(_.getAbsolutePath)
  }

  def isDir(dir: String): Boolean = {
    val file: File = new File(dir)
    file.exists && file.isDirectory
  }

}
