package com.tfedorov.tutorial.scala_with_cats

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MonoidTest {

  /*
    •an operation combine with type (A, A) => A
    • an element empty of type A
    */
  trait Monoid[A] {
    def combine(x: A, y: A): A

    def empty: A
  }

  @Test
  def associativeLaw(): Unit = {

    val m: Monoid[Option[Int]] = new Monoid[Option[Int]] {
      override def combine(x: Option[Int], y: Option[Int]): Option[Int] = {
        x.flatMap(el => y.map(_ + el))
      }

      override def empty: Option[Int] = None
    }

    val actualResult = m.combine(Some(1), m.combine(Some(2), Some(3)))

    val expectedResult = m.combine(m.combine(Some(1), Some(2)), Some(3))
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def identityLaw(): Unit = {

    val m: Monoid[Int] = new Monoid[Int] {
      override def combine(x: Int, y: Int): Int = x + y

      override def empty: Int = 0
    }

    val actualResult = m.combine(1, m.empty)

    val expectedResult = m.combine(m.empty, 1)
    assertEquals(expectedResult, actualResult)
    assertEquals(1, actualResult)
  }

  @Test
  def identityLawNone(): Unit = {

    val m: Monoid[Option[Int]] = new Monoid[Option[Int]] {
      override def combine(x: Option[Int], y: Option[Int]): Option[Int] = {
        x.flatMap(el => y.map(_ + el))
      }

      override def empty: Option[Int] = None
    }

    val actualResult = m.combine(Some(1), m.empty)

    val expectedResult = m.combine(m.empty, Some(1))
    assertEquals(expectedResult, actualResult)
    assertEquals(None, actualResult)
  }

  @Test
  def semigroup(): Unit = {
    trait Semigroup[A] {
      def combine(x: A, y: A): A
    }
    trait Monoid[A] extends Semigroup[A] {
      def empty: A
    }
    val semigroup = new Semigroup[Option[Int]] {
      override def combine(x: Option[Int], y: Option[Int]): Option[Int] = x.flatMap(xel => y.map(_ + xel))
    }
    val actualResult = semigroup.combine(Some(2), Some(2))

    assertEquals(Some(4), actualResult)
  }

  @Test
  def catsMonoids(): Unit = {
    import cats.Monoid
    import cats.instances.string._ // for Monoid

    val actualResult = Monoid[String].combine("Hi ", "there")

    assertEquals("Hi there", actualResult)
    assertEquals("", Monoid[String].empty)
  }

  @Test
  def monoidSyntax(): Unit = {
    import cats.Monoid
    import cats.instances.string._
    import cats.syntax.semigroup._ // for |+|

    val actualResult = "Hi " |+| "there" |+| Monoid[String].empty

    assertEquals("Hi there", actualResult)
  }

  @Test
  def addingInt(): Unit = {

    def add(items: List[Int]): Int = {
      import cats.Monoid
      import cats.instances.int._
      //import cats.syntax.semigroup._ // for |+|
      Monoid[Int].combineAll(items)
      //items.foldLeft(Monoid[Int].empty)(_ |+| _)
    }

    val actualResult = add(2 :: 2 :: Nil)

    assertEquals(4, actualResult)
  }

  @Test
  def addingCustom(): Unit = { // for |+|
    case class Order(totalCost: Double, quantity: Double)
    val monoidOrder = new cats.Monoid[Order] {
      override def empty: Order = Order(0, 0)

      override def combine(x: Order, y: Order): Order = Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
    }

    def add(items: List[Order]): Order = {
      import cats.Monoid
      //import cats.syntax.semigroup._ // for |+|

      Monoid[Order](monoidOrder).combineAll(items)

    }

    val actualResult = add(Order(10, 1) :: Order(20, 2) :: Nil)

    assertEquals(Order(30, 3), actualResult)
  }

  @Test
  def addingMap(): Unit = {
    import cats.instances.int._
    import cats.instances.map._
    import cats.syntax.semigroup._

    val map1: Map[String, Int] = Map("a" -> 1, "b" -> 2)
    val map2: Map[String, Int] = Map("b" -> 3, "d" -> 4)

    val actualResult: Map[String, Int] = map1 |+| map2

    assertEquals(Map("b" -> 5, "d" -> 4, "a" -> 1), actualResult)
  }
}
