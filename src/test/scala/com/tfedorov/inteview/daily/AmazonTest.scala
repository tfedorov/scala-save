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
  private def recSearch(acc: Set[Seq[Int]])(implicit stepNumbers: Int, climbs: Seq[Int]): Set[Seq[Int]] = {
    val iterationRes = acc.foldLeft(Set.empty[Seq[Int]]) { (accumulated: Set[Seq[Int]], element: Seq[Int]) =>
      var result = accumulated
      val summa = element.sum

      climbs.foreach((step: Int) =>
        if (summa + step <= stepNumbers) {
          result -= element
          result += (element :+ step)
        }
      )
      if (summa >= stepNumbers)
        result = result + element

      result
    }

    if (iterationRes.forall(comb => comb.sum >= stepNumbers))
      return iterationRes

    recSearch(iterationRes)
  }

  def search12(stepNumbers: Int): Set[Seq[Int]] = {
    searchSeq(stepNumbers, Seq(1, 2))
  }

  def searchSeq(stepNumbers: Int, climbSteps: Seq[Int]): Set[Seq[Int]] = {
    val flatten: Seq[Seq[Int]] = climbSteps.map(Seq(_))
    recSearch(flatten.toSet)(stepNumbers, climbSteps)
  }

  @Test
  def amazonTaskTest(): Unit = {

    val actualResult = search12(4)

    val expectedResult = Set(
      Seq(1, 1, 1, 1),
      Seq(2, 1, 1),
      Seq(1, 2, 1),
      Seq(1, 1, 2),
      Seq(2, 2))

    assertEquals(expectedResult.toSeq.sortWith(_.length >= _.length), actualResult.toSeq.sortWith(_.length >= _.length))
  }

  @Test
  def amazonChangedTest(): Unit = {

    val actualResult = search12(5)

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

  @Test
  def amazonExtraTest(): Unit = {

    val actualResult = searchSeq(5, 1 :: 3 :: 5 :: Nil)

    val expectedResult = Set(
      List(1, 1, 1, 1, 1),
      List(1, 1, 3),
      List(1, 3, 1),
      List(3, 1, 1),
      List(5))

    assertEquals(expectedResult.toSeq.sortWith(_.length >= _.length), actualResult.toSeq.sortWith(_.length >= _.length))
  }
}