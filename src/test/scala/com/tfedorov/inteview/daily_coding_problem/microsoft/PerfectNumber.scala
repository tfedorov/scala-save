package com.tfedorov.inteview.daily_coding_problem.microsoft

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.math._

/*
A number is considered perfect if its digits sum up to exactly 10.

Given a positive integer n, return the n-th perfect number.

For example, given 1, you should return 19. Given 2, you should return 28.
 */
class PerfectNumber {

  @Test
  def findPerfectTest(): Unit = {
    val input = Seq(1, 2, 12, 27, 305, 99)

    val actualResult = input.map(findPerfect)

    val expectedResult = List(19, 28, 127, 271, 3052, 181)
    assertEquals(expectedResult, actualResult)
  }

  private def findPerfect(input: Int): Int = {
    val maxRange = (1 to 10).find(r => pow(10, r) > input).get

    val digits = (1 to maxRange).map(r => pow(10, r).toInt).reverse.foldLeft((Seq.empty[Int], 0)) { (agg, r) =>
      val (digits, numberForRange) = agg
      val division = if (r == 10) 1 else r / 10
      val result = (input - agg._2) / division

      (digits :+ result, numberForRange + result * division)
    }._1

    val lastNumber = 10 - digits.sum
    if (lastNumber < 0)
      return findPerfect(digits.sum)
    input * 10 + lastNumber
  }

}
