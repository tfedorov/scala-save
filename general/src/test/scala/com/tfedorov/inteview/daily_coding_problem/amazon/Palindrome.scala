package com.tfedorov.inteview.daily_coding_problem.amazon

//Hard
//Given a string, find the longest palindromic contiguous substring. If there are more than one with the maximum length, return any one.
//
//For example, the longest palindromic substring of "aabcdcb" is "bcdcb". The longest palindromic substring of "bananas" is "anana".

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Palindrome {

  def pallindrome(input: String): String = {
    pallindromeRecurs(input, Nil).sortWith(_.length > _.length).head
  }

  def pallindromeRecurs(input: String, agg: Seq[String]): Seq[String] = {
    if (input.equals(input.reverse))
      return agg :+ input
    val minusFirst = pallindromeRecurs(input.tail, agg)
    val minusLast = pallindromeRecurs(input.reverse.tail.reverse, agg)
    minusFirst ++ minusLast
  }

  @Test
  def pallindromebBananasTest(): Unit = {
    val input = "bananas"

    val actualResult = pallindrome(input)

    val expectedResult = "anana"
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def pallindromebBanunisTest(): Unit = {
    val input = "banunis"

    val actualResult = pallindrome(input)

    val expectedResult = "nun"
    assertEquals(expectedResult, actualResult)
  }

}
