package com.tfedorov.inteview.daily.amazon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringNumTest {

  /**
   * Given a string s and an integer k, break up the string into multiple lines such that each line has a length of k or less.
   * You must break it up so that words don't break across lines. Each line has to have the maximum possible amount of words. If there's no way to break the text up, then return null.
   *
   * You can assume that there are no spaces at the ends of the string and that there is exactly one space between each word.
   */

  @Test
  def taskTest(): Unit = {

    val inputText = "the quick brown fox jumps over the lazy dog"
    val actual = spitN(inputText, 10)
    val expected = List("the quick",
      "brown fox",
      "jumps over",
      "the lazy",
      "dog"
    ) //No string in the list has a length of more than 10.
    assertEquals(expected, actual)
  }

  private def spitN(inputText: String, k: Int): List[String] = {
    var beginLineIdx: Int = 0
    var lastSpaceIdx: Int = 0
    val procesed = (inputText + " ").zipWithIndex.foldLeft(List.empty[String]) { (agg: List[String], chIdx: (Char, Int)) =>

      if (chIdx._1 == ' ') {
        val endWordIdx = chIdx._2
        if (endWordIdx - beginLineIdx > k) {
          if (beginLineIdx == lastSpaceIdx) {
            beginLineIdx = endWordIdx
            lastSpaceIdx = endWordIdx
            "" :: agg
          } else {
            val newWord = inputText.substring(beginLineIdx, lastSpaceIdx)
            beginLineIdx = lastSpaceIdx + 1
            lastSpaceIdx = endWordIdx
            newWord :: agg
          }
        } else {
          lastSpaceIdx = endWordIdx
          agg
        }
      } else
        agg
    }
    (inputText.substring(beginLineIdx, inputText.length) :: procesed).reverse
  }
}


