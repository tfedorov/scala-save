package com.tfedorov.inteview.daily_coding_problem

/* Medium
Palantir.

Write an algorithm to justify text. Given a sequence of words and an integer line length k, return a list of strings which represents each line, fully justified.

More specifically, you should have as many words as possible in each line.
There should be at least one space between each word. Pad extra spaces when necessary so that each line has exactly length k.
Spaces should be distributed as equally as possible, with the extra spaces, if any, distributed starting from the left.

If you can only fit one word on a line, then you should pad the right-hand side with spaces.

Each word is guaranteed not to be longer than k.

For example, given the list of words ["the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"] a
nd k = 16, you should return the following:

["the  quick brown", # 1 extra space on the left
"fox  jumps  over", # 2 extra spaces distributed evenly
"the   lazy   dog"] # 3 extra spaces distributed evenly

 */

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PalantirTest {

  def form(input: Seq[String])(implicit desiredLength: Int): Seq[String] = {
    var cadidates: Seq[Seq[String]] = Nil

    val lastCandidate = input.foldLeft(Seq.empty[String]) { (currentAgg, word) =>
      val candidateText = currentAgg.mkString(" ") + " " + word
      if (candidateText.length > desiredLength) {
        cadidates = cadidates :+ currentAgg
        word :: Nil
      }
      else
        currentAgg :+ word
    }
    cadidates = cadidates :+ lastCandidate
    cadidates.map { batch: Seq[String] =>
      val extraSpaces: scala.Seq[String] = wordsSpaces(batch, desiredLength)
      batch.zip(extraSpaces).map { case (word: String, spaces: String) => word + spaces }
        .mkString("").trim
    }
  }

  private def wordsSpaces(batch: Seq[String], desiredLength: Int): Seq[String] = {
    val actualLength = batch.mkString("").length
    val needed = desiredLength - actualLength
    val numSpacesForAll = needed / (batch.length - 1)
    val extraSpaceNumber = needed % (batch.length - 1)
    val allWordsSpaces = " " * numSpacesForAll
    val extraLeftSpaces = allWordsSpaces + " "
    Seq.fill(extraSpaceNumber)(extraLeftSpaces).padTo(batch.length, allWordsSpaces)
  }

  @Test
  def defaultTest(): Unit = {
    val input = Seq("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog")
    val length = 16

    val actualResult: Seq[String] = form(input)(length)

    val expectedResult = Seq("the  quick brown", "fox  jumps  over", "the   lazy   dog")
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def extraLengTest(): Unit = {
    val input = Seq("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dogs")
    val length = 16

    val actualResult: Seq[String] = form(input)(length)

    val expectedResult = Seq("the  quick brown", "fox  jumps  over", "the   lazy  dogs")
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def extraLeng2Test(): Unit = {
    val input = Seq("the", "quick", "brown", "fox", "jumps", "over", "th", "la", "do", "ha")
    val length = 16

    val actualResult: Seq[String] = form(input)(length)

    val expectedResult = Seq("the  quick brown", "fox  jumps  over", "th   la   do  ha")
    assertEquals(expectedResult, actualResult)
  }
}
