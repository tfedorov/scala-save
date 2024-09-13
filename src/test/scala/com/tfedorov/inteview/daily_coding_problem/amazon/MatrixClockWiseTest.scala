package com.tfedorov.inteview.daily_coding_problem.amazon

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Given a N by M matrix of numbers, print out the matrix in a clockwise spiral.
 *
 * For example, given the following matrix:
 *
 * [[1,  2,  3,  4,  5],
 * [6,  7,  8,  9,  10],
 * [11, 12, 13, 14, 15],
 * [16, 17, 18, 19, 20]]
 * You should print out the following:
 *
 * 1
 * 2
 * 3
 * 4
 * 5
 * 10
 * 15
 * 20
 * 19
 * 18
 * 17
 * 16
 * 11
 * 6
 * 7
 * 8
 * 9
 * 14
 * 13
 * 12
 */


class MatrixClockWiseTest {
  type Matrix = Seq[Seq[Option[Int]]]

  @Test
  def clockWiseTest(): Unit = {
    val input =
      Seq(1, 2, 3, 4, 5) ::
        Seq(6, 7, 8, 9, 10) ::
        Seq(11, 12, 13, 14, 15) ::
        Seq(16, 17, 18, 19, 20) ::
        Nil

    val actualResult: Seq[Int] = clockWise(input)

    val expectedResult: Seq[Int] = List(1, 2, 3, 4, 5, 10, 15, 20, 19, 18, 17, 16, 11, 6, 7, 8, 9, 14, 13, 12)
    assertEquals(expectedResult, actualResult)
  }

  sealed trait Move

  object Left extends Move

  object Down extends Move

  object Right extends Move

  object Up extends Move

  case class ElementPointer(x: Int, y: Int, move: Move) {
    def changeDirection: ElementPointer = {
      move match {
        case Right => this.copy(y = y + 1, move = Down)
        case Down => this.copy(x = x - 1, move = Left)
        case Left => this.copy(y = y - 1, move = Up)
        case Up => this.copy(x = x + 1, move = Right)
      }
    }

    def sameDirection: ElementPointer = {
      move match {
        case Right => this.copy(x + 1)
        case Down => this.copy(y = y + 1)
        case Left => this.copy(x = x - 1)
        case Up => this.copy(y = y - 1)
      }
    }
  }

  private def clockWise(input: List[Seq[Int]]): Seq[Int] = {
    val firstElement = ElementPointer(0, 0, Right)
    val matrix = input.map(r => r.map(Some(_)))
    processElement(Seq.empty, firstElement, matrix)
  }

  def isAvailable(current: ElementPointer, matrix: Matrix): Boolean = {
    val matrixX = matrix.head.size
    val matrixY = matrix.size

    current match {
      case ElementPointer(x, _, _) if x < 0 => false
      case ElementPointer(_, y, _) if y < 0 => false
      case ElementPointer(x, _, _) if x > matrixX - 1 => false
      case ElementPointer(_, y, _) if y > matrixY - 1 => false
      case ElementPointer(x, y, _) if matrix(y)(x).isEmpty => false
      case _ => true
    }

  }

  final def processElement(aggResult: Seq[Int], current: ElementPointer, matrix: Matrix): Seq[Int] = {

    val result: Seq[Int] = aggResult :+ matrix(current.y)(current.x).get
    val matrixWithNoCurrent = matrix.updated(current.y, matrix(current.y).updated(current.x, None))

    val isAvailableF: ElementPointer => Boolean = isAvailable(_, matrixWithNoCurrent)
    val processElementF: ElementPointer => Seq[Int] = processElement(result, _, matrixWithNoCurrent)

    current match {
      case _ if isAvailableF(current.sameDirection) => processElementF(current.sameDirection)
      case _ if isAvailableF(current.changeDirection) => processElementF(current.changeDirection)
      case lastElement => result
    }
  }

}
