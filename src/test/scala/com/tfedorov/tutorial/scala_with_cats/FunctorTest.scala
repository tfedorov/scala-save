package com.tfedorov.tutorial.scala_with_cats

import cats.Functor
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctorTest {

  @Test
  def examplesFunctors(): Unit = {
    import cats.Functor
    import cats.instances.list._
    import cats.instances.option._

    import scala.language.higherKinds
    val list1 = List(1, 2, 3)
    val option1 = Option(123)

    val actualResult: Seq[Int] = Functor[List].map(list1)(_ * 2)
    val actualResult2 = Functor[Option].map(option1)(_.toString)

    assertEquals(List(2, 4, 6), actualResult)
    assertEquals(Some("123"), actualResult2)
  }

  @Test
  def lift(): Unit = {
    import cats.Functor
    import cats.instances.option._

    import scala.language.higherKinds

    val func = (x: Int) => x + 1
    val liftedFunc: Option[Int] => Option[Int] = Functor[Option].lift(func)
    val actualResult: Option[Int] = liftedFunc(Option(1))

    assertEquals(Some(2), actualResult)
  }

  @Test
  def customFunctor(): Unit = {
    import scala.language.higherKinds
    sealed trait ButterflyStep

    case class Egg() extends ButterflyStep {
      def hatchFromEgg() = new Caterpillar
    }
    case class Caterpillar() extends ButterflyStep {
      def toMakeADoll() = new Doll
    }
    case class Doll() extends ButterflyStep {
      def hatchFromDoll() = new Butterfly
    }
    case class Butterfly() extends ButterflyStep {
      def layEggs() = new Egg
    }

    case class Transformer[T](step: T)

    implicit val _functor = new Functor[Transformer] {
      override def map[A, B](fa: Transformer[A])(f: A => B): Transformer[B] = Transformer[B](f(fa.step))
    }
    /*
        val ph0: Transformer[Egg] = Transformer(Egg())
        val ph1: Transformer[Caterpillar] = Functor[Transformer].map(ph0)(_.hatchFromEgg())
        val ph2: Transformer[Doll] = Functor[Transformer].map(ph1)(_.toMakeADoll())
        val ph3: Transformer[Butterfly] = Functor[Transformer].map(ph2)(_.hatchFromDoll())
        val ph4: Transformer[Egg] = Functor[Transformer].map(ph3)(_.layEggs())
        assertEquals(expected, ph4)

     */
    val input = Transformer(new Butterfly)
    val f: Transformer[Butterfly] => Transformer[Egg] = Functor[Transformer].lift[Butterfly, Egg](_.layEggs())
    val actualResult = f(input)

    val expected = Transformer(new Egg)
    assertEquals(expected, actualResult)
    Functor[Transformer].map(input)(_.layEggs())
  }

  @Test
  def compositionLawCustom(): Unit = {
    import scala.language.higherKinds
    sealed trait ButterflyStep

    case class Egg() extends ButterflyStep {
      def hatchFromEgg() = new Caterpillar
    }
    case class Caterpillar() extends ButterflyStep {
      def toMakeADoll() = new Doll
    }
    case class Doll() extends ButterflyStep {
      def hatchFromDoll() = new Butterfly
    }
    case class Butterfly() extends ButterflyStep {
      def layEggs() = new Egg
    }

    case class Transformer[T](step: T)

    implicit val _functor = new Functor[Transformer] {
      override def map[A, B](fa: Transformer[A])(f: A => B): Transformer[B] = Transformer[B](f(fa.step))
    }

    val input = Transformer(Egg())
    val f: Egg => Caterpillar = (_: Egg).hatchFromEgg()
    val g: Caterpillar => Doll = (_: Caterpillar).toMakeADoll()
    val actual1: Transformer[Doll] = Functor[Transformer].map(input)(f.andThen(g))
    val functorF: Transformer[Caterpillar] = Functor[Transformer].map(input)(f)
    val actual2: Transformer[Doll] = Functor[Transformer].map(functorF)(g)

    assertTrue(actual1 == actual2)
    assertEquals(Transformer(Doll()), actual1)
    assertEquals(Transformer(Doll()), actual2)
  }

  @Test
  def compositionLaw(): Unit = {
    val f = (a: Int) => a * 2
    val g = (a: Int) => a.toString

    val fg = f.andThen(g)

    assertTrue(Option(1).map(f).map(g) == Option(1).map(fg))
    assertTrue(List(1, 2, 3).map(f).map(g) == List(1, 2, 3).map(fg))
  }
}
