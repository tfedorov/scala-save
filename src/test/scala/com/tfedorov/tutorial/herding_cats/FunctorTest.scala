package com.tfedorov.tutorial.herding_cats

import cats._
import cats.implicits._
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctorTest {

  @Test
  def listFunctor(): Unit = {
    val input = List(1, 2, 3)

    val actualResult = Functor[List].map(input)(_ + 1)

    assertEquals(2 :: 3 :: 4 :: Nil, actualResult)
  }

  @Test
  def eitherFunctor(): Unit = {
    val input = Right(1): Either[String, Int]

    val actualResult1: Either[String, Int] = input map {
      _ + 1
    }
    val actualResult2: Either[String, Int] = (Left("boom!"): Either[String, Int]) map {
      _ + 1
    }

    assertEquals(Right(2), actualResult1)
    assertEquals(Left("boom!"), actualResult2)
  }
/*
  @Test
  def functionFunctor(): Unit = {
    val input: Int => Int = (x: Int) => x + 1

    val actualResult2 = ((x: Int) => x + 1) map {
      _ * 7
    }
    val actualResult = input map {
      _ * 7
    }

    assertEquals(Right(2), actualResult1)
    assertEquals(Left("boom!"), actualResult2)
  }
  */

}
