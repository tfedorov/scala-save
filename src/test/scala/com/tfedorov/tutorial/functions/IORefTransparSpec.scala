package com.tfedorov.tutorial.functions

import cats.effect.IO
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import java.util.Date
import scala.concurrent.duration.{FiniteDuration, MINUTES}
import scala.concurrent.{Await, Future}

/**
 * Referential Transparency
 */
class IORefTransparSpec extends FlatSpec {

  "IO flat map " should "executes twice. It is Referential Transparency." in {
    val io: IO[Long] = IO {
      val execTime = new Date().getTime
      println("executed in" + execTime)
      Thread.sleep(100)
      execTime
    }

    val actualIOResult: IO[(Long, Long)] = for {
      n1 <- io
      n2 <- io
    } yield (n1, n2)
    import cats.effect.unsafe.implicits.global
    val actualResult: (Long, Long) = actualIOResult.unsafeRunSync()

    actualResult._1 shouldNot be(actualResult._2)
  }

  "Future flat map " should "executes once. It is NOT Referential Transparency." in {
    import scala.concurrent.ExecutionContext.Implicits.global
    val future = Future {
      val execTime = new Date().getTime
      println("executed in" + execTime)
      Thread.sleep(100)
      execTime
    }

    val actualFutureResult: Future[(Long, Long)] = for {
      n1 <- future
      n2 <- future
    } yield (n1, n2)
    val actualResult: (Long, Long) = Await.result(actualFutureResult, FiniteDuration(2, MINUTES))

    actualResult._1 should be(actualResult._2)
  }

  it should "executes twice. It is NOT Referential Transparency." in {
    import scala.concurrent.ExecutionContext.Implicits.global
    def getAndPrintTime: Long = {
      val execTime = new Date().getTime
      println("executed in" + execTime)
      Thread.sleep(100)
      execTime
    }

    val actualFutureResult: Future[(Long, Long)] = for {
      n1 <- Future {
        getAndPrintTime
      }
      n2 <- Future {
        getAndPrintTime
      }
    } yield (n1, n2)
    val actualResult: (Long, Long) = Await.result(actualFutureResult, FiniteDuration(2, MINUTES))

    actualResult._1 shouldNot be(actualResult._2)
  }

}
