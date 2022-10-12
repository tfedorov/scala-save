package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Temp {

  def example(input: String): String = {
    ""
  }

  @Test
  def exampleTest(): Unit = {
    val input = "input"

    val actualResult = example(input)

    val expectedResult = "2"
    assertEquals(expectedResult, actualResult)
  }
}
