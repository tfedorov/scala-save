package com.tfedorov.tutorial.collections.append

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.mutable.ListBuffer

/**
 * Note- There are no neither -= nor --= . But present  ::  nor ::: (flatten) operations
 */
class ListAppendsTest {
  @Test
  def appendPrepend(): Unit = {
    // Note : where is collection , + where is element
    val actualResult = 1 +: List(2, 3) :+ 4

    assertEquals(List(1, 2, 3, 4), actualResult)
  }

  @Test
  def appendToElement(): Unit = {

    val actualResult1 = 1 +: List(2, 3)
    val actualResult2 = 1 :: List(2, 3)

    assertEquals(List(1, 2, 3), actualResult1)
    assertEquals(List(1, 2, 3), actualResult2)
  }

  @Test
  def appendElementToList(): Unit = {
    val input: List[Int] = List(1, 2)

    val actualResult = input :+ 3
    // Wrong it creates a String
    //val wrongActual = input + "3"

    assertEquals(List(1, 2, 3), actualResult)
  }

  @Test
  def appendVar(): Unit = {
    var actualResult: List[Int] = List(1, 2)

    actualResult ++= 3 :: Nil

    assertEquals(List(1, 2, 3), actualResult)
  }

  @Test
  def appendList(): Unit = {
    val input: List[Int] = List(1, 2)

    //flatten
    val actualResult1 = input ::: List(3, 4)
    //Wrong creates List(List(1, 2), 3, 4)
    //val wrong = input :: List(3, 4)
    val actualResult2 = input ++ List(3, 4)

    assertEquals(List(1, 2, 3, 4), actualResult1)
    assertEquals(List(1, 2, 3, 4), actualResult2)
  }

  @Test
  def appendAllHierarchy(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :: List(6, 7)
    //val actualResult = input :: 6 :: 7 :: Nil

    assertEquals(List(List(1, 2, 3, 4, 5), 6, 7), actualResult)
  }

  @Test
  def appendNilFlatten(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input ::: Nil
    //Seq example
    //val actualResult = input ++ Nil

    assertEquals(input, actualResult)
  }

  @Test
  def appendNilHierarchy(): Unit = {
    val input: List[Int] = List(1, 2, 3, 4, 5)

    val actualResult = input :: Nil

    assertEquals(List(List(1, 2, 3, 4, 5)), actualResult)
  }

  @Test
  @Deprecated
  def appendString(): Unit = {
    val input: List[Int] = List(1, 2)

    val actualResultImp: String = input + "6"
    //But not
    //val actualResultImp: String = input + 6
    //from package scala
    val actualResultExpl: String = any2stringadd(input) + "6"
    //But not
    //val actualResultExpl: String = any2stringadd(input) + 6

    assertEquals("List(1, 2)6", actualResultImp)
    assertEquals("List(1, 2)6", actualResultExpl)
  }

  @Test
  def deleteElements(): Unit = {
    val input: List[Int] = List(0, 1, 2, 3, 4)

    val actualResult = input.take(2) ++ input.drop(3)
    // Not preset for List
    //    input -= 2
    //    input --= 2

    assertEquals(List(0, 1, 3, 4), actualResult)
    assertEquals(List(0, 1, 3, 4), input.to[ListBuffer] -= 2)
    assertEquals(List(0, 1, 3), input.to[ListBuffer] --= List(2, 4))
  }

}
