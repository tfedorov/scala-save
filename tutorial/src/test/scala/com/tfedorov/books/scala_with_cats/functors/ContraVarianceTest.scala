package com.tfedorov.books.scala_with_cats.functors

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

import scala.math.Equiv.{fromComparator, fromFunction}

class ContraVarianceTest {

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

    implicit def boxPrintable[A](implicit p: Printable[A]): Printable[Box[A]] =
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

  @Test
  def contravariantShow: Unit = {
    case class Money(amount: Int)
    case class Salary(moneySize: Money)

    import cats._
    import cats.implicits._
    implicit val showMoney: Show[Money] = Show.show[Money]("$" + _.amount)
    implicit val showSalary: Show[Salary] = showMoney.contramap[Salary](_.moneySize)
    //val salary: Salary = Salary(Money(1000))
    //val actualResult: String = cats.implicits.toShow[Salary](target = salary)(tc = showSalary).show
    val actualResult: String = Salary(Money(1000)).show

    assertEquals("$1000", actualResult)
  }

  @Test
  def contravariantOrdering: Unit = {
    case class Money(amount: Int)
    case class Salary(moneySize: Money)
    implicit val _moneyOrdering: Ordering[Money] = Ordering.by[Money, Int](_.amount)
    val contravariantF: Salary => Money = (_: Salary).moneySize
    implicit val _salOrdering: Ordering[Salary] = _moneyOrdering.on[Salary](f = contravariantF)

    //val ord: Money => Ordered[Money] = Ordered.orderingToOrdered[Money](_)(ord = _moneyOrdering)
    //val actualResult = Ordered.orderingToOrdered[Money](x = Money(100))(ord = moneyOrdering).<(Money(200))
    val actualResult = Ordered.orderingToOrdered[Salary](x = Salary(Money(100)))(ord = _salOrdering).<(Salary(Money(200)))
//    val actualResult = Salary(Money(100)) < Salary(Money(200))

    assertTrue(actualResult)
  }

  @Test
  def contravariantCats: Unit = {
    import cats.instances.string._
    import cats.{Contravariant, Show}
    val showString = Show[String]
    case class Money(amount: Int)
    case class Salary(moneySize: Money)
    val showMoney: Show[Money] = Contravariant[Show].contramap(showString)((m: Money) => "Money amount = " + m.amount)
    val showSalary: Show[Salary] = Contravariant[Show].contramap(showMoney)((m: Salary) => m.moneySize)
    val showSalary2: Show[Salary] = Contravariant[Show].contramap(showString)((m: Salary) => "Salary amount = " + m.moneySize)

    val actualResult1 = showMoney.show(Money(10))
    val actualResult2 = showSalary.show(Salary(Money(10)))
    val actualResult3 = showSalary2.show(Salary(Money(10)))

    assertEquals("Money amount = 10", actualResult1)
    assertEquals("Money amount = 10", actualResult2)
    assertEquals("Salary amount = Money(10)", actualResult3)
  }


}
