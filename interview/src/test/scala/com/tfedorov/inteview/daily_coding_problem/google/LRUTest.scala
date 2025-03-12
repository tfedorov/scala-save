package com.tfedorov.inteview.daily_coding_problem.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.mutable

/*
Implement an LFU (Least Frequently Used) cache. It should be able to be initialized with a cache size n, and contain the following methods:

- set(key, value): sets key to value. If there are already n items in the cache and we are adding a new item, then it should also remove the least frequently used item.
If there is a tie, then the least recently used key should be removed.
- get(key): gets the value at key. If no such key exists, return null.
Each operation should run in O(1) time.
 */
class LRUTest {

  private val LIMIT = 5

  object LinkedElement {
    def unapply(u: LinkedElement): Option[(String, Long, Option[LinkedElement], Option[LinkedElement])] = Some(u.key, u.freq, u.prevElement, u.nextElement)
  }

  class LinkedElement(val key: String, var freq: Long, var prevElement: Option[LinkedElement], var nextElement: Option[LinkedElement]) {
    override def toString: String = "key=" + key + ", " + freq + ", " + prevElement.map(_.key).getOrElse("no prev") + ", " + nextElement.map(_.key).getOrElse("No next")
  }

  private val cache: mutable.Map[String, (LinkedElement, String)] = mutable.Map.empty

  private var lastElement: LinkedElement = _

  def set(key: String, value: String): Unit = {
    val maybePresentElement: Option[(LinkedElement, String)] = cache.get(key)
    if (maybePresentElement.isEmpty)
      newElementProcess(key, value)
    else
      existedElementProcess(key, maybePresentElement.get, value)
  }

  def get(key: String): Option[String] = cache.get(key).map(_._2)

  private def existedElementProcess(key: String, valueEl: (LinkedElement, String), newValue: String): Unit = {
    val (currentElement, value) = valueEl
    currentElement.freq += 1
    (currentElement, currentElement.nextElement) match {
      case (_, None) => // Do nothing
      case (LinkedElement(_, currentElFreq, _, _), Some(LinkedElement(_, nextFreq, _, _))) if currentElFreq <= nextFreq => // Do nothing

      case _ =>
        val element2Replace = foundElementToSetup(currentElement)

        currentElement.prevElement.foreach(_.nextElement = currentElement.nextElement)
        currentElement.nextElement.foreach(_.prevElement = currentElement.prevElement)
        currentElement.nextElement = element2Replace.nextElement
        currentElement.prevElement = Some(element2Replace)
        element2Replace.nextElement.foreach(_.prevElement = Some(currentElement))
        element2Replace.nextElement = Some(currentElement)
    }
  }

  private def foundElementToSetup(currentElement: LinkedElement): LinkedElement = {
    var maybeNextElement = currentElement.nextElement
    var element2Replace = maybeNextElement.get
    while (maybeNextElement.isDefined && currentElement.freq > maybeNextElement.get.freq) {
      element2Replace = maybeNextElement.get
      maybeNextElement = maybeNextElement.get.nextElement
    }
    element2Replace
  }


  private def newElementProcess(key: String, value: String): Unit = {
    cache.size match {
      case 0 =>
        val newLinkedElement = new LinkedElement(key = key, freq = 1L, prevElement = None, nextElement = None)
        cache.put(key, (newLinkedElement, value))
        lastElement = newLinkedElement

      case notFull if notFull < LIMIT =>

        val newLinkedElement = new LinkedElement(key = key, freq = 1L, prevElement = None, nextElement = Some(lastElement))
        lastElement.prevElement = Some(newLinkedElement)
        cache.put(key, (newLinkedElement, value))
        lastElement = newLinkedElement

      case _ =>
        val beforeLastElement = lastElement.nextElement.get
        val newLinkedElement = new LinkedElement(key = key, freq = 1L, prevElement = None, nextElement = Some(beforeLastElement))
        beforeLastElement.prevElement = Some(newLinkedElement)
        cache.remove(lastElement.key)
        cache.put(key, (newLinkedElement, value))
        lastElement = newLinkedElement
    }
  }

  @Test
  def limitTest(): Unit = {
    set("one", "oneVal")
    set("two", "twoVal")
    set("three", "threeVal")
    set("four", "fourVal")
    set("five", "fiveVal")
    set("six", "sixVal")

    val actualResult = get("five")

    cache.foreach(println)
    //    val expectedResult = None
    assertEquals(LIMIT, cache.size)
    assertEquals(None, actualResult)

    println("-------")
    set("four", "fourValChanged")
    cache.foreach(println)
    assertEquals(LIMIT, cache.size)
    assertEquals(None, actualResult)

    println("-------")
    set("six", "sixChanged")
    cache.foreach(println)
  }
}
