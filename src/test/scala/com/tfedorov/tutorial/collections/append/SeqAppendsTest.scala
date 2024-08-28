package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *  There are no neither -= nor --= nor ::  nor ::: operations
 */
class SeqAppendsTest {

  @Test
  def appendPrepend(): Unit = {
    // Note : where is collection , + where is element
    val actualResult = 1 +: Seq(2, 3) :+ 4

    assertEquals(Seq(1, 2, 3, 4), actualResult)
  }

  @Test
  def appendToElement(): Unit = {

    val actualResult1 = 1 +: Seq(2, 3)
    //Wrong no for ListBuffer
    val actualResult2 = 1 :: Seq(2, 3).toList
    //    val actualResult2 = 1 :: 2 :: 3 :: Nil

    assertEquals(Seq(1, 2, 3), actualResult1)
    assertEquals(Seq(1, 2, 3), actualResult2)
  }

  @Test
  def appendElementToSeq(): Unit = {
    val input: Seq[Int] = Seq(1, 2)

    val actualResult = input :+ 3
    // Wrong it creates a String
    //val wrongActual = input + "3"

    assertEquals(Seq(1, 2, 3), actualResult)
  }


  @Test
  def appendVar(): Unit = {
    var actualResult: Seq[Int] = Seq(1, 2)

    actualResult ++= 3 :: Nil
    //Wrong to string
    //actualResult += "3"

    assertEquals(Seq(1, 2, 3), actualResult)
  }

  @Test
  def appendSeq(): Unit = {
    val input: Seq[Int] = Seq(1, 2)
    //Wrong no for ListBuffer
    //reverse command
    //Wrong creates List(Seq(1, 2), 3, 4)
    //val wrong = input :: Seq(3, 4)
    val actualResult = input ++ Seq(3, 4)

    assertEquals(Seq(1, 2, 3, 4), actualResult)
  }

  @Test
  def appendAllHierarchy(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    //reverse command
    val actualResult = input :: List(6, 7)
    //val actualResult = input :: 6 :: 7 :: Nil

    assertEquals(List(Seq(1, 2, 3, 4, 5), 6, 7), actualResult)
  }

  @Test
  def appendNilFlatten(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    //Wrong no for Seq
    val actualResult = (input :: Nil).flatten
    //Seq example
    //val actualResult = input ++ Nil

    assertEquals(input, actualResult)
  }

  @Test
  def appendNilHierarchy(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    val actualResult = input :: Nil

    assertEquals(Seq(Seq(1, 2, 3, 4, 5)), actualResult)
  }

  @Test
  @Deprecated
  def appendString(): Unit = {
    val input: Seq[Int] = Seq(1, 2)

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
  def deleteElement(): Unit = {
    //    val input: Seq[Int] = Seq(1, 2)
    //
    //    val actualResult = input -= 2
    //
    //    assertEquals(ListBuffer(1), actualResult)
  }

  @Test
  def deleteElements(): Unit = {
    //    val input: Seq[Int] = Seq(1, 2)
    //
    //    val actualResult = input --= Seq(2)
    //
    //    assertEquals(Seq(1), actualResult)
  }

}
