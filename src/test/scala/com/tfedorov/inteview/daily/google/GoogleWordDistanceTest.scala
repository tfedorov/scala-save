package com.tfedorov.inteview.daily.google

/*
Google.

The edit distance between two strings refers to the minimum number of character insertions, deletions, and substitutions
 required to change one string to the other. For example, the edit distance between “kitten” and “sitting” is three:
 substitute the “k” for “s”, substitute the “e” for “i”, and append a “g”.

Given two strings, compute the edit distance between them.
 */

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GoogleWordDistanceTest {

  def calcRec(agg: Int, inp1Cut: String, inp2Cut: String): Int = {
    if (inp1Cut.isEmpty || inp2Cut.isEmpty)
      return agg
    val candidate2Same: Char = inp1Cut.head
    val indexOfCand = inp2Cut.indexOf(candidate2Same)
    // if no possible to change, go further
    if (indexOfCand < 0)
      calcRec(agg, inp1Cut.tail, inp2Cut)
    else
      calcRec(agg + 1, inp1Cut.tail, inp2Cut.drop(indexOfCand + 1))
  }

  def calcDistance(input1: String, input2: String): Int = {
    val inits = input1.reverse.inits.map(_.reverse).filterNot(_.isEmpty).toList

    val maxSame = inits.map(input1Cutted => calcRec(0, input1Cutted, input2)).max
    Math.max(input1.length, input2.length) - maxSame
  }


  @Test
  def defaultTest(): Unit = {
    val input1 = "kitten"
    val input2 = "sitting"

    val actualResult: Int = calcDistance(input1, input2)

    val expectedResult = 3
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def custom2Test(): Unit = {
    val input1 = "kitten"
    val input2 = "sittink"

    val actualResult: Int = calcDistance(input1, input2)

    val expectedResult = 3
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def custom2addTest(): Unit = {
    val input1 = "kitten"
    val input2 = "asittink"

    val actualResult: Int = calcDistance(input1, input2)

    val expectedResult = 4
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def custom2ReverseTest(): Unit = {
    val input1 = "aasittink"
    val input2 = "kitten"

    val actualResult: Int = calcDistance(input1, input2)

    val expectedResult = 5
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def custom3Test(): Unit = {
    val input1 = "itt"
    val input2 = "sittink"

    val actualResult: Int = calcDistance(input1, input2)

    val expectedResult = 4
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def custom4Test(): Unit = {
    val input1 = "itt"
    val input2 = "aiatata"

    val actualResult: Int = calcDistance(input1, input2)

    val expectedResult = 4
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def custom5Test(): Unit = {
    val input1 = "abcd"
    val input2 = "abcdefg"

    val actualResult: Int = calcDistance(input1, input2)

    val expectedResult = 3
    assertEquals(expectedResult, actualResult)
  }
}
