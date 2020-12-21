package com.tfedorov.books.scala_with_cats.functors

import cats.Functor
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctorCustomTest {

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
    val egg2Cater: Egg => Caterpillar = (_: Egg).hatchFromEgg()
    val cater2Doll: Caterpillar => Doll = (_: Caterpillar).toMakeADoll()
    val egg2Doll: Egg => Doll = egg2Cater.andThen(cater2Doll)
    val actual1: Transformer[Doll] = Functor[Transformer].map(input)(egg2Doll)
    val functorF: Transformer[Caterpillar] = Functor[Transformer].map(input)(egg2Cater)
    val actual2: Transformer[Doll] = Functor[Transformer].map(functorF)(cater2Doll)

    assertTrue(actual1 == actual2)
    assertEquals(Transformer(Doll()), actual1)
  }
}
