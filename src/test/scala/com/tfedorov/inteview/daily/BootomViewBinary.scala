package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
//https://github.com/subsr97/daily-coding-problem/blob/master/challenges/bottom-view-of-binary-tree.py
/*

Yelp
The horizontal distance of a binary tree node describes how far left or right the node will be when the tree is printed out.
More rigorously, we can define it as follows:
The horizontal distance of the root is 0.
The horizontal distance of a left child is hd(parent) - 1.
The horizontal distance of a right child is hd(parent) + 1.
For example, for the following tree, hd(1) = -2, and hd(6) = 0.
             5
          /     \
        3         7
      /  \      /   \
    1     4    6     9
   /                /
  0                8
The bottom view of a tree, then, consists of the lowest node at each horizontal distance. If there are two nodes at the same depth and horizontal distance, either is acceptable.
For this tree, for example, the bottom view could be [0, 1, 3, 6, 8, 9].
Given the root to a binary tree, return its bottom view.
 */
class BootomViewBinary {

  case class Graph(rootNode: Node) {

    def horizontalDistance(value: Int): Option[Int] = {
      current(0, rootNode, value)
    }

    private def current(currentDistnce: Int, currentNode: Node, seekValue: Int): Option[Int] = {
      if (seekValue equals currentNode.value)
        return Some(currentDistnce)

      val leftDistance = currentNode.left.flatMap(current(currentDistnce - 1, _, seekValue))
      if (leftDistance.isDefined)
        return leftDistance

      val rightDistance = currentNode.right.flatMap(current(currentDistnce + 1, _, seekValue))
      if (rightDistance.isDefined)
        return rightDistance

      None
    }
  }

  case class Node(value: Int, left: Option[Node], right: Option[Node])

  /*
             5
          /     \
        3         7
      /  \      /   \
    1     4    6     9
   /                /
  0                8
   */
  @Test
  def defaultTest(): Unit = {
    val graph = Graph(
      Node(5,
        Some(Node(3, Some(Node(1, Some(Node(0, None, None)), Some(Node(4, None, None)))), None))
        , Some(Node(7, Some(Node(6, None, None)), Some(Node(9, Some(Node(8, None, None)), None))))
      )
    )

    val actual0 = graph.horizontalDistance(5)
    val actual1 = graph.horizontalDistance(1)
    val actual6 = graph.horizontalDistance(6)
    val actualResultUnique = graph.horizontalDistance(0)

    Seq(0, 1, 3, 6, 8, 9).foreach(el => println(graph.horizontalDistance(el)))

    assertEquals(Some(0), actual0)
    assertEquals(Some(-2), actual1)
    assertEquals(Some(0), actual6)
    assertEquals(Some(-3), actualResultUnique)

  }
}
