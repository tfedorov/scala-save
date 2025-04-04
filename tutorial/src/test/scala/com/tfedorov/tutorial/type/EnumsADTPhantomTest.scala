package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions.assertNotNull

import scala.collection.immutable

class EnumsADTPhantomTest {

  import org.junit.jupiter.api.Assertions.assertEquals
  import org.junit.jupiter.api.Test

  @Test
  def ADTTest(): Unit = {

    sealed abstract class Http extends Product with Serializable

    object Http {

      final case object Ok extends Http

      final case object Error extends Http

      final case object Fail extends Http

    }

    def response(number: Int): Http = number match {
      case fNum if fNum >= 200 && fNum < 300 => Http.Ok
      case fNum if fNum >= 400 && fNum < 500 => Http.Fail
      case fNum if fNum >= 500 => Http.Error
    }

    assertEquals(Http.Fail, response(404))
    assertEquals(Http.Ok, response(200))
  }

  @Test
  def NoProductSerializableTest(): Unit = {

    sealed abstract class Http

    object Http {

      final case object Ok extends Http

      final case object Error extends Http

      final case object Fail extends Http

    }

    val list: List[Product with Serializable with Http] = Http.Ok :: Http.Error :: Http.Fail :: Nil

  }

  /*
 Types that are not instantiate, ever.
  */
  @Test
  def phantomTest(): Unit = {
    sealed trait ServiceState
    final class Started extends ServiceState
    final class Stopped extends ServiceState

    // 'State' <: ServiceState → Upper type bound constraint: (The <: symbol means "is a subtype of" (upper bound constraint).)
    // - 'State' must be a subtype (or exactly the same type) as 'ServiceState'.
    // - class Service[State <: ServiceState]  State can be ServiceState, Started, or Stopped.
    class Service[State <: ServiceState] private() {
      def start[T >: State <: Stopped](): Service[Started] = this.asInstanceOf[Service[Started]]

      def stop[T >: State <: Started](): Service[Stopped] = this.asInstanceOf[Service[Stopped]]
    }
    object Service {
      def create() = new Service[Stopped]
    }
    val initiallyStopped = Service.create()

    val started: Service[Started] = initiallyStopped.start()

    val stopped: Service[Stopped] = started.stop()

    //Error:(26, 13) inferred type arguments [Stopped] do not conform to method stop's type parameter bounds [T >: Stopped <: Started]
    //    stopped.stop()
    //But exixt
    // started.stop()
    assertNotNull(stopped.getClass)
  }

}
