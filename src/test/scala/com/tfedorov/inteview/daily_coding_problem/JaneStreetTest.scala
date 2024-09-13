package com.tfedorov.inteview.daily_coding_problem

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.immutable.Stream.cons

/*
cons(a, b) constructs a pair, and car(pair) and cdr(pair) returns the first and last element of that pair.
For example, car(cons(3, 4)) returns 3, and cdr(cons(3, 4)) returns 4.
 */
class JaneStreetTest {

  def car(pair: (Int, Int)): Int = Math.max(pair._1, pair._2)

  def cdr(pair: (Int, Int)): Int = Math.min(pair._1, pair._2)

  @Test
  def carTest(): Unit = {
    val pair = (3, 4)

    val actualResult = car(pair)

    val expectedResult = 4
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def cdrTest(): Unit = {
    val pair = (3, 4)

    val actualResult = cdr(pair)

    val expectedResult = 3
    assertEquals(expectedResult, actualResult)
  }

  def consOur[T](first: T, last: T)(implicit conv: T => Number): () => T = {
    def car(): T = {
      if (conv(first).floatValue() > conv(last).floatValue())
        first
      else
        last
    }

    car
  }

  @Test
  def consOurTestF(): Unit = {
    val car = consOur(3, 4)

    val actualResult = car()

    val expectedResult = 4
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def consOurFloatTestF(): Unit = {
    val car = consOur(3.0, 4.0)

    val actualResult = car()

    val expectedResult = 4
    assertEquals(expectedResult, actualResult)
  }
}
