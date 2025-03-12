package com.tfedorov.inteview.daily_coding_problem.facebook

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//# 182
//Facebook
//A graph is minimally-connected if it is connected and there is no edge that can be removed while still leaving the graph connected.
//For example, any binary tree is minimally-connected.
//Given an undirected graph, check if the graph is minimally-connected.
//You can choose to represent the graph as either an adjacency matrix or adjacency list.

class FacebookMinimal {
  //https://github.com/subsr97/daily-coding-problem/blob/master/index.md

  case class Graph(rootNode: Node) {
    def treeDrop(removeElement: (String, String)): Graph = {
      Graph(nodeWithoutElement(rootNode, removeElement))
    }

    private def nodeWithoutElement(currentNode: Node, removeElement: (String, String)): Node = {

      val newConnections = if (removeElement._1 != currentNode.value) {
        currentNode.connections.map(nodeWithoutElement(_, removeElement))
      } else {
        currentNode.connections.filter(_.value != removeElement._2)
      }
      currentNode.copy(connections = newConnections)
    }

    def valuesNumber(): Int = {
      values4Node(Set.empty, rootNode).size
    }

    private def values4Node(agg: Set[String], currentNode: Node): Set[String] = {
      val newAgg: Set[String] = agg + currentNode.value
      if (currentNode.connections.isEmpty)
        return newAgg

      val aggConneciton = currentNode.connections.flatMap(values4Node(newAgg, _))
      aggConneciton.toSet
    }
  }

  case class Node(value: String, connections: Seq[Node] = Seq.empty) {

    def allConnectedNodes(agg: Seq[(String, String)] = Seq.empty): Seq[(String, String)] = {
      if (this.connections.isEmpty)
        return agg.distinct

      val s = this.connections.flatMap { innerConnection =>
        val newAgg = agg :+ (this.value -> innerConnection.value)
        innerConnection.allConnectedNodes(newAgg)
      }
      s.distinct
    }

  }

  def checkMinimal(root: Graph): Boolean = {
    val originalNodes = root.rootNode.allConnectedNodes()
    val originalNodeCount = root.valuesNumber()
    originalNodes.filter { element2Remove =>
      val treeWithRemoved = root.treeDrop(element2Remove)
      val res = originalNodeCount == treeWithRemoved.valuesNumber()
      if (res)
        println(element2Remove)
      res
    }.isEmpty
  }

  @Test
  def defaultTest(): Unit = {
    val nodeL3 = Node("LeftRightL3", Nil)
    val originalTree = Graph(Node("top",
      Node("LeftL1", Node("LeftLeftL2", Nil) :: Node("LeftRightL2", nodeL3 :: Nil) :: Nil) :: Node("RightL1", nodeL3 :: Nil) :: Nil))
    println(originalTree)

    val actualResult = checkMinimal(originalTree)

    assertEquals(false, actualResult)
  }

  @Test
  def minimalTest(): Unit = {
    val originalTree = Graph(Node("top",
      Node("LeftL1", Node("LeftLeftL2", Nil) :: Node("LeftRightL2", Nil) :: Nil) :: Node("RightL1", Nil) :: Nil)
    )

    val actualResult = checkMinimal(originalTree)

    assertEquals(true, actualResult)
  }

  @Test
  def smallNotMinimalTest(): Unit = {
    val originalTree = Graph(Node("top",
      Node("LeftL1", Node("LeftLeftL2", Nil) :: Node("LeftRightL2", Node("LeftRightL3", Nil) :: Nil) :: Nil) :: Node("RightL1", Node("LeftRightL3", Nil) :: Nil) :: Nil))

    println("\nsmallNotMinimalTree")
    val smallNotMinimalTree = originalTree.treeDrop("LeftL1" -> "LeftLeftL2")

    println(smallNotMinimalTree)
    val actualResult = checkMinimal(smallNotMinimalTree)

    assertEquals(false, actualResult)
  }

  @Test
  def smallMinimalTreeTest(): Unit = {
    val originalTree = Graph(Node("top",
      Node("LeftL1", Node("LeftLeftL2", Nil) :: Node("LeftRightL2", Node("LeftRightL3", Nil) :: Nil) :: Nil) :: Node("RightL1", Node("LeftRightL3", Nil) :: Nil) :: Nil))

    println("\nsmallMinimalTree")
    val smallMinimalTree = originalTree.treeDrop("RightL1" -> "LeftRightL3")
    println(smallMinimalTree)
    val actualResult = checkMinimal(smallMinimalTree)

    assertEquals(true, actualResult)
  }

  @Test
  def cycledTreeTest(): Unit = {
    val originalTree = Graph(Node("top",
      Node("LeftL1", Node("LeftLeftL2", Nil) :: Node("LeftRightL2", Node("LeftRightL3", Nil) :: Nil) :: Nil) :: Node("RightL1", Node("LeftRightL3", Nil) :: Nil) :: Nil))

    println("\ncycledTree")
    val cycledTree = originalTree.treeDrop("RightL1" -> "LeftRightL3")
    println(cycledTree)
    val actualResult = checkMinimal(cycledTree)

    assertEquals(true, actualResult)
  }

}