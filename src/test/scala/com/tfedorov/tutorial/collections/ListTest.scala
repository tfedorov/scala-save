package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.Test

class ListTest {

  @Test
  def appendPrepend(): Unit = {

    val actualResult = 1 +: List(2, 3) ::: 4 :: Nil

    assertEquals(Seq(1, 2, 3, 4), actualResult)
  }


  @Test
  def append3Flatten(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input ::: List(6, 7)
    //val actualResult = input ++ (6 :: 7 :: Nil)

    assertEquals(List(1, 2, 3, 4, 5, 6, 7), actualResult)
  }

  @Test
  def append2Seq(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :: List(6, 7)
    //val actualResult = input :: 6 :: 7 :: Nil

    assertEquals(List(List(1, 2, 3, 4, 5), 6, 7), actualResult)
  }

  @Test
  def appendNilFlatten(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input ::: Nil

    assertEquals(input, actualResult)
  }

  @Test
  def appendNil(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :: Nil

    assertEquals(List(List(1, 2, 3, 4, 5)), actualResult)
  }

}
