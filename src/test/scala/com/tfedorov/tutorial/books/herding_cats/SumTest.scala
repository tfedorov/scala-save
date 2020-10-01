package com.tfedorov.tutorial.books.herding_cats

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SumTest {

  @Test
  def monoidIntTest(): Unit = {
    trait Monoid[A] {
      def mappend(el1: A, el2: A): A

      def mzero: A
    }
    def sum[T](xs: List[T])(implicit m: Monoid[T]): T = xs.foldLeft(m.mzero)(m.mappend)

    implicit object IntMonoid extends Monoid[Int] {
      def mappend(a: Int, b: Int): Int = a + b

      def mzero: Int = 0
    }

    val actualResult = sum(1 :: 2 :: 3 :: Nil)

    assertEquals(6, actualResult)
  }

  @Test
  def monoidSetTest(): Unit = {
    trait Monoid[A] {
      def mappend(el1: A, el2: A): A

      def mzero: A
    }
    def sum[T](xs: List[T])(implicit m: Monoid[T]): T = xs.foldLeft(m.mzero)(m.mappend)


    implicit object DateMonoid extends Monoid[Set[_]] {
      def mappend(a: Set[_], b: Set[_]): Set[_] = a ++ b

      def mzero: Set[_] = Set.empty
    }

    val list: List[Set[_]] = Set(1) :: Set(2) :: Set(1, 3) :: Nil
    val actualResult = sum(list)

    assertEquals(Set(1, 2, 3), actualResult)
  }

  @Test
  def foldLeftTest(): Unit = {
    trait Monoid[A] {
      def mappend(el1: A, el2: A): A

      def mzero: A
    }

    implicit object IntMonoid extends Monoid[Int] {
      def mappend(a: Int, b: Int): Int = a + b

      def mzero: Int = 0
    }

    trait FoldLeft[F[_]] {
      def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
    }

    implicit val foldLeftList: FoldLeft[List] = new FoldLeft[List] {
      def foldLeft[A, B](list: List[A], zero: B, opFunc: (B, A) => B): B = list.foldLeft(zero)(opFunc)
    }

    //def sum[M[A] : FoldLeft, A: Monoid](xs: M[A])(implicit m: Monoid[A], fl: FoldLeft[M]): A = {
    //higher-kinded type
    def sum[M[A], B](xs: M[B])(implicit m: Monoid[B], fl: FoldLeft[M]): B = {
      fl.foldLeft(xs, m.mzero, m.mappend)
    }

    val actualResult = sum(List(1, 2, 3, 4))

    assertEquals(10, actualResult)
  }

}
