package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MatrixRotateTest {

  private type Matrix = Seq[Seq[String]]

  private def rotateMatrix(input: Matrix): Matrix = {

    val width = input.head.length
    val zero: Matrix = Seq.fill(width)(Nil)
    input.foldLeft(zero) { (result, inputRow) =>
      result.zip(inputRow.reverse)
        .map { case (resultRow, inputRowEl) =>  resultRow :+ inputRowEl }
    }
  }

  @Test
  def defaultTest(): Unit = {
    val input = Seq(
      Seq("a", "b", "c"),
      Seq("e", "f", "g"),
      Seq("h", "i", "j"))

    val actualResult: Matrix = rotateMatrix(input)

    val expectedResult = Seq(
      Seq("c", "g", "j"),
      Seq("b", "f", "i"),
      Seq("a", "e", "h"))
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def customTest(): Unit = {
    val input = Seq(
      Seq("a", "b", "c"),
      Seq("e", "f", "g"),
    )

    val actualResult: Matrix = rotateMatrix(input)

    val expectedResult = Seq(
      Seq("c", "g"),
      Seq("b", "f"),
      Seq("a", "e"))
    assertEquals(expectedResult, actualResult)
  }
}
