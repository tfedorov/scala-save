package com.tfedorov.inteview.daily_coding_problem

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JaneStreetArbitrageTest {
  @Test
  def defaultTest(): Unit = {
    val inputArr = Seq(
      Seq("ARS","AUD", 0.036093121),
      Seq("AUD","ARS", 0.036093121),
    )

    val actualResult = "1"

    val expectedResult = "2"
    assertEquals(expectedResult, actualResult)
  }
}
