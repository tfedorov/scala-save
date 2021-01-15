package com.tfedorov.inteview.daily.google

/*
This problem was asked by Google.

The area of a circle is defined as πr^2. Estimate π to 3 decimal places using a Monte Carlo method.

Hint: The basic equation of a circle is x2 + y2 = r2.
 */
class GoogleCircleTest {

  import org.junit.jupiter.api.Assertions.assertEquals
  import org.junit.jupiter.api.Test

  def shots(): Boolean = {
    val x = Math.random()
    val y = Math.random()
    x * x + y * y < 1
  }

  def equalPi(): String = {
    var counter = 1
    var exist = 1
    while (counter < 10000000) {
      counter += 1
      if (shots())
        exist += 1
    }
    val res = 4.0 * exist / counter
    f"$res%1.3f"
  }

  @Test
  def circleTest(): Unit = {

    val actualResult = equalPi()

    val expectedResult = "3.1412"
    assertEquals(expectedResult, actualResult)
  }

}
