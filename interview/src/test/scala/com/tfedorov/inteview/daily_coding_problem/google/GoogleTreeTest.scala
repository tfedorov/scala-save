package com.tfedorov.inteview.daily_coding_problem.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
This problem was asked by Google.

A unival tree (which stands for "universal value") is a tree where all nodes under it have the same value.

Given the root to a binary tree, count the number of unival subtrees.

For example, the following tree has 5 unival subtrees:

   0
  / \
 1   0
    / \
   1   0
  / \
 1   1
 */
class GoogleTreeTest {

  case class ValueNode(value: Int, left: Option[ValueNode] = None, right: Option[ValueNode] = None) {

    def isUnival: Boolean = this match {
      case ValueNode(_, None, None) => true
      case ValueNode(root, None, Some(ValueNode(right, _, _))) => root == right
      case ValueNode(root, Some(ValueNode(left, _, _)), None) => root == left
      case ValueNode(root, Some(ValueNode(left, _, _)), Some(ValueNode(right, _, _))) => root == right && root == left
    }
  }

  def calcUnival(node: ValueNode): Int = {
    var res = 0

    if (node.isUnival)
      res += 1

    if (node.left.isDefined)
      res += calcUnival(node.left.get)
    if (node.right.isDefined)
      res += calcUnival(node.right.get)
    res
  }

  @Test
  def treeTaksTest(): Unit = {
    val right = ValueNode(0, Some(ValueNode(1, Some(ValueNode(1)), Some(ValueNode(1)))), Some(ValueNode(0)))
    val input = ValueNode(0, Some(ValueNode(1)), Some(right))

    val actualResult = calcUnival(input)

    val expectedResult = 5
    assertEquals(expectedResult, actualResult)
  }

    //https://crazycoderzz.wordpress.com/count-the-number-of-unival-subtrees-in-a-binary-tree/
  @Test
  def tree5Test(): Unit = {
    val left = ValueNode(1, left = Some(ValueNode(6)), right = Some(ValueNode(6)))
    val right = ValueNode(5, left = None, right = Some(ValueNode(5)))
    val input = ValueNode(5, Some(left), Some(right))

    val actualResult = calcUnival(input)

    val expectedResult = 4
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def tree5AllTest(): Unit = {
    val left = ValueNode(5, left = Some(ValueNode(5)), right = Some(ValueNode(5)))
    val right = ValueNode(5, left = None, right = Some(ValueNode(5)))
    val input = ValueNode(5, Some(left), Some(right))

    val actualResult = calcUnival(input)

    val expectedResult = 6
    assertEquals(expectedResult, actualResult)
  }
}
