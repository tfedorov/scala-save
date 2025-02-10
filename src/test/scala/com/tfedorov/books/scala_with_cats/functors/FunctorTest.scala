package com.tfedorov.books.scala_with_cats.functors

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class FunctorTest {

  /*
  Functors for collecঞons are extremely important, as they transform each element independently of the rest.
   This allows us to parallelise or distribute
   transformaঞons on large collecঞons, a technique leveraged heavily in “mapreduce” frameworks
   */

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
  def compositionLaw(): Unit = {
    val f = (a: Int) => a * 2
    val g = (a: Int) => a.toString

    val fg = f.andThen(g)

    assertTrue(Option(1).map(f).map(g) == Option(1).map(fg))
    assertTrue(List(1, 2, 3).map(f).map(g) == List(1, 2, 3).map(fg))
  }
/*
  @Test
  def functorSyntax(): Unit = {
    import cats.instances.all._
    import cats.syntax.functor._
    //import cats.Functor.ToFunctorOps

    val func1: Int => Int = (a: Int) => a + 1
    val func2 = (a: Int) => a * 2
    val func3 = (a: Int) => a + "!"
    //val func4: Function1[Int, String] = toFunctorOps(toFunctorOps(func1)(catsStdMonadForFunction1).map(func2))(catsStdMonadForFunction1).map(func3)
    val func4 = func1.map(func2).map(func3)
    val actualResult = func4(123)

    assertEquals("248!", actualResult)
  }
*/
  @Test
  def functorSyntax2(): Unit = {
    import cats.Functor
    import cats.instances.all._
    import cats.syntax.functor._

    def foo(int: Int): Int = int + 1

    // def doMath[F[Int]](start: F[Int])(implicit functor: Functor[F]): F[Int] = toFunctorOps(start)(functor).map((n: Int) => n.*(2).+(1))
    def doMath[F[Int]](start: F[Int])(implicit functor: Functor[F]): F[Int] = start.map(n => n * 2 + 1)

    val actualResult1 = doMath(Option(20))
    val actualResult2 = doMath(List(1, 2, 3))

    assertEquals(Some(41), actualResult1)
    assertEquals(List(3, 5, 7), actualResult2)
  }

  @Test
  def branchingOutFunctors(): Unit = {
    sealed trait Tree[+A]
    final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
    final case class Leaf[A](value: A) extends Tree[A]

    val input: Tree[Int] = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))
    import cats.Functor
    import cats.syntax.functor._
    implicit val _functor: Functor[Tree] = new Functor[Tree] {
      override def map[A, B](tree: Tree[A])(func: A => B): Tree[B] = {
        tree match {
          case Leaf(value) => Leaf(func(value))
          case Branch(left, right) => Branch(map(left)(func), map(right)(func))
        }
      }
    }

    val actualResult = input.map(el => (el + 2).toString)


    assertEquals(Branch(Branch(Leaf("3"), Leaf("4")), Leaf("5")), actualResult)
  }

  @Test
  def branchingOutFunctorsVariance(): Unit = {
    sealed trait Tree[+A]
    final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
    final case class Leaf[A](value: A) extends Tree[A]

    object Tree {
      def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
        Branch(left, right)

      def leaf[A](value: A): Tree[A] =
        Leaf(value)
    }
    import cats.Functor
    import cats.syntax.functor._
    implicit val _functor: Functor[Tree] = new Functor[Tree] {
      override def map[A, B](tree: Tree[A])(func: A => B): Tree[B] = {
        tree match {
          case Leaf(value) => Leaf(func(value))
          case Branch(left, right) => Branch(map(left)(func), map(right)(func))
        }
      }
    }

    //val actualResultLeaf: Tree[Int] = toFunctorOps(Tree.leaf(100))(_functor).map(iF => iF * 2)
    val actualResultLeaf = Tree.leaf(100).map(_ * 2)
    val actualResultBranch = Tree.branch(Tree.leaf(10), Tree.leaf(20)).map(_ * 2)

    assertEquals(Leaf(200), actualResultLeaf)
    assertEquals(Branch(Leaf(20), Leaf(40)), actualResultBranch)
  }
}
