package com.tfedorov.tutorial.scala_with_cats

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class VarianceFunctorTest {

  @Test
  def contravariant(): Unit = {
    trait Printable[A] {
      def format(value: A): String

      def contramap[B](func: B => A): Printable[B] =
        new Printable[B] {
          def format(value: B): String =
            ???
        }
    }
    implicit val stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          "\"" + value + "\""
      }
    implicit val booleanPrintable: Printable[Boolean] =
      new Printable[Boolean] {
        def format(value: Boolean): String =
          if (value) "yes" else "no"
      }

    val actualResult1 = stringPrintable.format("hello")
    val actualResult2 = booleanPrintable.format(true)

    assertEquals("\"hello\"", actualResult1)
    assertEquals("yes", actualResult2)
  }

  @Test
  def contravariantChanged(): Unit = {
    trait Printable[A] {
      self =>
      def format(value: A): String

      implicit def contramap[B](func: B => A): Printable[B] =
        new Printable[B] {
          def format(value: B): String =
            self.format(func(value))
        }
    }

    implicit val _stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          "[" + value + "]"
      }

    implicit val _booleanPrintable = _stringPrintable.contramap(if (_: Boolean) "yes" else "no")

    def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

    val actualResult1 = format("hello")
    val actualResult2 = format(true)

    assertEquals("[hello]", actualResult1)
    assertEquals("[yes]", actualResult2)
  }

  @Test
  def contravariantBox(): Unit = {
    trait Printable[A] {
      self =>
      def format(value: A): String

      def contramap[B](func: B => A): Printable[B] =
        new Printable[B] {
          def format(value: B): String =
            self.format(func(value))
        }
    }

    implicit val stringPrintable: Printable[String] =
      new Printable[String] {
        def format(value: String): String =
          "\"" + value + "\""
      }
    implicit val booleanPrintable: Printable[Boolean] =
      new Printable[Boolean] {

        def format(value: Boolean): String = if (value) "yes" else "no"
      }

    def format[A](value: A)(implicit p: Printable[A]): String = p.format(value)

    final case class Box[A](value: A)

    implicit def boxPrintable[A](implicit p: Printable[A]) =
      new Printable[Box[A]] {
        def format(box: Box[A]): String =
          p.format(box.value)
      }

    //val actualResult1 = format(Box("hello world"))
    val actualResult1 = format(Box("hello world"))(boxPrintable(stringPrintable))
    //val actualResult2 = format(Box(true))
    val actualResult2 = format(Box(true))(boxPrintable(booleanPrintable))

    assertEquals("\"hello world\"", actualResult1)
    assertEquals("yes", actualResult2)
  }

  @Test
  def equiv(): Unit = {
    import java.lang.String.CASE_INSENSITIVE_ORDER

    import scala.math.Equiv
    import scala.math.Equiv.{fromComparator, fromFunction}

    val equevStr: Equiv[String] = fromComparator(CASE_INSENSITIVE_ORDER)
    val int2Str: Int => String = _.toString
    val function: (Int, Int) => Boolean = (x: Int, y: Int) => equevStr.equiv(int2Str(x), int2Str(y))
    val equevInt: Equiv[Int] = fromFunction(function)
    val actualResult: Boolean = equevInt.equiv(1, 2)

    assertFalse(actualResult)
  }

  @Test
  def contravariantCustom(): Unit = {

    sealed trait Comparator[A] {
      self =>
      def compare(white: A, black: A): Boolean

      def contramap[B](f: B => A): Comparator[B] = new Comparator[B] {
        override def compare(white: B, black: B): Boolean = self.compare(f(white), f(black))
      }
    }
    val intComp = new Comparator[Int] {
      override def compare(white: Int, black: Int): Boolean = white > black
    }

    def str2Int(input: String): Int = input.toLowerCase match {
      case "horse" => 3
      case "rook" => 7
      case "queen" => 12
    }

    val figureComp = intComp.contramap(str2Int)

    val actualResult = figureComp.compare("Horse", "Rook")

    assertFalse(actualResult)
    assertTrue(figureComp.compare("Rook", "Horse"))
  }
}
