package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.mutable.ListBuffer

class ListBufferAppendsTest {

  @Test
  def appendPrepend(): Unit = {

    val actualResult1 = 1 +: ListBuffer(2, 3) :+ 4

    assertEquals(ListBuffer(1, 2, 3, 4), actualResult1)
  }

  @Test
  def appendElement(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :+ 6
    //The same as
    //val actualResult = input.:+(6)
    //The same as
    //val actualResult2 = input ++ (6 :: Nil)

    assertEquals(Seq(1, 2, 3, 4, 5, 6), actualResult)
  }

  @Test
  def appendVar(): Unit = {
    var actualResult: ListBuffer[Int] = ListBuffer(1, 2, 3, 4, 5)

    actualResult ++= 6 :: Nil

    assertEquals(ListBuffer(1, 2, 3, 4, 5, 6), actualResult)
  }

  @Test
  def appendAll(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2, 3, 4, 5)

    val actualResult = input ++ ListBuffer(6, 7)

    assertEquals(ListBuffer(1, 2, 3, 4, 5, 6, 7), actualResult)
  }

  @Test
  def append2Element(): Unit = {

    val actualResult = 0 +: ListBuffer(1, 2)

    assertEquals(ListBuffer(0, 1, 2), actualResult)
  }

  @Test
  @Deprecated
  def prepend(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2, 3, 4, 5)

    //Bun not
    // val actualResult = input +: 0
    val actualResult = input.+:(0)
    // The same us
    // val actualResult = 0 +: input

    assertEquals(ListBuffer(0, 1, 2, 3, 4, 5), actualResult)
  }

  @Test
  @Deprecated
  def appendString(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2)

    val actualResult: String = input + "6"

    assertEquals("ListBuffer(1, 2)6", actualResult)
  }

  @Test
  def deleteElement(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2)

    val actualResult = input -= 2

    assertEquals(ListBuffer(1), actualResult)
  }

  @Test
  def deleteElements(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2)

    val actualResult = input --= ListBuffer(2)

    assertEquals(ListBuffer(1), actualResult)
  }
}
