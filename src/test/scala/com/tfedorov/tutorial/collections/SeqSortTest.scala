package com.tfedorov.tutorial.collections

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeqSortTest {

  /**
   * Tests the `sortWith` method, which sorts the sequence based on a comparator function.
   */
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

  /**
   * Tests the `sorted` method, where elements are sorted based on a custom ordering.
   * In this example, even numbers are prioritized over odd ones, and within each category,
   * the elements are sorted in descending order.
   */
  @Test
  def sorted(): Unit = {
    val input: Seq[Int] = 1 to 10

    val explicitOrd: Ordering[Int] = new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = {
        if (x % 2 == 0 && y % 2 != 0) -1
        else if (x % 2 != 0 && y % 2 == 0) 1
        else y.compareTo(x)
      }
    }

    val actualResult = input.sorted(explicitOrd)
    assertEquals(Seq(10, 8, 6, 4, 2, 9, 7, 5, 3, 1), actualResult)
  }

  /**
   * Tests the `sortBy` method, where elements are sorted based on a function that transforms them.
   * Here, vowels are prioritized by assigning them a special value, and then the sequence is sorted accordingly.
   */
  @Test
  def sortBy(): Unit = {
    val input: Seq[Char] = 'A' to 'Z'

    val actualResult = input.sortBy[Int] {
      case vovel if "AEIOU".contains(vovel) => -1
      case _ => 1
    }(Ordering.Int)

    assertEquals(Seq('A', 'E', 'I', 'O', 'U', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'), actualResult)
  }

}
