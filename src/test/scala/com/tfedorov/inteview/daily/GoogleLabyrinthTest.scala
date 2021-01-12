package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
Google.

You are given an M by N matrix consisting of booleans that represents a board. Each True boolean represents a wall.
Each False boolean represents a tile you can walk on.

Given this matrix, a start coordinate, and an end coordinate, return the minimum number of steps required to reach the
end coordinate from the start. If there is no possible path, then return null. You can move up, left, down, and right.
You cannot move through walls. You cannot wrap around the edges of the board.

For example, given the following board:

[[f, f, f, f],
[t, t, f, t],
[f, f, f, f],
[f, f, f, f]]
and start = (3, 0) (bottom left) and end = (0, 0) (top left), the minimum number of steps required to reach the end is 7,
since we would need to go through (1, 2) because there is a wall everywhere else on the second row.
 */
class GoogleLabyrinthTest {

  private case class StepPosition(activeFields: Seq[Seq[Option[Boolean]]], step: (Int, Int), doneSteps: Int) {
    lazy val maxX = activeFields.head.length
    lazy val maxY = activeFields.length

    def newPosition(step: (Int, Int)): StepPosition = {
      val row = activeFields(step._1)
      val newRow = row.updated(step._2, None)
      val newFields = activeFields.updated(step._1, newRow)
      StepPosition(newFields, step, doneSteps + 1)
    }

    def isCompletedLabyrinth(implicit end: (Int, Int)): Boolean = step.equals(end)

    def isNormalStep(step: (Int, Int)): Boolean = step match {
      case (-1, _) => false
      case (yLarge, _) if yLarge == maxY => false
      case (_, -1) => false
      case (_, xLarge) if xLarge == maxX => false
      case _ => true
    }

    def possibleSteps: Seq[(Int, Int)] = {
      val (y, x) = step

      val theoreticalSteps = Seq((y + 1, x), (y - 1, x), (y, x + 1), (y, x - 1))
      val normalSteps = theoreticalSteps.filter(isNormalStep)
      val possibleOnFieldSteps = normalSteps.filter { case (y, x) =>
        activeFields(y)(x).exists(!_)
      }
      possibleOnFieldSteps
    }
  }

  def recSearch(stepPosition: StepPosition)(implicit endPoint: (Int, Int)): Option[Int] = {
    if (stepPosition.isCompletedLabyrinth)
      return Some(stepPosition.doneSteps)

    val possibleSteps = stepPosition.possibleSteps
    if (possibleSteps.isEmpty)
      return None

    var bestResult: Option[Int] = None
    possibleSteps.foreach { step =>
      val newPosition = stepPosition.newPosition(step)
      val stepRes: Option[Int] = recSearch(newPosition)
      (stepRes, bestResult) match {
        case (Some(_), None) => bestResult = stepRes
        case (Some(step), Some(best)) => bestResult = Some(Math.min(step, best))
        case _ =>
      }

    }
    bestResult
  }

  def findPath(startPoint: (Int, Int), endPoint: (Int, Int), labirynth: Seq[Seq[Boolean]]): Option[Int] = {
    val stepField: Seq[Seq[Option[Boolean]]] = labirynth.map(_.map(Some(_)))
    val postion0 = StepPosition(stepField, startPoint, -1).newPosition(startPoint)
    recSearch(postion0)(endPoint)
  }

  @Test
  def defaultTest(): Unit = {
    implicit val labyrinth: Seq[Seq[Boolean]] = Seq(
      Seq(false, false, false, false),
      Seq(true, true, false, true),
      Seq(false, false, false, false),
      Seq(false, false, false, false)
    )
    val startPoint = (3, 0)
    val endPoint = (0, 0)

    val actualResult = findPath(startPoint, endPoint, labyrinth)

    val expectedResult = Some(7)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def wrongTest(): Unit = {
    implicit val labyrinth: Seq[Seq[Boolean]] = Seq(
      Seq(false, true, false, false),
      Seq(true, true, false, true),
      Seq(false, false, false, false),
      Seq(false, false, false, false)
    )
    val startPoint = (3, 0)
    val endPoint = (0, 0)

    val actualResult = findPath(startPoint, endPoint, labyrinth)

    val expectedResult = None
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def customTest(): Unit = {
    implicit val labyrinth: Seq[Seq[Boolean]] = Seq(
      Seq(false, false, false, false),
      Seq(true, true, true, false),
      Seq(false, false, false, false),
      Seq(false, false, false, false)
    )
    val startPoint = (3, 0)
    val endPoint = (0, 0)

    val actualResult = findPath(startPoint, endPoint, labyrinth)

    val expectedResult = Some(9)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def customLargerTest(): Unit = {
    implicit val labyrinth: Seq[Seq[Boolean]] = Seq(
      Seq(false, false, false, false, false),
      Seq(true, true, true, false, false),
      Seq(false, false, false, false, false),
      Seq(false, false, false, false, false)
    )
    val startPoint = (3, 0)
    val endPoint = (0, 4)

    val actualResult = findPath(startPoint, endPoint, labyrinth)

    val expectedResult = Some(7)
    assertEquals(expectedResult, actualResult)
  }
}
