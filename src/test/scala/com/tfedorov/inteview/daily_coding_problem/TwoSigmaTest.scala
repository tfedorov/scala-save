package com.tfedorov.inteview.daily_coding_problem

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.util.Random

/**
 * Using a function rand7() that returns an integer from 1 to 7 (inclusive) with uniform probability, implement a function rand5() that returns an integer from 1 to 5 (inclusive).
 */
class TwoSigmaTest {

  @Test
  def getRandom5Test(): Unit = {

    val actualResult: Seq[Int] = (1 to 100000).map(_ => getRandom5())
    val actual: Map[Int, Int] = actualResult.groupBy(identity).mapValues(_.sum)

    val expectedResult = Map.empty[Int, Int]
    assertEquals(expectedResult, actual)
  }

  private def getRandom5(): Int = {
    val gen7 = Random.nextInt(7) + 1

    math.round((gen7 * 5) / 7.0).toInt

  }
}
