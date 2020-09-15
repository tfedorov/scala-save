package com.tfedorov.tutorial.herding_cats

import cats.PartialOrder
import cats.implicits._
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class EqTest {
  @Test
  def equalEqualEqual(): Unit = {

    //false 6 == 6
    //: error: type mismatch;
    //val actualResult: Boolean = 6 === "six"
    val actualResult: Boolean = 6 === 6

    assertTrue(actualResult)
  }

  @Test
  def order(): Unit = {

    val actualResult: Boolean = 1 > 2.0
    //error: type mismatch;
    //val actualResult: Boolean = 1 compare 2.0
    val actualResultTyped: Int = 1.0 compare 2.0

    assertFalse(actualResult)
    assertEquals(-1, actualResultTyped)
  }

  @Test
  def partialOrder(): Unit = {

    val actualResult: Option[Int] = 1 tryCompare 2

    assertEquals(Some(-1), actualResult)
  }

  @Test
  def partialOrderFunc(): Unit = {
    def lt[A: PartialOrder](a1: A, a2: A): Boolean = a1 < a2

    //val actualResult: Option[Int] =  lt[Int](1, 2.0)
    val actualResult = lt[Int](1, 2)

    assertTrue(actualResult)
  }

}
