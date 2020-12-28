package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.annotation.tailrec

/*
This problem was asked by Amazon.

There exists a staircase with N steps, and you can climb up either 1 or 2 steps at a time.
 Given N, write a function that returns the number of unique ways you can climb the staircase. The order of the steps matters.

For example, if N is 4, then there are 5 unique ways:

1, 1, 1, 1
2, 1, 1
1, 2, 1
1, 1, 2
2, 2
What if, instead of being able to climb 1 or 2 steps at a time,
you could climb any number from a set of positive integers X? For example, if X = {1, 3, 5}, you could climb 1, 3, or 5 steps at a time.
 */
class AmazonTest {

  @tailrec
  private def selectAll(acc: Set[Seq[Int]])(implicit stepNumbers: Int): Set[Seq[Int]] = {
    val iterationRes = acc.foldLeft(Set.empty[Seq[Int]]) { (accumulated: Set[Seq[Int]], element: Seq[Int]) =>
      var result = accumulated
      val summa = element.sum

      if (summa <= stepNumbers)
        result = result + element

      if (summa + 1 <= stepNumbers) {
        result -= element
        result = accumulated + (element :+ 1)
      }

      if (summa + 2 <= stepNumbers) {
        result -= element
        result = result + (element :+ 2)
      }
      result
    }

    if (iterationRes.forall(comb => comb.sum >= stepNumbers))
      return iterationRes

    selectAll(iterationRes)
  }

  def stes(stepNumbers: Int): Set[Seq[Int]] = {
    selectAll(Set(Seq(1), Seq(2)))(stepNumbers: Int)
  }

  @Test
  def amazonTaskTest(): Unit = {

    val actualResult = stes(4)

    val expectedResult = Set(
      Seq(1, 1, 1, 1),
      Seq(2, 1, 1),
      Seq(1, 2, 1),
      Seq(1, 1, 2),
      Seq(2, 2))

    assertEquals(expectedResult.toSeq.sortWith(_.length >= _.length), actualResult.toSeq.sortWith(_.length >= _.length))
  }

  @Test
  def amazonExtraTest(): Unit = {

    val actualResult = stes(5)

    val expectedResult = Set(
      Seq(1, 1, 1, 1, 1),
      Seq(1, 2, 1, 1),
      Seq(1, 1, 1, 2),
      Seq(2, 1, 1, 1),
      Seq(1, 1, 2, 1),
      Seq(1, 2, 2),
      Seq(2, 2, 1),
      Seq(2, 1, 2)
    )

    assertEquals(expectedResult.toSeq.sortWith(_.length >= _.length), actualResult.toSeq.sortWith(_.length >= _.length))
  }
}