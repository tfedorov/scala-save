package com.tfedorov.tutorial.math

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MathTest {
  @Test
  def powTest(): Unit = {
    val input = 3

    val result = Math.pow(3, 2)

    assertEquals(9.0f, result)
  }

  @Test
  def expTest(): Unit = {
    val input = 3

    val result = Math.exp(input)

    assertEquals(20.085536923187668, result)
  }

  @Test
  def logTest(): Unit = {
    val input = 100

    val result = Math.log(input)

    assertEquals(4.605170185988092, result)
  }

  @Test
  def log10Test(): Unit = {
    val input = 100

    val result = Math.log10(input)

    assertEquals(2.0, result)
  }

  @Test
  def sqrtTest(): Unit = {

    val result = Math.sqrt(81.0f)

    assertEquals(9.0, result)
  }

}
