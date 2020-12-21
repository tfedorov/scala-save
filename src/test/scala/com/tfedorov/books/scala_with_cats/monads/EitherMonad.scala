package com.tfedorov.books.scala_with_cats.monads

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EitherMonad {

  @Test
  def monadEither(): Unit = {
    import cats.syntax.either._ // for asRight
    val a = 3.asRight[String]
    val b = 4.asRight[String]

    val actualResult = for {
      x <- a
      y <- b
    } yield x * 2 + y * 2

    assertEquals(Right(14), actualResult)
  }

  @Test
  def monadEitherString(): Unit = {
    import cats.syntax.either._ // for asRight
    val a = "ab".asRight[Int]
    val b = "cd".asRight[String]

    val actualResult = for {
      x <- a
      y <- b
    } yield x * 2 + y * 2

    assertEquals(Right("ababcdcd"), actualResult)
  }

  @Test
  def monadEitherOrElse(): Unit = {
    import cats.syntax.either._

    val actualResult1: Int = "Error".asLeft[Int].getOrElse(0)
    val actualResult2: Either[String, Int] = "Error".asLeft[Int].orElse(2.asRight[String])
    val actualResult3: Either[String, Int] = "Error".asLeft[Int]

    assertEquals(0, actualResult1)
    assertEquals(Right(2), actualResult2)
    assertEquals(Left("Error"), actualResult3)
  }

  @Test
  def monadEitherEnsure(): Unit = {
    import cats.syntax.either._

    val actualResult1: Either[String, Int] = 1.asRight[String].ensure("Must be non-negative!")(_ > 0)
    val actualResult2: Either[String, Int] = (-1).asRight[String].ensure("Must be non-negative!")(_ > 0)

    assertEquals(Right(1), actualResult1)
    assertEquals(Left("Must be non-negative!"), actualResult2)
  }

  @Test
  def monadEitherRecover(): Unit = {
    import cats.syntax.either._
    val pfSugar: PartialFunction[String, Int] = {
      case "error" => -1
    }
    val pfNew: PartialFunction[String, Int] = new PartialFunction[String, Int]() {
      override def isDefinedAt(x: String): Boolean = x.equalsIgnoreCase("error")

      override def apply(v1: String): Int = -1
    }

    val actualResult1: Either[String, Int] = "error".asLeft[Int].recover(pfSugar)
    val actualResult2: Either[String, Int] = "error".asLeft[Int].recover(pfNew)

    assertEquals(Right(-1), actualResult1)
    assertEquals(Right(-1), actualResult2)
  }

  @Test
  def errorHandlingStr(): Unit = {
    import cats.syntax.either._

    val actualResult: Either[String, Int] = for {
      a <- 1.asRight[String]
      b <- 0.asRight[String]
      c <- if (b == 0) "DIV0".asLeft[Int]
      else (a / b).asRight[String]
    } yield c * 100

    assertEquals(Left("DIV0"), actualResult)
  }

  @Test
  def errorHandling(): Unit = {
    sealed trait LoginError extends Product with Serializable

    final case class UserNotFound(username: String) extends LoginError

    final case class PasswordIncorrect(username: String) extends LoginError

    case object UnexpectedError extends LoginError

    case class User(username: String, password: String)

    def handleError(error: LoginError): String = error match {
      case UserNotFound(u) => s"User not found: $u"
      case PasswordIncorrect(u) => s"Password incorrect: $u"
      case UnexpectedError => s"Unexpected error"
    }
    import cats.syntax.either._
    type LoginResult = Either[LoginError, User]

    val correctUser: LoginResult = User("dave", "passw0rd").asRight
    // actualResult1: LoginResult = Right(User(dave,passw0rd))
    val incorrectUser: LoginResult = UserNotFound("dave").asLeft
    // actualResult2: LoginResult = Left(UserNotFound(dave))
    val actualResult1: String = correctUser.fold(handleError, _.toString)
    // User(dave,passw0rd)
    val actualResult2: String = incorrectUser.fold(handleError, _.toString)
    // User not found: dave

    assertEquals("User(dave,passw0rd)", actualResult1)
    assertEquals("User not found: dave", actualResult2)
  }
}
