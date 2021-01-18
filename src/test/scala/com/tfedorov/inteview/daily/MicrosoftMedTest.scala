package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
  Easy Microsoft.

Compute the running median of a sequence of numbers. That is, given a stream of numbers,
print out the median of the list so far on each new element.

Recall that the median of an even-numbered list is the average of the two middle numbers.
For example, given the sequence [2, 1, 5, 7, 2, 0, 5], your algorithm should print out:

2, 1.5, 2, 3.5, 2, 2, 2

 */

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MicrosoftMedTest {

  private case class Agg(result: Seq[Float], sortedInput: Seq[Int])

  private def findMedians(input: Seq[Int]): Seq[Float] = {
    input.foldLeft(Agg(Nil, Nil)) { (agg: Agg, inputEl: Int) =>
      val mewSorted: Seq[Int] = (agg.sortedInput :+ inputEl).sorted
      val median: Float = findMedian(mewSorted)
      Agg(agg.result :+ median, mewSorted)
    }.result
  }

  private def findMedian(agg: Seq[Int]): Float = {
    val medianIndex: Int = agg.length / 2
    val isDiv: Int = agg.length % 2
    (medianIndex, isDiv) match {
      case (0, 1) => agg.head
      case (medianInd, 1) => agg(medianInd)
      case (afterMedianInd, 0) =>
        val beforeMedianVal: Int = agg(afterMedianInd - 1)
        val afterMedianVal: Int = agg(afterMedianInd)
        beforeMedianVal + ((afterMedianVal - beforeMedianVal).toFloat / 2)
    }
  }

  @Test
  def defaultMedian(): Unit = {
    val dict = Seq(2, 1, 5, 7, 2, 0, 5)

    val actualResult: Seq[Float] = findMedians(dict)

    val expectedResult = Seq(2, 1.5, 2, 3.5, 2, 2, 2)
    assertEquals(expectedResult, actualResult)
  }
}
