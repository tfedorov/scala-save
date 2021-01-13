package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
Google.

Implement locking in a binary tree. A binary tree node can be locked or unlocked only if all of its descendants or ancestors are not locked.

Design a binary tree node class with the following methods:

is_locked, which returns whether the node is locked
lock, which attempts to lock the node. If it cannot be locked, then it should return false. Otherwise, it should lock it and return true.
unlock, which unlocks the node. If it cannot be unlocked, then it should return false. Otherwise, it should unlock it and return true.
You may augment the node to add parent pointers or any other property you would like.
You may assume the class is used in a single-threaded program, so there is no need for actual locks or mutexes.
 Each method should run in O(h), where h is the height of the tree.
 */
class GoogleLockTreeTest {

  case class Node(value: String, parent: Option[Node], var isLocked: Boolean = false) {

    var left: Option[Node] = None
    var right: Option[Node] = None

    def addLeft(value: String): Node = {
      val newNode = Node(value, Some(this))
      left = Some(newNode)
      newNode
    }

    def addRight(value: String): Node = {
      val newNode = Node(value, Some(this))
      right = Some(newNode)
      newNode
    }

    def lock: Boolean = {
      val couldBeLock = descLocked || ancessLocked
      if (couldBeLock)
        isLocked = true
      couldBeLock
    }

    def unlock: Boolean = {
      val couldBeUnLock = descLocked || ancessLocked
      if (couldBeUnLock)
        isLocked = false
      couldBeUnLock
    }

    private def ancessLocked: Boolean = parent.forall(_.isLocked) && parent.forall(_.ancessLocked)

    private def descLocked: Boolean = {
      val leftDirect = left.forall(_.isLocked)
      val leftDesc = left.forall(_.descLocked)
      leftDirect && leftDesc && right.forall(_.isLocked)
    }


  }

  @Test
  def isLockedTest(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")

    val actualResult = l1r2.isLocked

    assertEquals(false, actualResult)
  }

  @Test
  def lockTest(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")

    l1r2.lock
    val actualResult = l1r2.isLocked

    assertEquals(true, actualResult)
  }

  @Test
  def couldNotLockTest(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")

    l1r2.lock
    val actualResult = l1.lock

    assertEquals(false, actualResult)
  }

  @Test
  def lock2Test(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")

    l1l2.lock
    l1r2.lock
    val actualResult = l1.lock

    assertEquals(true, actualResult)
  }

  @Test
  def lock3Test(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")
    val l1l2l1 = l1l2.addLeft("l1l2l1")

    l1l2.lock
    l1r2.lock
    val actualResult = l1.lock

    assertEquals(false, actualResult)
  }

  @Test
  def lock3lTest(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")
    val l1l2l1 = l1l2.addLeft("l1l2l1")

    l1l2l1.lock
    l1l2.lock
    l1r2.lock

    val actualResult = l1.lock

    assertEquals(true, actualResult)
  }

  @Test
  def lockAncTest(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")
    val l1l2l1 = l1l2.addLeft("l1l2l1")

    root.lock
    val actualResult = r1.lock

    assertEquals(true, actualResult)
  }

  @Test
  def lockAnc2Test(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")
    val l1l2l1 = l1l2.addLeft("l1l2l1")

    val actualResult = l1.lock

    assertEquals(false, actualResult)
  }

  @Test
  def unlockAnc2Test(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")
    val l1l2l1 = l1l2.addLeft("l1l2l1")

    val actualResult = l1.unlock

    assertEquals(false, actualResult)
  }

  @Test
  def unlock3lTest(): Unit = {
    val root = Node("root", None)
    val l1 = root.addLeft("l1")
    val r1 = root.addRight("r1")
    val l1l2 = l1.addLeft("l1l2")
    val l1r2 = l1.addRight("l1r2")
    val l1l2l1 = l1l2.addLeft("l1l2l1")

    l1l2l1.lock
    l1l2.lock
    l1r2.lock
    l1.lock

    val actualResult = l1.unlock

    assertEquals(true, actualResult)
  }

}
