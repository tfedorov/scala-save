package com.tfedorov.inteview.daily_coding_problem

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


  def search(procCandidates: Seq[Int], forCheck: Seq[Int])(implicit expectedResult: Int): Seq[Int] = {

    if (procCandidates.sum == expectedResult)
      return procCandidates

    if (forCheck.isEmpty)
      return Nil

    var smallestResult = Seq.empty[Int]

    forCheck.zipWithIndex.foreach { case (nextCandidate, ind) =>
      val forCheckNoCandidate = forCheck.take(ind) ++ forCheck.drop(ind + 1)
      val candidateResult = search(procCandidates :+ nextCandidate, forCheckNoCandidate)
      if (candidateResult.sum == expectedResult && (smallestResult.isEmpty || smallestResult.size > candidateResult.size))
        smallestResult = candidateResult
    }
    smallestResult
  }

  @Test
  def adjacentTest(): Unit = {

    val input = Seq(2, 4, 6, 2, 5)
    val actualResult: Seq[Int] = search(Nil, input)(13)

    assertEquals(2 :: 6 :: 5 :: Nil, actualResult)
  }

  @Test
  def adjacentTest5115(): Unit = {

    val input = Seq(5, 1, 1, 5)
    val actualResult: Seq[Int] = search(Nil, input)(10)

    assertEquals(5 :: 5 :: Nil, actualResult)
  }
}
