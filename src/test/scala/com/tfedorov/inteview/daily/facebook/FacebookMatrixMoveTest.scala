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
    (0 until n).map(_ => (0 until m).map(_ => 0))


  def printMatrix(input: Seq[Seq[Int]]): Unit = {
    println("\n[")
    input.foreach(r => println(r.mkString(" ")))
    println("]\n")
  }


  private def matrixMove(inputMatrix: Seq[Seq[Int]], currentMove: (Int, Int) = (0, 0), agg: Int = 0): Int =
    currentMove match {
      case (currentX, _) if currentX >= inputMatrix.head.length => agg
      case (_, currentY) if currentY >= inputMatrix.length => agg
      case (currentX, currentY) if currentX >= inputMatrix.head.length - 1 && currentY >= inputMatrix.length - 1 => agg + 1
      case (currentX, currentY) =>
        val matrixWithMove = inputMatrix.updated(currentY, inputMatrix(currentY).updated(currentX, 1))
        printMatrix(matrixWithMove)
        val rightMove = matrixMove(matrixWithMove, (currentMove._1 + 1, currentMove._2), agg)
        val leftMove = matrixMove(matrixWithMove, (currentMove._1, currentMove._2 + 1), agg)
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
