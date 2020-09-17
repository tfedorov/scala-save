package com.tfedorov.tutorial.herding_cats


import cats.Functor
import cats.implicits._
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctorTest {

  @Test
  def listFunctor(): Unit = {
    val input = List(1, 2, 3)

    //val expl = cats.instances.list.catsStdInstancesForList
    //val actualResult = Functor[List](expl).map(input)(_ + 1)
    //val actualResult = Functor[List].map(input)(_ + 1)
    val actualResult = input map (_ + 1)

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

  @Test
  def functionFunctorHerds(): Unit = {
    val inputFunc: Int => Int = (x: Int) => x + 1

    val composedFunc = inputFunc.map({
      _ * 7
    })
    val actualResult = composedFunc(3)

    assertEquals(28, actualResult)
  }

  @Test
  def functionFunctorCompose(): Unit = {
    val f1 = (_: Int) * 3
    val f2 = (_: Int) + 100

    val composedFunction = f1 map f2
    val actualResult = composedFunction(1)

    assertEquals(103, actualResult)
  }

  @Test
  def functionFunctor(): Unit = {

    val func1: Int => Double = (x: Int) => x.toDouble

    val func2: Double => Double = (y: Double) => y * 2

    (func1 map func2) (1) // composition using map

    (func1 andThen func2) (1) // composition using andThen
    // res8: Double = 2.0

    func2(func1(1)) // composition written out by hand
  }

  @Test
  def lift(): Unit = {
    val lifted = Functor[List].lift((_: Int) * 2)

    val actualResult = lifted(List(1, 2, 3))

    assertEquals(List(2, 4, 6), actualResult)
  }
}
