package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*

Given a string s, partition the string into one or more substrings such that the characters in each substring are unique.
That is, no letter appears in a single substring more than once.

Return the minimum number of substrings in such a partition.

Note that each character should belong to exactly one substring in a partition.



Example 1:

Input: s = "abacaba"
Output: 4
Explanation:
Two possible partitions are ("a","ba","cab","a") and ("ab","a","ca","ba").
It can be shown that 4 is the minimum number of substrings needed.
Example 2:

Input: s = "ssssss"
Output: 6
Explanation:
The only valid partition is ("s","s","s","s","s","s").
 */
class LakshmiTest {

  @Test
  def taskTest(): Unit = {
    val input = "abacaba"

    val actual = checkAll(input)

    val expected = 4
    assertEquals(expected, actual)
  }

  @Test
  def taskTest2(): Unit = {
    val input = "abdcafa"

    val actual = checkAll(input)

    val expected = 3
    assertEquals(expected, actual)
  }

  @Test
  def taskTest3(): Unit = {
    val input = "ssssss"

    val actual = checkAll(input)

    val expected = 6
    assertEquals(expected, actual)
  }

  private def checkAll(input: String): Int = {
    val result: Seq[Seq[String]] = Seq(Seq(input.headOption.map(_.toString).getOrElse("")))
    val sorted = input.tail.foldLeft(result) { (agg: Seq[Seq[String]], letter: Char) =>
      agg.flatMap { possibleWords: Seq[String] =>
        val currentWord: String = possibleWords.last
        val possibleWordsPlusLetter: Seq[String] = possibleWords :+ letter.toString
        if (currentWord.contains(letter))
          Seq(possibleWordsPlusLetter)
        else {
          val previousWords = possibleWords.reverse.tail.reverse
          val currentWithLetterAndPrevious: Seq[String] = previousWords :+ (currentWord + letter)
          Seq(currentWithLetterAndPrevious, possibleWordsPlusLetter)
        }
      }
    }.sortWith(_.size <= _.size)
    println("\nResult for " + input)
    sorted.take(3).foreach(println)
    sorted.head.size
  }

}
