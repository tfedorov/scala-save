package com.tfedorov.inteview.daily.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GoogleSubArray {

  def maxSeq(inputSeq: Seq[Int], size: Int): Seq[Int] = {
    inputSeq.sliding(size).map(_.max).toList
  }

  def maxSeqOpt(inputSeq: Seq[Int], size: Int): Seq[Int] = {
    var current: Seq[Int] = Nil
    inputSeq.foldLeft[Seq[Int]](Nil) { (agg, element) =>
      current = current :+ element
      if (current.length <= size) {
        current = current :+ element
        Nil
      } else {
        current = current.tail
        agg :+ current.max
      }
    }
  }

  @Test
  def taskTest(): Unit = {
    val inputSeq = Seq(10, 5, 2, 7, 8, 7)
    val size = 3

    val actualResult: Seq[Int] = maxSeqOpt(inputSeq, size)

    val expectedResult = Seq(10, 7, 8, 8)
    assertEquals(expectedResult, actualResult)
  }
}
