package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

import scala.collection.SortedSet

/*
Given a list of numbers and a number k, return whether any two numbers from the list add up to k.

For example, given [10, 15, 3, 7] and k of 17, return true since 10 + 7 is 17.

Bonus: Can you do this in one pass?

 */

//https://dev.to/awwsmm/scala-daily-coding-problem-001-fi2
class Taks1 {

  private def recCheck(input: Seq[Int], neededSum: Int): Boolean = {
    if (input.isEmpty)
      return false
    recursiveCheck(input.head, input.tail)(neededSum)
  }

  private def recursiveCheck(checkingEl: Int, checkingSeq: Seq[Int])(implicit neededSum: Int): Boolean =
    checkingSeq match {
      case Nil => false
      case checkingElMatch if checkingElMatch.exists(_ + checkingEl == neededSum) => true
      case noMatchCheckingEl => recursiveCheck(noMatchCheckingEl.head, noMatchCheckingEl.tail)
    }

  private def combCheck(inputChecked: Seq[Int], neededSum: Int): Boolean = inputChecked.combinations(2).exists(_.sum == neededSum)

  @Test
  def task1Test(): Unit = {

    val input = Seq(10, 15, 3, 7)

    assertTrue(recCheck(input, 17))
    assertTrue(combCheck(input, 17))
    assertFalse(recCheck(input, 19))
    assertFalse(combCheck(input, 19))
  }

}
