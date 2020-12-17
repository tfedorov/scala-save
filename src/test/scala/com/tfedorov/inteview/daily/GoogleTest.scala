package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
This problem was asked by Google.

Given the root to a binary tree, implement serialize(root), which serializes the tree into a string, and deserialize(s),
 which deserializes the string back into the tree.

For example, given the following Node class

class Node:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right
 */
class GoogleTest {

  sealed case class Node(value: String, left: Node = null, right: Node = null)

  @Test
  def Test(): Unit = {
    val input = Node("root", Node("left", Node("left.left")), Node("right"))
    val actualResult = input.left.left.value

    assertEquals("left.left", actualResult)
  }
}
