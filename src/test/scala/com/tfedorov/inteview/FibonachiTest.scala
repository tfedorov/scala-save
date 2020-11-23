package com.tfedorov.inteview

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.annotation.tailrec

class FibonachiTest {

  @Test
  def testSum8(): Unit = {
    val target = 8

    @tailrec def calculate(aggSum: Int, current: Int, currentMinus1: Int, number: Int): Int = number match {
      case 0 => aggSum
      case _ => calculate(aggSum + current, current + currentMinus1, current, number - 1)
    }
    val actualResult = calculate(1, 1, 1, target - 1)

    assertEquals(54, actualResult)
  }
}
