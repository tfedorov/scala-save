package com.tfedorov.tutorial.books.scala_with_cats.monads

import cats.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MonadsTest {
  /*
  Informally, a monad is anything with a constructor and a flatMap method.

  A monad is a mechanism for sequencing computations.

  Laws
  • pure, of type A => F[A];
  • flatMap¹, of type (F[A], A => F[B]) => F[B].

  Left identity:
  pure(a).flatMap(func) == func(a)

  Right identity:
  m.flatMap(pure) == m

  Associatyvity:
  m.flatMap(f).flatMap(g) == m.flatMap(x => f(x).flatMap(g))
   */

  @Test
  def testPure(): Unit = {
    import cats.Monad
    import cats.instances.list._
    import cats.instances.option._ // for Monad

    val actualResult = Monad[Option].pure(3)
    val actualResult2 = Monad[List].pure(3)

    assertEquals(Some(3), actualResult)
    assertEquals(3 :: Nil, actualResult2)
  }

  @Test
  def testPureSyntax(): Unit = {
    import cats.instances.list._
    import cats.instances.option._
    import cats.syntax.applicative._ // for pure

    val actualResult1 = 1.pure[Option]
    val actualResult2 = 1.pure[List]

    assertEquals(Some(1), actualResult1)
    assertEquals(1 :: Nil, actualResult2)
  }

  @Test
  def testPureSyntaxCustom(): Unit = {
    import cats.Monad
    import cats.syntax.flatMap._
    import cats.syntax.functor._

    import scala.language.higherKinds
    /*
        def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
          for {
            x <- a
            y <- b
          } yield x*x + y*y
        */
    def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] = a.flatMap(x => b.map(y => x * x + y * y))
    import cats.instances.list._
    import cats.instances.option._ // for Monad

    val actualResult1 = sumSquare(Option(3), Option(4))
    val actualResult2 = sumSquare(List(1, 2, 3), List(4, 5))

    assertEquals(Some(25), actualResult1)
    assertEquals(List(17, 26, 20, 29, 25, 34), actualResult2)
  }

  @Test
  def testFlatMap(): Unit = {
    import cats.Monad
    import cats.instances.list._
    import cats.instances.option._ // for Monad

    val opt1 = Monad[Option].pure(3)
    //val actualResult1 = Monad[Option](cats.instances.option.catsStdInstancesForOption).flatMap(opt1)((a: Int) => Some(a + 2))
    val actualResult1 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
    //val list2 = Monad[List](cats.instances.list.catsStdInstancesForList).flatMap(List(1, 2, 3))((a: Int) => List(a, a * 10))
    val list2 = Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10))
    val actualResult2 = Monad[List].map(list2)(a => a + 123)

    assertEquals(Some(5), actualResult1)
    assertEquals(List(124, 133, 125, 143, 126, 153), actualResult2)
  }

  @Test
  def identityMonad(): Unit = {
    import cats.Monad
    import cats.syntax.flatMap._
    import cats.syntax.functor._

    import scala.language.higherKinds

    def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] = a.flatMap(x => b.map(y => x * x + y * y))

    /*
    Error:(112, 25) no type parameters for method sumSquare: (a: F[Int], b: F[Int])(implicit evidence$2: cats.Monad[F])F[Int] exist so that it can be applied to arguments (Int, Int)
 --- because ---
argument expression's type is not compatible with formal parameter type;
 found   : Int
 required: ?F[Int]
    val actualResult1 = sumSquare(3, 4)
     */
    //val actualResult1 = sumSquare(3, 4)

    import cats.Id
    val actualResult = sumSquare(3: Id[Int], 4: Id[Int])

    assertEquals(25, actualResult)
  }

  @Test
  def identityMonadId(): Unit = {
    import cats.Monad
    import cats.syntax.flatMap._
    import cats.syntax.functor._

    import scala.language.higherKinds

    val a = Monad[Id].pure(3)
    val b = Monad[Id].flatMap(a)(_ + 1)
    val actualResult = for {
      x <- a
      y <- b
    } yield x + y

    assertEquals(7, actualResult)
  }

}
