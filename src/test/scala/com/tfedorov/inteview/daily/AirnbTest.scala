package com.tfedorov.inteview.daily

import scala.collection.SortedSet

/*
This problem was asked by Airbnb.

Given a list of integers, write a function that returns the largest sum of non-adjacent numbers. Numbers can be 0 or negative.

For example, [2, 4, 6, 2, 5] should return 13, since we pick 2, 6, and 5. [5, 1, 1, 5] should return 10, since we pick 5 and 5.

Follow-up: Can you do this in O(N) time and constant space?
 */
class AirnbTest {

  import org.junit.jupiter.api.Assertions.assertEquals
  import org.junit.jupiter.api.Test

  def search(cand: Seq[Int], leave: Seq[Int])(implicit expectedResult: Int): Seq[Int] = {

    if (cand.sum == expectedResult)
      return cand
    if (leave.isEmpty)
      return Nil

    var result = Seq.empty[Int]
    leave.zipWithIndex.foreach { case (nextSearched, ind) =>
      val leaveWithot = leave.take(ind) ++ leave.drop(ind + 1)
      val resNext = search(cand :+ nextSearched, leaveWithot)
      if (resNext.sum == expectedResult)
        result = resNext
    }
    result
  }

  def nonAjastSum(input: Seq[Int], result: Int): Seq[Int] = {
    search(Nil, input)(result)
  }

  @Test
  def adjacentTest(): Unit = {

    val input = Seq(2, 4, 6, 2, 5)
    val actualResult: Seq[Int] = nonAjastSum(input, 13)

    assertEquals(5 :: 2 :: 6 :: Nil, actualResult)
  }

  @Test
  def adjacentTest5115(): Unit = {

    val input = Seq(5, 1, 1, 5)
    val actualResult: Seq[Int] = nonAjastSum(input, 10)

    assertEquals(5 :: 5 :: Nil, actualResult)
  }
}
