package com.tfedorov.tutorial.herding_cats

import cats.PartialOrder
import cats.implicits._
import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test

class EqTest {
  @Test
  def equalEqualEqual(): Unit = {

    //false 6 == 6
    //: error: type mismatch;
    //val actualResult: Boolean = 6 === "six"
    val actualResult: Boolean = 6 === 6

    assertTrue(actualResult)
  }

  @Test
  def order(): Unit = {

    val actualResult: Boolean = 1 > 2.0
    //error: type mismatch;
    //val actualResult: Boolean = 1 compare 2.0
    val actualResultTyped: Int = 1.0 compare 2.0

    assertFalse(actualResult)
    assertEquals(-1, actualResultTyped)
  }

  @Test
  def partialOrder(): Unit = {

    val actualResult: Option[Int] = 1 tryCompare 2

    assertEquals(Some(-1), actualResult)
  }

  @Test
  def partialOrderFunc(): Unit = {
    def lt[A: PartialOrder](a1: A, a2: A): Boolean = a1 < a2

    //def lt[A](a1: A, a2: A)(implicit partialOrder: PartialOrder[A]): Boolean = partialOrder.lt(a1, a2)

    //val actualResult: Option[Int] =  lt[Int](1, 2.0)
    val actualResult = lt[Int](1, 2)

    assertTrue(actualResult)
  }

  @Test
  def show(): Unit = {
    import cats.Show

    //val intShow = Show.show[Int](_.toString)
    val intShow = Show.fromToString[Int]
    val actualResult = intShow.show(3)

    assertEquals("3", actualResult)
    assertEquals("3", 3.show)
  }

  @Test
  def showClass(): Unit = {
    case class Person(name: String)
    case class Car(model: String)
    import cats.Show

    implicit val personShow: Show[Person] = Show.show[Person](_.name)
    val actualPersonResult: String = Person("Alice").show
    implicit val carShow: Show[Car] = Show.fromToString[Car]
    //implicit val carShow: Show[Car] = Show.show[Car](_.model)
    val actualCarResult: String = Car("CR-V").show

    assertEquals("Alice", actualPersonResult)
    assertEquals("Car(CR-V)", actualCarResult)
  }

  @Test
  def typeclasses102() = {
    import cats._
    sealed trait TrafficLight
    object TrafficLight {
      def red: TrafficLight = Red

      def yellow: TrafficLight = Yellow

      def green: TrafficLight = Green

      case object Red extends TrafficLight

      case object Yellow extends TrafficLight

      case object Green extends TrafficLight

    }

    implicit val trafficLightEq: Eq[TrafficLight] =
      new Eq[TrafficLight] {
        def eqv(a1: TrafficLight, a2: TrafficLight): Boolean = a1 == a2
      }

    //val actualResult = new cats.syntax.EqOps(TrafficLight.red).equals(TrafficLight.yellow)
    val actualResult = TrafficLight.red === TrafficLight.yellow

    assertFalse(actualResult)
  }
}
