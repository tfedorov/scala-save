package com.tfedorov.inteview.daily.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
Easy Facebook.

Given a string of round, curly, and square open and closing brackets, return whether the brackets are balanced (well-formed).

For example, given the string "([])[]({})", you should return true.

Given the string "([)]" or "((()", you should return false.
 */
class FacebookCurlTest {

  def isEcnlosed(input: String): Boolean = {
    input.foldLeft("") { (openedBracesAgg, char) =>
      if ("([{".contains(char))
        openedBracesAgg + char
      else {
        (openedBracesAgg.lastOption, char) match {
          case (Some('('), ')') => openedBracesAgg.init
          case (Some('['), ']') => openedBracesAgg.init
          case (Some('{'), '}') => openedBracesAgg.init
          case _ => return false
        }
      }
    }.isEmpty
  }

  @Test
  def defaultTrueTest(): Unit = {
    val input = "([])[]({})"

    val actualResult: Boolean = isEcnlosed(input)

    assertEquals(true, actualResult)
  }

  @Test
  def defaultFalse1Test(): Unit = {
    val input = "([)]"

    val actualResult: Boolean = isEcnlosed(input)

    assertEquals(false, actualResult)
  }

  @Test
  def defaultFalse2Test(): Unit = {
    val input = "((()"

    val actualResult: Boolean = isEcnlosed(input)

    assertEquals(false, actualResult)
  }

  @Test
  def changedFalseTest(): Unit = {
    val input = "([]))[]({})"

    val actualResult: Boolean = isEcnlosed(input)

    assertEquals(false, actualResult)
  }
}
