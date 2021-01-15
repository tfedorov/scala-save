package com.tfedorov.inteview.daily.google

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
An XOR linked list is a more memory efficient doubly linked list.
Instead of each node holding next and prev fields, it holds a field named both, which is an XOR of the next node and the previous node.
 Implement an XOR linked list; it has an add(element) which adds the element to the end, and a get(index) which returns the node at index.

If using a language that has no pointers (such as Python), you can assume you have access to get_pointer
and dereference_pointer functions that converts between nodes and memory addresses.
 */
class GoogleXORListTest {

  case class XorElement(value: Int, maybeLeft: Option[Int], maybeRight: Option[Int]) {

    private def xorBitOp(lr: (Char, Char)): Char = lr match {
      case ('0', '0') => '0'
      case ('0', '1') => '1'
      case ('1', '0') => '1'
      case ('1', '1') => '0'
    }

    private def xorIntOp(valueBefore: Int, valueAfter: Int): Int = {
      val before: String = valueBefore.toBinaryString.reverse
      val after: String = valueAfter.toBinaryString.reverse

      val maxNumber = Math.max(before.length, after.length)

      val beforeNorm: Array[Char] = before.toCharArray.padTo(maxNumber, '0')
      val afterNorm: Array[Char] = after.toCharArray.padTo(maxNumber, '0')

      val zipped: Array[(Char, Char)] = beforeNorm.zip(afterNorm)
      val calc = zipped.map(xorBitOp)
      Integer.parseInt(calc.reverse.mkString, 2)
    }

    def both: Option[Int] =
      for (l <- maybeLeft;
           r <- maybeRight)
        yield xorIntOp(l, r)
  }

  case class XorList(_insideSeq: Seq[Int]) {
    def add(value: Int): Seq[Int] = _insideSeq :+ value

    def get(value: Int): Option[XorElement] = {
      _insideSeq.indexOf(value) match {
        case -1 => None
        case _ if _insideSeq.length == 1 => Some(XorElement(value, None, None))
        case 0 => Some(XorElement(value, None, Some(_insideSeq(1))))
        case lastEl if lastEl == _insideSeq.length => Some(XorElement(value, Some(_insideSeq(lastEl - 1)), None))
        case middleEl => Some(XorElement(value, Some(_insideSeq(middleEl - 1)), Some(_insideSeq(middleEl + 1))))
      }
    }
  }

  implicit def seq2Xor(inside: Seq[Int]): XorList = XorList(inside)

  @Test
  def bothTest12(): Unit = {
    var list: Seq[Int] = Nil
    list = list.add(1)
    list = list.add(5)
    list = list.add(2)

    val actualResult = list.get(5).flatMap(_.both)

    val expectedResult = Some(3)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def bothTest35(): Unit = {
    var list: Seq[Int] = Nil
    list = list.add(3)
    list = list.add(15)
    list = list.add(5)

    val actualResult = list.get(15).flatMap(_.both)

    val expectedResult = Some(6)
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def bothTestNone(): Unit = {
    var list: Seq[Int] = Nil
    list = list.add(3)
    list = list.add(15)
    list = list.add(5)

    val actualResult = list.get(3).flatMap(_.both)

    val expectedResult = None
    assertEquals(expectedResult, actualResult)
  }
}


