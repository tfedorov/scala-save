package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import java.util
import scala.collection.mutable

/*
his problem was asked by Twitter.

You run an e-commerce website and want to record the last N order ids in a log. Implement a data structure to accomplish this, with the following API:

record(order_id): adds the order_id to the log
get_last(i): gets the ith last element from the log. i is guaranteed to be smaller than or equal to N.
You should be as efficient with time and space as possible.
 */
class TwitterTest {

  class LatestLog(sizeOfLog: Int) {
    var inside = Seq.empty[Int]

    def add(orderId: Int): Unit = {
      if (inside.length >= sizeOfLog)
        inside = orderId +: inside.init
      else
        inside = orderId +: inside
    }

    def get_last(num: Int): Option[Int] = {
      if (num >= inside.size)
        return None
      Some(inside(num))
    }
  }

  @Test
  def lastLogTest(): Unit = {
    val log = new LatestLog(3)
    log.add(10)
    log.add(11)
    log.add(12)
    log.add(13)


    assertEquals(Some(13), log.get_last(0))
    assertEquals(Some(12), log.get_last(1))
    assertEquals(Some(11), log.get_last(2))
    assertEquals(None, log.get_last(3))
  }

  class LatestLogMutable(sizeOfLog: Int) {
    val inside = new util.LinkedList[Int]()

    def add(orderId: Int): Unit = {
      if (inside.size() >= sizeOfLog) {
        inside.removeLast()
      }
      inside.addFirst(orderId)
    }

    def get_last(num: Int): Option[Int] = {
      if (num >= inside.size)
        return None
      Some(inside.get(num))
    }
  }

  @Test
  def lastLogMutTest(): Unit = {
    val log = new LatestLogMutable(3)
    log.add(10)
    log.add(11)
    log.add(12)
    log.add(13)


    assertEquals(Some(13), log.get_last(0))
    assertEquals(Some(12), log.get_last(1))
    assertEquals(Some(11), log.get_last(2))
    assertEquals(None, log.get_last(3))
  }

}
