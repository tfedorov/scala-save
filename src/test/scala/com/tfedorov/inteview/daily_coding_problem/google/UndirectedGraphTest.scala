package com.tfedorov.inteview.daily_coding_problem.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Given an undirected graph represented as an adjacency matrix and an integer k, write a function to determine whether
 * each vertex in the graph can be colored such that no two adjacent vertices share the same color using at most k colors.
 */
class UndirectedGraphTest {

  case class Edge(first: String, second: String) {
    var colour: Int = -1
  }

  case class Graph(edges: Seq[Edge]) {
    def edges4Node(nodeName: String): Seq[Edge] = edges.filter(n => n.first.equalsIgnoreCase(nodeName) || n.second.equalsIgnoreCase((nodeName)))

    def adjacentEdges(edge: Edge): Seq[Edge] =
      edges4Node(edge.first).filter(!_.equals(edge)) ++ edges4Node(edge.second).filter(!_.equals(edge))
  }

  def checkFunctionQuick(source: Graph, numberOfColour: Int): Boolean = {

    val numOf: Map[String, Int] = source.edges.flatten(vertex => Seq(vertex.second, vertex.first)).groupBy(el => el.toLowerCase).mapValues(_.length)
    println(numOf)

    numOf.forall(_._2 <= numberOfColour)
  }

  @Test
  def taskTest(): Unit = {

    val input = Graph(Edge("a", "b")
      :: Edge("b", "c")
      :: Edge("c", "d")
      :: Edge("d", "e")
      :: Edge("a", "d")
      :: Edge("b", "d")
      :: Nil)


    assertEquals(false, checkFunctionQuick(input, 2))
    assertEquals(true, checkFunctionQuick(input, 4))
  }


  def checkFunctionEmul(source: Graph, numberOfColour: Int): Boolean = {
    val possibleResolvedGraph = buildPossible(source: Graph, numberOfColour)
    possibleResolvedGraph.isDefined
  }

  private def buildPossible(originGraph: Graph, numberOfColour: Int): Option[Graph] = {
    val allPossibleColours: Seq[Int] = (1 to numberOfColour)
    val resolvedCandidate: Graph = originGraph.copy()
    resolvedCandidate.edges.foreach { proccessEdge =>
      if (proccessEdge.colour < 0) {
        val adjacentColors: Set[Int] = originGraph.adjacentEdges(proccessEdge).filter(_.colour > 0).map(_.colour).toSet
        if (adjacentColors.size == numberOfColour)
          return None
        val colourCandidates = allPossibleColours.diff(adjacentColors.toSeq)
        val colourCandidate = colourCandidates.head
        proccessEdge.colour = colourCandidate
      }
    }

    Some(resolvedCandidate)
  }

  @Test
  def taskTestProcesse1(): Unit = {

    val input = Graph(Edge("a", "b")
      :: Edge("b", "c")
      :: Edge("c", "d")
      :: Edge("d", "e")
      :: Edge("a", "d")
      :: Edge("b", "d")
      :: Nil)


    assertEquals(false, checkFunctionEmul(input, 2))
    assertEquals(true, checkFunctionEmul(input, 4))
  }


  @Test
  def taskTestProcesse2(): Unit = {

    val input = Graph(
      Edge("a", "b")
        :: Edge("a", "c")
        :: Edge("a", "d")
        :: Edge("a", "e")
        :: Edge("e", "f")
        :: Edge("e", "g")
        :: Edge("e", "h")
        :: Edge("e", "j")
        :: Nil)


    assertEquals(false, checkFunctionEmul(input, 2))
    assertEquals(true, checkFunctionEmul(input, 4))
  }

}
