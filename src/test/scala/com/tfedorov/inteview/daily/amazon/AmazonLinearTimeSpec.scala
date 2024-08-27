package com.tfedorov.inteview.daily.amazon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * An sorted array of integers was rotated an unknown number of times.
 *
 * Given such an array, find the index of the element in the array in faster than linear time. If the element doesn't exist in the array, return null.
 *
 * For example, given the array [13, 18, 25, 2, 8, 10] and the element 8, return 4 (the index of 8 in the array).
 *
 * You can assume all the integers in the array are unique.
 */
class AmazonLinearTimeSpec {

  def checkFunc(input: List[Int], seek: Int, prefix: Int = 0): Int = {
    println(input)
    val checkMiddleIdx: Int = input.length / 2
    val lastIdx = input.length - 1
    println(input(checkMiddleIdx))
    (input(0), input(checkMiddleIdx), input(lastIdx)) match {
      case (first, _, _) if first == seek => return prefix
      case (_, middle, _) if middle == seek => return prefix + checkMiddleIdx
      case (_, _, last) if last == seek => return prefix + lastIdx
      case (first, middle, last) if (first < seek && seek < middle) => checkFunc(input.slice(0, checkMiddleIdx), seek, prefix)
      case (first, middle, last) if (middle < seek && seek < last) => checkFunc(input.slice(checkMiddleIdx, lastIdx + 1), seek, prefix + checkMiddleIdx)
      case (first, middle, last) if (middle > seek && last > seek && first > middle) => checkFunc(input.slice(0, checkMiddleIdx), seek, prefix)
      case (first, middle, last) if (middle > seek && last > seek && first < middle) => checkFunc(input.slice(checkMiddleIdx, lastIdx + 1), seek, prefix + checkMiddleIdx)
    }
  }

  @Test
  def taskTest(): Unit = {
    val inputs = Seq(
      List(13, 18, 25, 2, 8, 10),
      List(18, 25, 2, 8, 10, 13),
      List(25, 2, 8, 10, 13, 18),
      List(2, 8, 10, 13, 18, 25),
      List(8, 10, 13, 18, 25, 2),
      List(10, 13, 18, 25, 2, 8),
    )

    val actual = inputs.map(checkFunc(_, 8))

    val expected = 4 :: 3 :: 2 :: 1 :: 0 :: 5 :: Nil
    assertEquals(expected, actual)
  }

  @Test
  def taskTestAnother(): Unit = {
    val inputs = Seq(
      List(15, 25, 3, 8, 9, 11, 12, 13, 14),
      List(25, 3, 8, 9, 11, 12, 13, 14, 15),
      List(3, 8, 9, 11, 12, 13, 14, 15, 25),
      List(11, 12, 13, 14, 15, 25, 3, 8, 9),
    )

    val actual = inputs.map(checkFunc(_, 8))

    val expected = 3 :: 2 :: 1 :: 7 :: Nil
    assertEquals(expected, actual)
  }
}
