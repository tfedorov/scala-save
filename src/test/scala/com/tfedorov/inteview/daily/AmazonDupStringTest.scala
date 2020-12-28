package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.annotation.tailrec
import scala.collection.immutable

/*
Given an integer k and a string s, find the length of the longest substring that contains at most k distinct characters.

For example, given s = "abcba" and k = 2, the longest substring with k distinct characters is "bcb".
 */
class AmazonDupStringTest {

  def search(input: String, distincNum: Int): String = {
    val noDistinctLetters: Map[Char, Int] = input.groupBy(ch => ch).mapValues(_.length).filter(_._2 > 1)

    (distincNum to input.length).reverse.foreach { num =>
      input.sliding(num).foreach { candidate =>
        val districtsLetter = candidate.toSeq.filter(ch => noDistinctLetters.contains(ch))
        if (districtsLetter.distinct.length == 1 && districtsLetter.size == distincNum)
          return candidate
      }
    }
    ""
  }

  @Test
  def amazonTaskTest(): Unit = {
    val input = "abcba"

    val actualResult = search(input, 2)

    val expectedResult = "bcb"
    assertEquals(expectedResult, actualResult)
  }

}