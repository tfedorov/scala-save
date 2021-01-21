package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatrixRotateTest {

  def rotateMatrix(input: Array[Array[String]]): Array[Array[String]] = {

    val width = input.head.length
    val zero: Seq[Seq[String]] = Seq.fill(width)(Seq.empty[String])
    val seq = input.foldLeft(zero) { (agg: Seq[Seq[String]], seq) =>
      var res = agg
      seq.reverse.zipWithIndex.foreach { case (el, i) =>
        res = res.updated(i, agg(i) :+ el)
      }
      res
    }
    seq.map(_.toArray).toArray
  }

  @Test
  def defaultTest(): Unit = {
    val input = Array(
      Array("a", "b", "c"),
      Array("e", "f", "g"),
      Array("h", "i", "j"))

    val actualResult: Array[Array[String]] = rotateMatrix(input)

    val expectedResult = Array(
      Array("c", "g", "j"),
      Array("b", "f", "i"),
      Array("a", "e", "h"))
    assertEquals(expectedResult.map(_.toSeq).toSeq, actualResult.map(_.toSeq).toSeq)
  }

  @Test
  def customTest(): Unit = {
    val input = Array(
      Array("a", "b", "c"),
      Array("e", "f", "g"),
    )

    val actualResult: Array[Array[String]] = rotateMatrix(input)

    val expectedResult = Array(
      Array("c", "g"),
      Array("b", "f"),
      Array("a", "e"))
    assertEquals(expectedResult.map(_.toSeq).toSeq, actualResult.map(_.toSeq).toSeq)
  }
}
