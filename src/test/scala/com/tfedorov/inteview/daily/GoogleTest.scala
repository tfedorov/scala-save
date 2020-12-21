package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
An XOR linked list is a more memory efficient doubly linked list.
Instead of each node holding next and prev fields, it holds a field named both, which is an XOR of the next node and the previous node.
 Implement an XOR linked list; it has an add(element) which adds the element to the end, and a get(index) which returns the node at index.

If using a language that has no pointers (such as Python), you can assume you have access to get_pointer
and dereference_pointer functions that converts between nodes and memory addresses.
 */
class GoogleTest {

  case class XorElement(value: Int, index: Int)(implicit list: XORList) {

    private def operation(valueBefore: Int, valueAfter: Int): Int = {

      val before: String = valueBefore.toBinaryString.reverse
      val after: String = valueAfter.toBinaryString.reverse

      val maxNumber = Math.max(before.length, after.length)

      val beforeNorm: Array[Char] = before.toCharArray.padTo(maxNumber, '0')
      val afterNorm: Array[Char] = after.toCharArray.padTo(maxNumber, '0')

      val zipped: Array[(Char, Char)] = beforeNorm.zip(afterNorm)
      val calc = zipped.map { case (l, r) =>
        (l, r) match {
          case ('0', '0') => '0'
          case ('0', '1') => '1'
          case ('1', '0') => '1'
          case ('1', '1') => '0'
        }
      }
      Integer.parseInt(calc.reverse.mkString, 2)
    }

    def both: Option[Int] = {
      for (l <- list(index - 1);
           r <- list(index + 1))
        yield operation(l.value, r.value)
    }
  }

  class XORList(var _insideList: Seq[XorElement]) {

    def apply(index: Int): Option[XorElement] = {
      if (!_insideList.indices.contains(index))
        return None
      Some(_insideList(index))
    }

    def add(el: Int)(implicit list: XORList): Unit = {
      _insideList ++= XorElement(el, _insideList.length) :: Nil
    }

    def get(el: Int): Option[XorElement] = {
      _insideList.find(_.value == el)
    }
  }

  @Test
  def getTest(): Unit = {
    implicit val list = new XORList(Nil)
    list.add(1)
    list.add(5)
    list.add(2)

    val actualResult = list.get(5).flatMap(_.both)

    val expectedResult = Some(3)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def getTest2(): Unit = {
    implicit val list = new XORList(Nil)
    list.add(3)
    list.add(15)
    list.add(5)

    val actualResult = list.get(15).flatMap(_.both)

    val expectedResult = Some(6)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def getTest3(): Unit = {
    implicit val list = new XORList(Nil)
    list.add(3)
    list.add(15)
    list.add(5)

    val actualResult = list.get(3).flatMap(_.both)

    val expectedResult = None
    assertEquals(expectedResult, actualResult)
  }
}


