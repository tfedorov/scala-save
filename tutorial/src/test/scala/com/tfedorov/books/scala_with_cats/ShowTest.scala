package com.tfedorov.books.scala_with_cats

import cats.Show
import cats.kernel.Semigroup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShowTest {

  @Test
  def noneShow(): Unit = {
    import cats.instances.option._
    import cats.instances.string._ // for Show

    val actualResult = Show.apply[Option[String]]

    assertEquals("None", actualResult.show(None))
  }

  @Test
  def stringShow(): Unit = {
    import cats.implicits._

    val actualResult = "abc".show

    assertEquals("abc", actualResult)
  }

  @Test
  def customShow(): Unit = {
    case class Cats(sound: String)
    implicit val show: Show[Cats] = Show.show(t => s"Cat says '${t.sound}'")

    import cats.implicits._
    val actualResult = Cats("meow").show

    assertEquals("Cat says 'meow'", actualResult)
  }

  @Test
  def customShow2(): Unit = {
    import cats.implicits._

    println(Semigroup[Int].combine(1, 2))
    println(Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)))
    println(Semigroup[Option[Int]].combine(Option(1), Option(2)))
    println(Semigroup[Option[Int]].combine(Option(1), None))
    println(Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6))
    println(Map("foo" -> Map("bar" -> 5)).combine(Map("foo" -> Map("bar" -> 6), "baz" -> Map())))
  }
}
