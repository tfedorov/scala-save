package com.tfedorov.inteview.daily.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class OutOfOrder {

  /*
  We can determine how "out of order" an array A is by counting the number of inversions it has.
   Two elements A[i] and A[j] form an inversion if A[i] > A[j] but i < j. That is, a smaller element appears after a larger element.

  Given an array, count the number of inversions it has. Do this faster than O(N^2) time.

  You may assume each element in the array is distinct.

  For example, a sorted list has zero inversions.
  The array [2, 4, 1, 3, 5] has three inversions: (2, 1), (4, 1), and (4, 3).
  The array [5, 4, 3, 2, 1] has ten inversions: every distinct pair forms an inversion.
   */
  @Test
  def rangeTest(): Unit = {
    val input = 1 to 10

    val actualResult = calcRecurs(input, Nil)

    val expectedResult = Nil

    assertEquals(expectedResult, actualResult)
  }

  //  The array [2, 4, 1, 3, 5] has three inversions: (2, 1), (4, 1), and (4, 3).
  @Test
  def def1Test(): Unit = {
    val input = Seq(2, 4, 1, 3, 5)

    val actualResult = calcRecurs(input, Nil)

    val expectedResult = (2, 1) :: (4, 1) :: (4, 3) :: Nil

    assertEquals(expectedResult, actualResult)
  }

  //  The array [2, 4, 1, 3, 5] has three inversions: (2, 1), (4, 1), and (4, 3).
  @tailrec
  final def calcRecurs(input: Seq[Int], inverse: List[(Int, Int)]): List[(Int, Int)] = {

    if (input.size <= 1)
      return inverse

    val checkedNum = input.head
    val searchInverseInput = input.tail.toList
    val foundedInversePairs: List[(Int, Int)] = searchInverseInput
      .filter(_ <= checkedNum)
      .flatMap { inversEl => List((checkedNum, inversEl)) }

    val allFounded = inverse ::: foundedInversePairs
    calcRecurs(searchInverseInput, allFounded)
  }

  // Given an array, count the number of inversions it has. Do this faster than O(N^2) time.
  // The array [2, 4, 1, 3, 5] has three inversions: (2, 1), (4, 1), and (4, 3).

  final def calcShort(input: Seq[Int], inverse: List[(Int, Int)]): List[(Int, Int)] = {

    if (input.size <= 1)
      return inverse

    val inverseCandidates = mutable.TreeMap.empty[Int, mutable.ListBuffer[Int]]

    input.foreach { inverseCand =>

      val prevLessThanCurrent = inverseCandidates.range(inverseCand, Int.MaxValue).values.map(_.append(inverseCand))
      if (prevLessThanCurrent.isEmpty)
        inverseCandidates.put(inverseCand, ListBuffer.empty)

    }

    val r: mutable.Iterable[List[(Int, Int)]] = inverseCandidates.filterNot(_._2.isEmpty).flatMap(kv => kv._2.map(end => List((kv._1, end))))
    r.flatten.toList
  }

  // Given an array, count the number of inversions it has. Do this faster than O(N^2) time.
  // The array [2, 4, 1, 3, 5] has three inversions: (2, 1), (4, 1), and (4, 3).
  @Test
  def def1ShortTest(): Unit = {
    val input = Seq(2, 4, 1, 3, 5)

    val actualResult = calcShort(input, Nil)

    val expectedResult = (2, 1) :: (4, 1) :: (4, 3) :: Nil

    assertEquals(expectedResult, actualResult)
  }

}
