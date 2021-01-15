package com.tfedorov.inteview.daily

/*
Medium Facebook.

You are given an array of non-negative integers that represents a
two-dimensional elevation map where each element is unit-width wall and the integer is the height. Suppose it will rain and all spots between two walls get filled up.

Compute how many units of water remain trapped on the map in O(N) time and O(1) space.

For example, given the input [2, 1, 2], we can hold 1 unit of water in the middle.

Given the input [3, 0, 1, 3, 0, 5], we can hold 3 units in the first index, 2 in the second, and 3 in the fourth index
(we cannot hold 5 since it would run off to the left), so we can trap 8 units of water.
 */

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FacebookWaterTest {

  private case class Wall(value: Int, index: Int)

  private case class InnerPool(left: Wall, right: Wall) {
    lazy val height: Int = Math.min(left.value, right.value)

    def divideIfHigher(middle: Wall): Seq[InnerPool] = {
      if (height > middle.value)
        this :: Nil
      else
        InnerPool(left, middle) :: InnerPool(middle, right) :: Nil
    }
  }

  def howMuchWater(input: Seq[Int]): Int = {

    val allPool = InnerPool(Wall(input.head, 0), Wall(input.last, input.length - 1))
    val pools = input.zipWithIndex.map(t => Wall(t._1, t._2))
      .foldLeft(Seq(allPool)) { (pools, wall) => pools.init ++ (pools.last divideIfHigher wall) }
    pools.foldLeft(0) { (sumAgg, pool) =>
      val poolWallHeights = input.slice(pool.left.index, pool.right.index + 1)
      sumAgg + poolWallHeights.map { wallHeight =>
        Math.max(pool.height, wallHeight) - wallHeight
      }.sum
    }
  }

  @Test
  def default1Test(): Unit = {
    val input = Seq(3, 0, 1, 3, 0, 5)

    val actualResult: Int = howMuchWater(input)

    val expectedResult = 8
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def default2Test(): Unit = {
    val input = 2 :: 1 :: 2 :: Nil

    val actualResult: Int = howMuchWater(input)

    val expectedResult = 1
    assertEquals(expectedResult, actualResult)
  }


  @Test
  def custom1Test(): Unit = {
    val input = Seq(3, 0, 4, 3, 6, 4, 5)

    val actualResult: Int = howMuchWater(input)

    val expectedResult = 5
    assertEquals(expectedResult, actualResult)
  }

}
