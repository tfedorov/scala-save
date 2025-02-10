package com.tfedorov.tutorial.`type`.generics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HigherKindedType {

  /*
  Scala can abstract over “higher kinded” types.
  For example, suppose that you needed to use several types of containers for several types of data.
  You might define a Container interface that might be implemented by means of several container types: an Option, a List, etc.
  You want to define an interface for using values in these containers without nailing down the values’ type.

   */
  @Test
  def higherKindedParamless(): Unit = {
    trait Functor[F[_]] {
      def map[A, B](fa: F[A])(f: A => B): F[B]
    }
    val functorL = new Functor[List] {
      override def map[A, B](functionArg: List[A])(f: A => B): List[B] = functionArg.map(f)
    }
    val input = 1 :: 2 :: 3 :: Nil

    val actualResult = functorL.map(input)("a" * _)

    assertEquals("a" :: "aa" :: "aaa" :: Nil, actualResult)
  }

  @Test
  def higherKindedPs(): Unit = {
    trait Converter[F[_], A] {
      def convert[B](fa: F[A])(f: A => B): F[B]
    }
    val converterList = new Converter[List, Int] {
      override def convert[B](fa: List[Int])(f: Int => B): List[B] = fa.map(f)
    }
    val converterOpt = new Converter[Option, Int] {
      override def convert[B](fa: Option[Int])(f: Int => B): Option[B] = fa.map(f)
    }
    val input1 = 1 :: 2 :: 3 :: Nil

    val actualResult1 = converterList.convert(input1)("a" * _)
    val actualResult2 = converterList.convert(input1)(_.toFloat)
    val actualResult3 = converterOpt.convert(Some(2))("a" * _)
    val actualResult4 = converterOpt.convert(None)(_.toFloat)

    assertEquals("a" :: "aa" :: "aaa" :: Nil, actualResult1)
    assertEquals(1.0 :: 2.0 :: 3.0 :: Nil, actualResult2)
    assertEquals(Some("aa"), actualResult3)
    assertEquals(None, actualResult4)
  }

}
