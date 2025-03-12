package com.tfedorov.inteview.daily_coding_problem.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.Seq

/**
 * In a directed graph, each node is assigned an uppercase letter. We define a path's value as the number of most frequently-occurring letter along that path.
 * For example, if a path in the graph goes through "ABACA", the value of the path is 3, since there are 3 occurrences of 'A' on the path.
 *
 * Given a graph with n nodes and m directed edges, return the largest value path of the graph. If the largest value is infinite, then return null.
 *
 * The graph is represented with a string and an edge list. The i-th character represents the uppercase letter of the i-th node. Each tuple in the edge list (i, j) means there is a directed edge from the i-th node to the j-th node. Self-edges are possible, as well as multi-edges.
 *
 * For example, the following input graph:
 *
 * ABACA
 * [(0, 1),
 * (0, 2),
 * (2, 3),
 * (3, 4)]
 * Would have maximum value 3 using the path of vertices [0, 2, 3, 4], (A, A, C, A).
 *
 * The following input graph:
 *
 * A
 * [(0, 0)]
 * Should return null, since we have an infinite loop.
 */
class GraphPathTest {

  @Test
  def graphPathTest(): Unit = {
    val input = "ABACA"
    val nodes = Seq((0, 1),
      (0, 2),
      (2, 3),
      (3, 4))

    val actualResult = graphPath(input, nodes)

    val expectedResult = "AACA"
    assertEquals(expectedResult, actualResult)
  }

  case class Direction(from: Int, to: Int, fromLetter: Char, toLetter: Char)

  case class PossibleWay(available: Seq[Direction], from: Int, fromLetter: Char, to: Int, toLetter: Char)

  private def graphPath(input: String, nodes: Seq[(Int, Int)]) = {
    val (mostFreqLetter, number) = input.groupBy(identity).mapValues(_.length).toSeq.sortWith(_._2 > _._2).head
    (mostFreqLetter, number)
    val allNodes: Seq[Direction] = nodes.map { case (from, to) => Direction(from, to, input(from), input(to)) }

    def doWay(possible: Seq[Seq[Direction]], founded: Seq[Seq[Direction]]): Seq[Seq[Direction]] = {
      if (possible.headOption.isEmpty)
        return founded
      val currentDirection: Seq[Direction] = possible.head
      if (isInfinitLoop(currentDirection))
        return Seq.empty

      val chIncurrent = if (currentDirection.last.fromLetter == mostFreqLetter)
        1 + currentDirection.count(_.toLetter == mostFreqLetter)
      else
        currentDirection.count(_.toLetter == mostFreqLetter)
      val foundedWithNew = if (chIncurrent == number) {
        println("Founded:" + (currentDirection +: founded))
        currentDirection +: founded
      } else
        founded

      val currentStep: Direction = currentDirection.head
      val possibleNextSteps: Seq[Direction] = allNodes.filter(_.from == currentStep.to)
      if (possibleNextSteps.isEmpty)
        return doWay(possible.tail, foundedWithNew)

      val newGenerated: Seq[Seq[Direction]] = possibleNextSteps.map(_ +: currentDirection)
      newGenerated.foreach(println)

      doWay(newGenerated ++ possible.tail, foundedWithNew)
    }

    val firstNodes = allNodes.filter(_.from == 0).map(Seq(_))
    val foundedWays = doWay(firstNodes, Seq.empty)
    val biggest = foundedWays.sortWith(_.size > _.size).head.reverse
    biggest.head.fromLetter +: biggest.map(_.toLetter).mkString("")
  }


  private def isInfinitLoop(current: Seq[Direction]): Boolean = false
}
