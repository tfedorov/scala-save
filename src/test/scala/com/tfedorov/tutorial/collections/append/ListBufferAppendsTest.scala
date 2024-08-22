package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.mutable.ListBuffer

/**
 * Note- There are no :: & ::: operations but present -= & --=
 *
 * There are no neither :: nor ::: operations. But present -= & --=
 */
class ListBufferAppendsTest {

  @Test
  def appendPrepend(): Unit = {

    val actualResult = 1 +: ListBuffer(2, 3) :+ 4

    assertEquals(ListBuffer(1, 2, 3, 4), actualResult)
  }

  @Test
  def appendToElement(): Unit = {

    val actualResult1 = 1 +: ListBuffer(2, 3)
    //Wrong no for ListBuffer
    //reverted opeation
    //val actualResult2 = 1 :: ListBuffer(2, 3)

    assertEquals(ListBuffer(1, 2, 3), actualResult1)
  }

  @Test
  def appendElementToListBuffer(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2)

    val actualResult = input :+ 3
    // Wrong it creates a String
    //val wrongActual = input + "3"

    assertEquals(ListBuffer(1, 2, 3), actualResult)
  }

  @Test
  def appendVar(): Unit = {
    var actualResult: ListBuffer[Int] = ListBuffer(1, 2)

    actualResult ++= 3 :: Nil

    assertEquals(ListBuffer(1, 2, 3), actualResult)
  }

  @Test
  def appendVar2(): Unit = {
    var actualResult: ListBuffer[Int] = ListBuffer(1, 2)

    actualResult += 3

    assertEquals(ListBuffer(1, 2, 3), actualResult)
  }

  @Test
  def appendList(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2)

    //Wrong no for ListBuffer
    //reverse command
    val actualResult1 = input.toList ::: List(3, 4)
    //Wrong creates List(ListBuffer(1, 2), 3, 4)
    //val wrong = input :: ListBuffer(3, 4)
    val actualResult2 = input ++ ListBuffer(3, 4)

    assertEquals(List(1, 2, 3, 4), actualResult2)
  }

  @Test
  def appendAllHierarchy(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2, 3, 4, 5)

    //reverse command
    val actualResult = input :: List(6, 7)
    //val actualResult = input :: 6 :: 7 :: Nil

    assertEquals(List(List(1, 2, 3, 4, 5), 6, 7), actualResult)
  }

  @Test
  def appendNilFlatten(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2, 3, 4, 5)

    //Wrong no for ListBuffer
    //reverse command
    val actualResult = input.toList ::: Nil
    //Seq example
    //val actualResult = input ++ Nil

    assertEquals(input, actualResult)
  }

  @Test
  def appendNilHierarchy(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2, 3, 4, 5)

    val actualResult = input :: Nil

    assertEquals(List(ListBuffer(1, 2, 3, 4, 5)), actualResult)
  }

  @Test
  @Deprecated
  def appendString(): Unit = {
    val input: ListBuffer[Int] = ListBuffer(1, 2)

    val actualResultImp: String = input + "6"
    //But not
    //val actualResultImp: String = input + 6
    //from package scala
    val actualResultExpl: String = any2stringadd(input) + "6"
    //But not
    //val actualResultExpl: String = any2stringadd(input) + 6

    assertEquals("ListBuffer(1, 2)6", actualResultImp)
    assertEquals("ListBuffer(1, 2)6", actualResultExpl)
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
