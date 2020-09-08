package com.tfedorov.tutorial

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import scala.collection.immutable

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

  @Test
  def viewBoundsMethod(): Unit = {
    implicit def strToInt(x: String) = x.toInt

    val actual = math.max("123", 111)

    assertEquals(123, actual)
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

  @Test
  def contextBoundsImplicitly(): Unit = {
    //def foo[A : Ordered] {}
    //def foo[A](implicit x: Ordered[A]) {}
    //implicitly[Ordering[Int]]

    trait Container[M[_]] {
      def put[A](x: A): M[A]

      def get[A](m: M[A]): A
    }

    implicit val listContainer = new Container[List] {
      def put[A](x: A) = List(x)

      def get[A](m: List[A]): A = m.head
    }

    implicit val optionContainer = new Container[Some] {
      def put[A](x: A): Some[A] = Some(x)

      def get[A](m: Some[A]): A = m.get
    }

    def tupleize[M[_] : Container, A, B](fst: M[A], snd: M[B]): M[(A, B)] = {
      val c = implicitly[Container[M]]
      c.put(c.get(fst), c.get(snd))
    }

    val actualOpts: Option[(Int, String)] = tupleize(Some(1), Some("2"))
    val actualLists: immutable.Seq[(Int, Int)] = tupleize(List(1), List(2))

    assertEquals(Some((1, "2")), actualOpts)
    assertEquals(List((1, 2)), actualLists)
  }

}
