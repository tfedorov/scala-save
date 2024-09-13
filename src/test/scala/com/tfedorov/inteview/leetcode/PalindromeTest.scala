package com.tfedorov.inteview.leetcode


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *
 * Code
 * Testcase
 * Test Result
 * Test Result
 * 131. Palindrome Partitioning
 * Medium
 * Topics
 * Companies
 * Given a string s, partition s such that every
 * substring
 * of the partition is a
 * palindrome
 * . Return all possible palindrome partitioning of s.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "aab"
 * Output: [["a","a","b"],["aa","b"]]
 * Example 2:
 *
 * Input: s = "a"
 * Output: [["a"]]
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 16
 * s contains only lowercase English letters.
 */
class PalindromeTest {

  def partition(s: String): List[List[String]] = {
    (1 to s.size).toList.map(s.sliding(_).toList).map(_.filter(l => l == l.reverse)).filter(_.nonEmpty)
  }


  @Test
  def defaultTest(): Unit = {
    val input = "aab"

    val actualResult = partition(input)

    val expectedResult = ("a" :: "a" :: "b" :: Nil) :: ("aa" :: Nil) :: Nil
    assertEquals(expectedResult, actualResult)
  }

}