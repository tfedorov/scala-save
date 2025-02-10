package com.tfedorov.inteview.daily_coding_problem.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
Given a list of integers, return the largest product that can be made by multiplying any three integers.

For example, if the list is [-10, -10, 5, 2], we should return 500, since that's -10 * -10 * 5.

You can assume the list has at least three integers.
 */
class ThreeMax {

  @Test
  def defaultTest(): Unit = {
    val input = Array(-10, -10, 5, 2)

    val actualResult = returnMax(input)

    val expectedResult = 500
    assertEquals(expectedResult, actualResult)
  }

  private def returnMax(input: Array[Int]): Int = input.combinations(3).map(_.product).max

}
