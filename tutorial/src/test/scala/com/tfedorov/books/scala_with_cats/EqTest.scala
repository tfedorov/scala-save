package com.tfedorov.books.scala_with_cats

import cats.kernel.Eq
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class EqTest {

  @Test
  def intEq(): Unit = {

    import cats.instances.int._
    import cats.syntax.eq._

    val actualResult = 123 === 123

    assertTrue(actualResult)
  }

  @Test
  def customEq(): Unit = {
    case class Cats(breed: String, sound: String)
    import cats.syntax.eq._ // for Eq
    implicit val eqCat: Eq[Cats] = Eq.instance[Cats] { (cat1, cat2) =>
      import cats.instances.string._
      cat1.breed === cat2.breed
    }
    val cat1 = Cats("Cheshire", "meow")
    val cat2 = Cats("Cheshire", "meow meow meow")

    val actualResult = cat1 === cat2

    assertTrue(actualResult)
  }
}
