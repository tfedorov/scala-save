package com.tfedorov.tutorial.books.herding_cats

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
    val inputFunc = (_: Int) * 2

    //val lifted: List[Int] => List[Int] = _ map inputFunc
    import cats.Functor
    val lifted: List[Int] => List[Int] = Functor[List].lift(inputFunc)
    val actualResult = lifted(List(1, 2, 3))

    assertEquals(List(2, 4, 6), actualResult)
  }

  @Test
  def liftVoid(): Unit = {

    val actualResult = List(1, 2, 3).void

    assertEquals(List((), (), ()), actualResult)
  }

  @Test
  def fproduct(): Unit = {
    val input = List(1, 2, 3)

    val actualResult = input fproduct ((_: Int) * 3)

    assertEquals(List((1, 3), (2, 6), (3, 9)), actualResult)
  }

  @Test
  def as(): Unit = {
    val input = List(1, 2, 3)

    val actualResult = input as "x"

    assertEquals("x" :: "x" :: "x" :: Nil, actualResult)
  }

  @Test
  def functorLaw1(): Unit = {
    val input: Either[String, Int] = Right(1)

    val actualResult = input map identity

    assertEquals(input, actualResult)
  }

  @Test
  def functorLaw2(): Unit = {
    val input: Either[String, Int] = Right(1)
    val f = (_: Int) * 3
    val g = (_: Int) + 1


    assertEquals(input.map(f.map(g)), input.map(f).map(g))
  }
}
