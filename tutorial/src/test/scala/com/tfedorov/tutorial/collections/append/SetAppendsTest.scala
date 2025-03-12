package com.tfedorov.tutorial.collections.append

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


/**
 * Note - no appendPrepend neither not :: nor ::: . But presence --
 */

class SetAppendsTest {

  @Test
  def appendPrepend(): Unit = {

    //    val actualResult = 1 +: Set(2, 3) :+ 4
    //
    //    assertEquals(Set(1, 2, 3, 4), actualResult)
  }

  @Test
  def appendToElement(): Unit = {

    //    val actualResult1 = 1 +: Set(2, 3)
    //Wrong no for Set
    //reverted opeation
    //    val actualResult2 = 1 + Set(2, 3)

    //    assertEquals(Set(1, 2, 3), actualResult2)
  }

  @Test
  def appendElementToSet(): Unit = {
    val input: Set[Int] = Set(1, 2)

    val actualResult = input + 3
    // Wrong it creates a String
    //val wrongActual = input + "3"

    assertEquals(Set(1, 2, 3), actualResult)
  }

  @Test
  def appendElementsToSet(): Unit = {
    val input: Set[Int] = Set(1, 2)

    val actualResult = input ++ Set(3)
    // Wrong it creates a String
    //val wrongActual = input + "3"

    assertEquals(Set(1, 2, 3), actualResult)
  }

  @Test
  def appendVar(): Unit = {
    var actualResult: Set[Int] = Set(1, 2)

    actualResult ++= Set(3)

    assertEquals(Set(1, 2, 3), actualResult)
  }

  @Test
  def appendVar2(): Unit = {
    var actualResult: Set[Int] = Set(1, 2)

    actualResult += 3

    assertEquals(Set(1, 2, 3), actualResult)
  }

  @Test
  def appendSet(): Unit = {
    val input: Set[Int] = Set(1, 2)

    //Wrong no for Set
    //reverse command
    val actualResult1 = input.toList ::: List(3, 4)
    //Wrong creates List(Set(1, 2), 3, 4)
    //val wrong = input :: Set(3, 4)
    val actualResult2 = input ++ Set(3, 4)

    assertEquals(Set(1, 2, 3, 4), actualResult2)
  }

  @Test
  def appendAllHierarchy(): Unit = {
    val input: Set[Any] = Set(1, 2, 3, 4, 5)
    // But not  val input: Set[Int]

    //reverse command
    val actualResult: Set[Any] = input + Set(6, 7)


    assertEquals(Set(1, 2, 3, 4, 5, Set(6, 7)), actualResult)
  }

  @Test
  def appendNilFlatten(): Unit = {
    val input: Set[Int] = Set(1, 2, 3, 4, 5)

    //Wrong no for Set
    //reverse command
    val actualResult = input ++ Set()
    //Seq example
    //val actualResult = input ++ Nil

    assertEquals(input, actualResult)
  }


  @Test
  @Deprecated
  def appendString(): Unit = {
    val input: Set[Int] = Set(1, 2)

    val actualResultImp: String = input + "6"
    //But not
    //val actualResultImp: String = input + 6
    //from package scala
    val actualResultExpl: String = any2stringadd(input) + "6"
    //But not
    //val actualResultExpl: String = any2stringadd(input) + 6

    assertEquals("Set(1, 2)6", actualResultImp)
    assertEquals("Set(1, 2)6", actualResultExpl)
  }

  @Test
  def deleteElement(): Unit = {
    val input: scala.collection.mutable.Set[Int] = scala.collection.mutable.Set(1, 2)

    val actualResult1 = input -= 2
    val actualResult2 = input - 2

    assertEquals(Set(1), actualResult1)
    assertEquals(Set(1), actualResult2)
  }

  @Test
  def deleteElements(): Unit = {
    val input: scala.collection.mutable.Set[Int] = scala.collection.mutable.Set(1, 2)

    val actualResult1 = input --= Set(2)
    val actualResult2 = input -- Set(2)

    assertEquals(Set(1), actualResult1)
    assertEquals(Set(1), actualResult2)
  }
}
