package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.{assertEquals, assertTrue}
import org.junit.jupiter.api.Test

/**
 *
 * https://alvinalexander.com/scala/scala-collections-classes-methods-organized-category/
 */
class CollectionTest {

  //1) Filtering methods

  /**
   * Tests the `drop` method, which removes the first n elements from the sequence.
   * See [[dropWhile()]]
   */
  @Test
  def drop(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 13, 4, 2, 7, 5)

    val actualResult = input.drop(3)

    assertEquals(Seq(13, 4, 2, 7, 5), actualResult)
  }

  /**
   * Tests the `dropWhile` method, which removes elements from the sequence as long as a predicate is true.
   * See [[drop()]]
   */
  @Test
  def dropWhile(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.dropWhile(_ < 3)

    val expected = Seq(7, 3, 4, 2, 7, 5)
    assertEquals(expected, actualResult)
  }


  /**
   * Tests the `updated` method, which creates a new sequence with an element replaced
   * at the specified index.
   */
  @Test
  def updated(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 13, 4, 5)

    val actualResult: Seq[Int] = input.updated(2, 3)

    assertEquals(Seq(1, 2, 3, 4, 5), actualResult)
  }

  /**
   * Tests the `collect` method, which applies a partial function to elements of the sequence,
   * filtering and transforming elements based on the defined cases.
   */
  @Test
  def collect4Update(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5)

    val actualResult = input.collect {
      case e if e % 2 == 0 => e
      case _ => -1
    }

    assertEquals(List(-1, 2, -1, -1, 4, -1), actualResult)
  }

  /**
   * Tests the `padTo` method, which extends the sequence to a specified length by appending a value.
   */
  @Test
  def padTo(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 3, 4, 5)

    val actualResult1: Seq[Int] = input.padTo(input.length + 3, Int.MaxValue)
    val actualResult2: Seq[Int] = input.padTo(1, Int.MaxValue)

    val expected = List(1, 2, 3, 4, 5, 2147483647, 2147483647, 2147483647)
    assertEquals(expected, actualResult1)
    assertEquals(input, actualResult2)
  }

  /**
   * Tests the `lift` method, which safely accesses an element by index, returning an `Option`.
   */
  @Test
  def liftExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5)

    val actualResult = input.lift(4)

    assertEquals(Some(4), actualResult)
  }

  /**
   * Tests the `lift` method with an existing element at the specified index.
   */
  @Test
  def liftExist2(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5, 4)

    val actualResult = input.lift(4)

    assertEquals(Some(4), actualResult)
  }

  /**
   * Tests the `lift` method with a non-existent index, returning `None`.
   */
  @Test
  def liftNotExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5)

    val actualResult = input.lift(14)

    assertEquals(None, actualResult)
  }


  /**
   * Tests the `containsSlice` method, which checks if a sequence contains a specified subsequence.
   */
  @Test
  def containsSlice(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.containsSlice(3 :: 4 :: 2 :: Nil)

    assertTrue(actualResult)
  }


  /**
   * Tests the `prefixLength` method, which returns the length of the longest prefix that satisfies a predicate.
   * See [[segmentLength()]]
   */
  @Test
  def prefixLength(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.prefixLength(_ < 3)

    assertEquals(2, actualResult)
  }

  /**
   * Tests the `segmentLength` method, which returns the length of the longest segment that satisfies a predicate,
   * starting from a given index.
   * See [[prefixLength()]]
   */
  @Test
  def segmentLength(): Unit = {
    val input: Seq[Int] = Seq(1, 1, 7, 3, 4, 2, 7, 5)

    val actualResult1 = input.segmentLength(_ < 3, from = 5)
    val actualResult2 = input.segmentLength(_ % 2 != 0, from = 0)

    assertEquals(1, actualResult1)
    assertEquals(4, actualResult2)
  }

  /**
   * Tests the `corresponds` method, which checks if two sequences satisfy a predicate pairwise.
   */
  @Test
  def corresponds(): Unit = {
    val input1: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)
    val input2: Seq[Int] = input1.map(_ + 10)

    val actualResult = input1.corresponds(that = input2)(_ < _)

    assertTrue(actualResult)
  }

  //4) Grouping methods


  /**
   * Tests the `span` method, which splits the sequence into a prefix/suffix pair based on a predicate.
   * See [[partition]]
   */
  @Test
  def span(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 13, 4, 2, 7, 5)

    val actualResult: (Seq[Int], Seq[Int]) = input.span(_ < input.max)

    val expected = (List(1, 2, 7), List(13, 4, 2, 7, 5))
    assertEquals(expected, actualResult)
  }

  /**
   * Tests the `partition` method, which splits the sequence into a pair of sequences based on a predicate.
   * See [[span]]
   */
  @Test
  def partition(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 13, 4, 2, 7, 5)

    val actualResult: (Seq[Int], Seq[Int]) = input.partition(_ < input.max)

    val expected = (List(1, 2, 7, 4, 2, 7, 5), List(13))
    assertEquals(expected, actualResult)
  }

  /**
   * Tests the `grouped` method, which splits the sequence into fixed-size chunks.
   * This example groups the elements into sub-sequences of size 3.
   * See [[sliding()]]
   */
  @Test
  def grouped(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.grouped(size = 3).toSeq
    assertEquals(Seq(Seq(1, 2, 7), Seq(3, 4, 2), Seq(7, 5)), actualResult)
  }

  /**
   * Tests the `sliding` method, which returns a sliding window of elements.
   * In this case, the window size is 3, and it slides by 2 positions.
   * See [[grouped()]]
   */
  @Test
  def sliding(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult1 = input.sliding(size = 3, step = 2).toSeq
    val actualResult2 = input.sliding(size = 2, step = 3).toList

    val expected = Seq(Seq(1, 2, 7), Seq(7, 3, 4), Seq(4, 2, 7), Seq(7, 5))
    assertEquals(expected, actualResult1)
    assertEquals(Seq(Seq(1, 2), Seq(3, 4), Seq(7, 5)), actualResult2)
  }


  //5) Informational and mathematical methods

  /**
   * Tests the `find` method, which searches for the first element in the sequence that satisfies a given predicate.
   * In this example, the method looks for the first even number in the sequence.
   *
   * See [[liftExist()]]
   */
  @Test
  def find(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5, 4)

    val actualResult = input.find(_ % 2 == 0)

    assertEquals(Some(2), actualResult)
  }


  @Test
  def count(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 5, 4)

    val actualResult = input.count(_ % 2 == 0)

    assertEquals(3, actualResult)
  }


  /**
   * Tests the `indexOfSlice` method, which finds the starting index of the first occurrence
   * of a subsequence within the sequence.
   */
  @Test
  def indexOfSliceExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.indexOfSlice(2 :: 7 :: Nil)

    assertEquals(1, actualResult)
  }

  /**
   * Tests the `indexOfSlice` method with a non-existent subsequence.
   */
  @Test
  def indexOfSliceNotExist(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.indexOfSlice(2 :: 3 :: Nil)

    assertEquals(-1, actualResult)
  }

  /**
   * Tests the `lastIndexOfSlice` method, which finds the starting index of the last occurrence
   * of a subsequence within the sequence.
   */
  @Test
  def lastIndexOfSlice(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    val actualResult = input.lastIndexOfSlice(2 :: 7 :: Nil)

    assertEquals(5, actualResult)
  }

  /**
   * Tests a sequence of operations combining `zipWithIndex`, `sliding`, and `collect`.
   * This example finds sub-sequences where the middle element is greater than both its neighbors.
   */
  @Test
  def integratedTest1(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    def isInMiddleMore(elements: Seq[(Int, Int)]): Boolean = {
      val elementBefore: Int = elements.head._1
      val currentEl = elements(1)._1
      val elementAfter = elements.last._1
      currentEl > elementBefore && currentEl > elementAfter
    }

    val actualResults = input
      .zipWithIndex
      .sliding(size = 3)
      .collect { case e if isInMiddleMore(e) =>
        e.map(_._1)
      }

    assertEquals(List(Seq(2, 7, 3), Seq(3, 4, 2), Seq(2, 7, 5)), actualResults.toList)
  }

  /**
   * Similar to the `several` test, but this example uses `collectFirst` to find only the first matching sub-sequence.
   */
  @Test
  def integratedTest2(): Unit = {
    val input: Seq[Int] = Seq(1, 2, 7, 3, 4, 2, 7, 5)

    def isInMiddleMore(elements: Seq[(Int, Int)]): Boolean = {
      val elementBefore: Int = elements.head._1
      val currentEl = elements(1)._1
      val elementAfter = elements.last._1
      currentEl > elementBefore && currentEl > elementAfter
    }

    val actualResults = input.zipWithIndex.sliding(size = 3).collectFirst { case e if isInMiddleMore(e) =>
      e.map(_._1)
    }

    assertEquals(List(Seq(2, 7, 3)), actualResults.toList)
  }

}
