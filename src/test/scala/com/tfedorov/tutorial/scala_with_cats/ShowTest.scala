package com.tfedorov.tutorial.scala_with_cats

import cats.Show
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
}
