package com.tfedorov.inteview.daily_coding_problem.facebook

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import scala.util.Random
/*
This problem was asked by Facebook.

Given a stream of elements too large to store in memory, pick a random element from the stream with uniform probability.
 */
class FacebookStreamTest {

  @Test
  def streamTest(): Unit = {
    val random = new Random()

    val stream = Stream.from(0)
    val element2Find = random.nextInt(10000)

    val actualResult: String = stream.view(element2Find, element2Find + 1).map { i => println(i); "" + i + random.nextPrintableChar() + random.nextPrintableChar() }.head

    assertTrue(actualResult.startsWith("" + element2Find))
  }
}
