package com.tfedorov

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestBoilerPlot {

  @Test
  def taskTest(): Unit = {

    val actual = 2 + 2

    val expected = 4
    assertEquals(expected, actual)
  }

}
