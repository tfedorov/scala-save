package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.Test

class SeqTest {

  @Test
  def updated(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 13, 4, 5)

    val actualResult: Seq[Int] = input.updated(2, 3)

    assertEquals(Seq(1, 2, 3, 4, 5), actualResult)
  }

  @Test
  def collect4Update(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5)

    val actualResult = input.collect {
      case e if e % 2 == 0 => e
      case _ => -1
    }

    assertEquals(List(-1, 2, -1, -1, 4, -1), actualResult)
  }

  @Test
  def padTo(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    val actualResult: Seq[Int] = input.padTo(input.length + 3, 2)

    assertEquals(input.toList ::: (2 :: 2 :: 2 :: Nil), actualResult)
  }

  @Test
  def liftExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5)

    val actualResult = input.lift(4)

    assertEquals(Some(4), actualResult)
  }

  @Test
  def liftExist2(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5, 4)

    val actualResult = input.lift(4)

    assertEquals(Some(4), actualResult)
  }

  @Test
  def liftNotExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5)

    val actualResult = input.lift(14)

    assertEquals(None, actualResult)
  }

  @Test
  def indexOfSliceExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.indexOfSlice(2 :: 7 :: Nil)

    assertEquals(1, actualResult)
  }

  @Test
  def indexOfSliceNotExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.indexOfSlice(12 :: 7 :: Nil)

    assertEquals(-1, actualResult)
  }

  @Test
  def lastIndexOfSlice(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.lastIndexOfSlice(2 :: 7 :: Nil)

    assertEquals(5, actualResult)
  }

  @Test
  def containsSlice(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.containsSlice(3 :: 4 :: 2 :: Nil)

    assertTrue(actualResult)
  }

  @Test
  def drop(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 13, 4, 2, 7, 5)

    val actualResult = input.drop(3)

    assertEquals(Seq(13, 4, 2, 7, 5), actualResult)
  }

  @Test
  def span(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 13, 4, 2, 7, 5)

    val actualResult = input.span(_ < input.max)

    assertEquals((1 :: 2 :: 7 :: Nil, input.drop(3)), actualResult)
  }

  @Test
  def partition(): Unit = {
    val input: Seq[Int] = 1 :: 2 :: 7 :: 3 :: 4 :: 2 :: 7 :: 5 :: Nil

    val actualResult = input.partition(_ < 3)

    assertEquals((1 :: 2 :: 2 :: Nil, 7 :: 3 :: 4 :: 7 :: 5 :: Nil), actualResult)
  }

  @Test
  def dropWhile(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.dropWhile(_ < 3)

    assertEquals(input.tail.tail, actualResult)
  }

  @Test
  def prefixLength(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.prefixLength(_ < 3)

    assertEquals(2, actualResult)
  }

  @Test
  def segmentLength(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.segmentLength(_ < 3, 5)

    assertEquals(1, actualResult)
  }

  @Test
  def corresponds(): Unit = {
    val input1: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)
    val input2: Seq[Int] = input1.map(_ + 10)

    val actualResult = input1.corresponds(input2)(_ < _)

    assertTrue(actualResult)
  }

  @Test
  def sortWith(): Unit = {
    val input: Seq[Int] = 1 to 10

    val actualResult = input.sortWith { (el1, el2) =>
      (el1, el2) match {
        case (p1, np2) if p1 % 2 == 0 && np2 % 2 != 0 => true
        case (np1, p2) if np1 % 2 != 0 && p2 % 2 == 0 => false
        case _ => el1 > el2
      }
    }

    assertEquals(Seq(10, 8, 6, 4, 2, 9, 7, 5, 3, 1), actualResult)
  }

  @Test
  def sorted(): Unit = {
    val input: Seq[Int] = 1 to 10

    val explicitOrd: Ordering[Int] = new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = {
        if (x % 2 == 0 && y % 2 != 0)
          return -1
        if (x % 2 != 0 && y % 2 == 0)
          return 1

        y.compareTo(x)
      }
    }
    val actualResult = input.sorted(explicitOrd)

    assertEquals(Seq(10, 8, 6, 4, 2, 9, 7, 5, 3, 1), actualResult)
  }

  @Test
  def sortBy(): Unit = {
    val input: Seq[Char] = 'A' to 'Z'

    val actualResult = input.sortBy((a: Char) => a match {
      case vovel if "AEIOU".contains(vovel) => -1
      case _ => 1
    })

    assertEquals(Seq('A', 'E', 'I', 'O', 'U', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'), actualResult)
  }

  @Test
  def grouped(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.grouped(3).toSeq
    //val actualResult = input.sliding(3,3).toSeq

    assertEquals(Seq(1, 2, 7) :: Seq(3, 4, 2) :: Seq(7, 5) :: Nil, actualResult)
  }

  @Test
  def several(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    def isInMiddleMore(elements: Seq[(Int, Int)]): Boolean = {
      val elementBefore: Int = elements.head._1
      val currentEl = elements(1)._1
      val elementAfter = elements.last._1
      currentEl > elementBefore && currentEl > elementAfter
    }

    val actualResults = input
      .zipWithIndex
      .sliding(3)
      .collect { case e if isInMiddleMore(e) =>
        e.map(_._1)
      }

    assertEquals(List(2 :: 7 :: 3 :: Nil, 3 :: 4 :: 2 :: Nil, 2 :: 7 :: 5 :: Nil), actualResults.toList)
  }

  @Test
  def several2(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    def isInMiddleMore(elements: Seq[(Int, Int)]): Boolean = {
      val elementBefore: Int = elements.head._1
      val currentEl = elements(1)._1
      val elementAfter = elements.last._1
      currentEl > elementBefore && currentEl > elementAfter
    }

    val actualResults = input.zipWithIndex.sliding(3).collectFirst { case e if isInMiddleMore(e) =>
      e.map(_._1)
    }

    assertEquals(List(2 :: 7 :: 3 :: Nil), actualResults.toList)
  }


}
