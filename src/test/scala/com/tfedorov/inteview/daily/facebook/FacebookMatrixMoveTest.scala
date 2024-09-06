package com.tfedorov.inteview.daily.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 *
 * There is an N by M matrix of zeroes. Given N and M, write a function to count the number of ways of starting at the top-left corner and getting to the bottom-right corner. You can only move right or down.
 *
 * For example, given a 2 by 2 matrix, you should return 2, since there are two ways to get to the bottom-right:
 *
 * Right, then down
 * Down, then right
 * Given a 5 by 5 matrix, there are 70 ways to get to the bottom-right.
 */
class FacebookMatrixMoveTest {

  def buildMatrix(n: Int, m: Int): Seq[Seq[Int]] =
    (0 to m - 1).map(_ => (0 to m - 1).map(_ => 0))


  def printMatrix(input: Seq[Seq[Int]]) {
    println("\n[")
    input.foreach(r => println(r.mkString(" ")))
    println("]\n")
  }


  def matrixMove(input: Seq[Seq[Int]], current: (Int, Int) = (0, 0), agg: Int = 0): Int = {
    if (current == (input.length - 1, input.head.length - 1))
      return agg + 1
    if (current._1 >= input.length)
      return agg

    if (current._2 >= input.head.length)
      return agg
    val (currentX, currentY) = current
    val changed = input.updated(currentY, input(currentY).updated(currentX, 1))
    printMatrix(changed)
    val rightMove = matrixMove(changed, (current._1 + 1, current._2), agg)
    val leftMove = matrixMove(changed, (current._1, current._2 + 1), agg)
    rightMove + leftMove
  }

  @Test
  def matrixMoveTest(): Unit = {
    val input = buildMatrix(2, 2)
    printMatrix(input)

    val actualResult = matrixMove(input)

    val expectedResult = 2
    assertEquals(expectedResult, actualResult)
  }


  @Test
  def matrixMove5Test(): Unit = {
    val input = buildMatrix(5, 5)
    printMatrix(input)

    val actualResult = matrixMove(input)

    val expectedResult = 70
    assertEquals(expectedResult, actualResult)
  }
}
