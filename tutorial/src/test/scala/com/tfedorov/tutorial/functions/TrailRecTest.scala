package com.tfedorov.tutorial.functions

import scala.annotation.tailrec

class TrailRecTest {

  import org.junit.jupiter.api.Assertions.assertEquals
  import org.junit.jupiter.api.Test

  @Test
  def trailRecTest(): Unit = {

    @tailrec
    def sum(list: List[Int], accumulator: Int): Int = list match {
      case Nil => accumulator
      case x :: xs => sum(xs, x + accumulator)
    }

    val actualResult = sum(1 :: 9 :: 15 :: Nil, 0)

    assertEquals(25, actualResult)
  }

  @Test
  def noTrailRecTest(): Unit = {
    //@tailrec
    def sum(list: List[Int]): Int = list match {
      case Nil => 0
      case x :: xs => x + sum(xs)
        //Recursive call not in tail position (in @tailrec annotated method)
    }

    val actualResult = sum(1 :: 9 :: 15 :: Nil)

    assertEquals(25, actualResult)
  }
}
