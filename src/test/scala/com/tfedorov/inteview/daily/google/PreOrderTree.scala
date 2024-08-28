package com.tfedorov.inteview.daily.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

//Given pre-order and in-order traversals of a binary tree, write a function to reconstruct the tree.
//
//For example, given the following preorder traversal:
//
//[a, b, d, e, c, f, g]
//
//And the following inorder traversal:
//
//[d, b, e, a, f, c, g]
//
//You should return the following tree:

//    a
//   / \
//  b   c
// / \ / \
//d  e f  g

//Preorder Traversal (Practice):
//Algorithm Preorder(tree)
//Visit the root.
//Traverse the left subtree, i.e., call Preorder(left->subtree)
//Traverse the right subtree, i.e., call Preorder(right->subtree)

//Postorder Traversal (Practice):
//Algorithm Postorder(tree)
//Traverse the left subtree, i.e., call Postorder(left->subtree)
//Traverse the right subtree, i.e., call Postorder(right->subtree)
//Visit the root

class PreOrderTree {

  case class Node[L, R](value: Char, left: L, right: R)


  def buildTreeRec(preorderTraversal: Seq[Char], inputTraversal: Seq[Char]): Node[_, _] = {
    println("----")
    println("preorderTraversal = " + preorderTraversal)
    println("inputTraversal = " + inputTraversal)

    if (inputTraversal.size <= 1) {
      println(Node(inputTraversal.head, null, null))
      return Node(inputTraversal.head, null, null)
    }

    val rootChar = preorderTraversal.head
    println("rootChar = " + rootChar)

    val leftTraversalList = inputTraversal.takeWhile(_ != rootChar)
    println("leftTraversalList = " + leftTraversalList)

    val leftNode = buildTreeRec(preorderTraversal.tail, leftTraversalList)
    val rightTraversal = inputTraversal.drop(inputTraversal.indexOf(rootChar) + 1)
    println("rightTraversal = " + rightTraversal)
    println("preorderTraversal = " + preorderTraversal.drop(preorderTraversal.indexOf(rootChar) + 1))
    val rightNode = buildTreeRec(preorderTraversal.drop(preorderTraversal.indexOf(rootChar) + 1), rightTraversal)
    Node(preorderTraversal.head, leftNode, rightNode)
  }

  @Test
  def buildTreeTest(): Unit = {
    val preorderTraversal = Seq('a', 'b', 'd', 'e', 'c', 'f', 'g')
    val inputTraversal = Seq('d', 'b', 'e', 'a', 'f', 'c', 'g')

    val actualResult = buildTreeRec(preorderTraversal, inputTraversal)

    val expectedResult = "2"
    assertEquals(expectedResult, actualResult)
  }
}
