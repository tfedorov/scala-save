package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Test

class PhantomTypeTest {

  //"Types that are not instantiate, ever".
  @Test
  def phantomTest(): Unit = {
    sealed trait ServiceState
    final class Started extends ServiceState
    final class Stopped extends ServiceState

    class Service[State <: ServiceState] private() {
      def start[T >: State <: Stopped](): Service[Started] = this.asInstanceOf[Service[Started]]

      def stop[T >: State <: Started](): Service[Stopped] = this.asInstanceOf[Service[Stopped]]
    }
    object Service {
      def create() = new Service[Stopped]
    }
    val initiallyStopped = Service.create()

    val started = initiallyStopped.start()

    val stopped = started.stop()

    //Error:(26, 13) inferred type arguments [Stopped] do not conform to method stop's type parameter bounds [T >: Stopped <: Started]
    //    stopped.stop()
    //stopped.stop()
  }
}
