package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
//
//This problem was asked by Two Sigma.
//
//Using a function rand5() that returns an integer from 1 to 5 (inclusive) with uniform probability,
// implement a function rand7() that returns an integer from 1 to 7 (inclusive).
//

class Rand5 {

  def rand(limit: Int): Int = {
    val divider = 5.0 / limit
    val fistRand = rand5()
    if (fistRand / 5.0 <= divider)
      return rand5()

    val secondRand = rand5()
    val s = secondRand / 5.0
    val add = (s * (limit - 5) - 0.0000001).toInt
    6 + add
  }

  def rand5(): Int = {
    (Math.random() * 5).toInt + 1
  }

  @Test
  def rand7Test(): Unit = {

    val actualResults = (1 to 10000).map(_ => rand(7))
    println(actualResults.groupBy(identity).mapValues(i => i.length))

    assertEquals(1, actualResults.min)
    assertEquals(7, actualResults.max)

  }

  @Test
  def rand9Test(): Unit = {

    val actualResults = (1 to 10000).map(_ => rand(9))
    println(actualResults.groupBy(identity).mapValues(i => i.length))

    assertEquals(1, actualResults.min)
    assertEquals(9, actualResults.max)

  }
}
