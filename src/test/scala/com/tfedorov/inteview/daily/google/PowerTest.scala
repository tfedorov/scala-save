package com.tfedorov.inteview.daily.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


/*Easy
 Google.

The power set of a set is the set of all its subsets. Write a function that, given a set, generates its power set.

For example, given the set {1, 2, 3}, it should return {{}, {1}, {2}, {3}, {1, 2}, {1, 3}, {2, 3}, {1, 2, 3}}
 */
class PowerTest {

  def findPow(input: Set[Int]): Set[Set[Int]] = {
    var result = Set(Set.empty[Int])
    input.zipWithIndex.foreach { case (value, index) =>
      val (before, after) = input.splitAt(index)
      val changed = after ++ before
      result = result ++ changed.inits
    }
    result
  }


  @Test
  def defaultTest(): Unit = {
    val input = Set(1, 2, 3)

    val actualResult: Set[Set[Int]] = findPow(input)

    val expectedResult = Set(Set.empty, Set(1), Set(2), Set(3), Set(1, 2), Set(1, 3), Set(2, 3), Set(1, 2, 3))
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def customTest(): Unit = {
    val input = Set(1, 2, 3, 4)

    val actualResult: Set[Set[Int]] = findPow(input)

    val expectedResult = Set(Set(), Set(2), Set(4), Set(1, 2), Set(2, 3), Set(1, 2, 3, 4), Set(3, 4), Set(3),
      Set(4, 1, 2), Set(4, 1), Set(1, 2, 3), Set(2, 3, 4), Set(1), Set(3, 4, 1))
    assertEquals(expectedResult, actualResult)
  }
}
