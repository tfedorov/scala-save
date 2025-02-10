package com.tfedorov.inteview.daily_coding_problem.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Disabled

/*
A knight's tour is a sequence of moves by a knight on a chessboard such that all squares are visited once.

Given N, write a function to return the number of knight's tours on an N by N chessboard.


 */
class GoogleChecsTurn {
  case class Board(field: Seq[Seq[Int]]) {

    def printBoard(): Unit = {
      val max = field.flatten.max
      println("\n[")
      field.foreach(row => println(row.map {

        case 0 => "__"
        case el if el > 9 && el == max => "*" + el
        case el if el > 9 => el
        case el => "0" + el
      }.mkString(" ")))
      println("]\n")
    }

    def isCompleted(): Boolean = field.forall(_.forall(_ > 0))

    def newBoardWithStep(stepCandidate: Step): Option[Board] = {
      if (stepCandidate.x < 0 || stepCandidate.x >= this.field.head.size || stepCandidate.y < 0 || stepCandidate.y >= this.field.size)
        return None
      val oldRow: Seq[Int] = this.field(stepCandidate.y)
      if (oldRow(stepCandidate.x) > 0)
        return None
      val newRow = oldRow.updated(stepCandidate.x, stepCandidate.stepNumber)
      val newField: Seq[Seq[Int]] = this.field.updated(stepCandidate.y, newRow)
      Some(Board(newField))
    }
  }

  case class Step(x: Int, y: Int, stepNumber: Int) {

  }


  @Test
  @Disabled("Skipping because infinite time")
  def board8on8Test(): Unit = {
    val board = buildBoard(6)
    board.printBoard

    val actualResult = applyGame(board)

    val expectedResult = 2
    assertEquals(expectedResult, actualResult)
  }

  private def applyGame(board: Board): Int = {
    val returnAll = applyStep(Seq(board), Seq.empty, Step(x = 0, y = 0, stepNumber = 1))
    returnAll.size
  }

  private def applyStep(currentBoards: Seq[Board], completedBoards: Seq[Board], stepCandidate: Step): Seq[Board] = {
    val currentBoard = currentBoards.head

    val maybeNewBoard = currentBoard.newBoardWithStep(stepCandidate)
    if (maybeNewBoard.isEmpty) {
      return completedBoards
    }

    val newBoard = maybeNewBoard.get
    //    if (stepCandidate.stepNumber > currentBoards.headOption.map(b => b.field.size * b.field.head.size - 2).getOrElse(Int.MaxValue))
    //      newBoard.printBoard()
    if (newBoard.isCompleted()) {
      println("Completed!!!")
      newBoard.printBoard()
      println("Completed!!!")
      return completedBoards :+ currentBoard
    }

    val upLet: Seq[Board] = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x - 1, stepCandidate.y - 2, stepCandidate.stepNumber + 1))
    val upRight = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x + 1, stepCandidate.y - 2, stepCandidate.stepNumber + 1))
    val rightUp = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x + 2, stepCandidate.y - 1, stepCandidate.stepNumber + 1))
    val rightDown = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x + 2, stepCandidate.y + 1, stepCandidate.stepNumber + 1))
    val downRight = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x + 1, stepCandidate.y + 2, stepCandidate.stepNumber + 1))
    val downLeft = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x - 1, stepCandidate.y + 2, stepCandidate.stepNumber + 1))
    val leftDown = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x - 2, stepCandidate.y + 1, stepCandidate.stepNumber + 1))
    val leftUp = applyStep(newBoard +: currentBoards.tail, completedBoards, Step(stepCandidate.x - 2, stepCandidate.y - 1, stepCandidate.stepNumber + 1))
    upLet ++ upRight ++ rightUp ++ rightDown ++ downRight ++ downLeft ++ leftDown ++ leftUp
  }

  private def buildBoard(size: Int): Board = {
    val field = (1 to size).map(_ => (1 to size).map(_ => 0))
    Board(field)
  }

}
