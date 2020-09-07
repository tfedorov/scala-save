package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ImplicitTest {

  @Test
  def viewBounds(): Unit = {
    implicit def strToInt(x: String) = x.toInt

    class Container[A <% Int] {
      def addIt(x: A): Int = 123 + x
    }

    val strResult: Int = new Container[String].addIt("123")
    val intResult: Int = new Container[Int].addIt(123)
    //new Container[Float].addIt(123.0F)
    //Error:(18, 5) not enough arguments for constructor Container: (implicit evidence$1: Float => Int)Container[Float].
    //Unspecified value parameter evidence$1.
    //    new Container[Float].addIt(123.0F)

    assertEquals(246, strResult)
    assertEquals(246, intResult)
  }

  /*
  Methods may ask for some kinds of specific “evidence” for a type without setting up strange objects as with Numeric.
  Instead, you can use one of these type-relation operators:
  +---------+--------------------------+
  | Generic |        Relations         |
  +---------+--------------------------+
  | A =:= B | A must be equal to B     |
  | A <:< B | A must be a subtype of B |
  | A <%< B | A must be viewable as B  |
  +---------+--------------------------+
   */

  @Test
  def otherTypeBounds(): Unit = {
    class Container[A](value: A) {
      def addIt(implicit evidence: A =:= Int) = 123 + value
    }
    implicit def strToInt(x: String) = x.toInt

    val intResult = new Container(123).addIt


    assertEquals(246, intResult)
  }

  @Test
  def implicitConversionsAsParameters(): Unit = {
    def getIndex[T, CC](seqSource: CC, value: T)(implicit conversion: CC => Seq[T]) = {
      //val seq: Seq[T] = conversion(seqSource)
      //val seq: Seq[T] = seqSource
      //seq.indexOf(value)
      seqSource.indexOf(value)
    }

    val indexA = getIndex("abc", 'a')

    case class FullName(firstName: String, familyName: String, fatherName: String)
    val myName = FullName("Fedorov", "Taras", "Serhiy")

    implicit def confFullName(inp: FullName): Seq[String] = inp.firstName :: inp.familyName :: inp.fatherName :: Nil

    val indexTaras = getIndex(myName, "Taras")

    assertEquals(1, indexTaras)
    assertEquals(0, indexA)
  }

}
