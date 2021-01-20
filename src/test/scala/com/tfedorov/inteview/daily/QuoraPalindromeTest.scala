package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/* medium Quora.

Given a string, find the palindrome that can be made by inserting the fewest number of characters as possible anywhere
 in the word. If there is more than one palindrome of minimum length that can be made, return the
  lexicographically earliest one (the first one alphabetically).

For example, given the string "race", you should return "ecarace", since we can add three letters to it
 (which is the smallest amount to make a palindrome). There are seven other palindromes that can be made from
 "race" by adding three letters, but "ecarace" comes first alphabetically.

As another example, given the string "google", you should return "elgoogle".

 */
class QuoraPalindromeTest {
  private def isValidCandidate(candidate: String)(implicit input: String) = candidate.contains(input) && candidate.equals(candidate.reverse)

  private def allPalindromes(implicit input: String): Seq[String] = {
    val averseLetters = input +: input.inits.toList.filterNot(_.isEmpty)
    val reverseLetters = input +: input.reverse.inits.toList.filterNot(_.isEmpty)
    val enhancedBefore = extraBefore(averseLetters, reverseLetters)
    val enhancedAfter = extraAfter(averseLetters, reverseLetters)
    enhancedBefore ++ enhancedAfter
  }

  private def extraBefore(averseLetters: List[String], reverseLetters: List[String])
                         (implicit input: String) = {
    for {
      avers <- averseLetters
      revers <- reverseLetters
      candidate = avers + revers if isValidCandidate(candidate)
    }
      yield candidate
  }

  private def extraAfter(averseLetters: List[String], reverseLetters: List[String])
                        (implicit input: String) = {
    for {
      avers <- averseLetters
      revers <- reverseLetters
      candidate = revers + avers if isValidCandidate(candidate)
    }
      yield candidate
  }

  private def searchPalindrome(input: String): String = {
    val all: Seq[String] = allPalindromes(input)
    val sourted = all.sortWith { (pal1, pal2) =>
      (pal1.head, pal2.head) match {
        case (ch1, ch2) if ch1 == ch2 => pal1.length < pal2.length
        case (ch1, ch2) => ch1 < ch2
      }
    }
    sourted.head
  }

  @Test
  def default1Test(): Unit = {
    val input = "race"

    val actualResult: String = searchPalindrome(input)

    assertEquals("ecarace", actualResult)
  }

  @Test
  def default2Test(): Unit = {
    val input = "google"

    val actualResult: String = searchPalindrome(input)

    assertEquals("elgoogle", actualResult)
  }

  @Test
  def custom1Test(): Unit = {
    val input = "za"

    val actualResult: String = searchPalindrome(input)

    assertEquals("aza", actualResult)
  }

  @Test
  def custom2Test(): Unit = {
    val input = "zzaz"

    val actualResult: String = searchPalindrome(input)

    assertEquals("zzazz", actualResult)
  }
}
