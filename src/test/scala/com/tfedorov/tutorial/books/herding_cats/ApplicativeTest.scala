package com.tfedorov.tutorial.books.herding_cats


import cats._
import cats.implicits._
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class ApplicativeTest {

  @Test
  def pureList(): Unit = {

    val actualResult = Applicative[List].pure(1)

    assertEquals(List(1), actualResult)
  }

  @Test
  def pureOption(): Unit = {

    val actualResult = Applicative[Option].pure(1)

    assertEquals(Some(1), actualResult)
  }

  @Test
  def pureOptionAdd(): Unit = {
    val F = Applicative[Option]

    val actualResult = F.ap(F.pure((_: Int) + 3))(F.pure(9))

    assertEquals(Some(12), actualResult)
  }

  @Test
  def sequence(): Unit = {
    def sequenceA[F[_] : Applicative, A](list: List[F[A]]): F[List[A]] = list match {
      case Nil => Applicative[F].pure(Nil: List[A])
      case x :: xs => (x, sequenceA(xs)) mapN {
        _ :: _
      }
    }

    val actualResult1 = sequenceA(List(1.some, 2.some))
    val actualResult2 = sequenceA(List(List(1, 2, 3), List(4, 5, 6)))

    assertEquals(Some(List(1, 2)), actualResult1)
    val expected2 = List(List(1, 4), List(1, 5), List(1, 6), List(2, 4), List(2, 5), List(2, 6), List(3, 4), List(3, 5), List(3, 6))
    assertEquals(expected2, actualResult2)
    assertEquals(None, sequenceA(List(3.some, none[Int], 1.some)))
  }
}
