package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HigherKindedType {

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
    trait Converter[F[A], A] {
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
