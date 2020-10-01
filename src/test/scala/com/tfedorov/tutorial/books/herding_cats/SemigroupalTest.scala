package com.tfedorov.tutorial.books.herding_cats

import cats._
import cats.implicits._
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SemigroupalTest {

  @Test
  def listFunctor(): Unit = {
    val inputList = List(1, 2, 3, 4)
    val inputFunctor = Functor[List].map(inputList)({
      (_: Int) * (_: Int)
    }.curried)

    val actualResult = Functor[List].map(inputFunctor) {
      _ (9)
    }

    assertEquals(9 :: 18 :: 27 :: 36 :: Nil, actualResult)
  }

  @Test
  def product(): Unit = {
    import cats.Semigroupal
    val inputListInt = List(6, 7)
    val inputListStr = List("♥", "♠", "♣", "♦")

    val actualResult = Semigroupal[List].product(inputListInt, inputListStr)

    val expected = (6, "♥") :: (6, "♠") :: (6, "♣") :: (6, "♦") :: (7, "♥") :: (7, "♠") :: (7, "♣") :: (7, "♦") :: Nil
    assertEquals(expected, actualResult)
  }

  @Test
  def apply(): Unit = {
    val inputList = List(1, 2, 3, 4)
    val function1 = (_: Int) * (_: Int)

    val functor: List[Int => Int] = Functor[List].map(inputList)(function1.curried)
    val actualResult = Functor[List].map(functor)((elFunc: Int => Int) => elFunc(3))

    val expected = List(3, 6, 9, 12)
    assertEquals(expected, actualResult)
  }

  @Test
  def mapN(): Unit = {

    val actualResult = (3.some, 5.some) mapN (_ - _)

    assertEquals(Some(-2), actualResult)
  }

  @Test
  def mapNNone(): Unit = {

    val actualResult = (3.some, none[Int]) mapN (_ - _)

    assertEquals(None, actualResult)
  }

  @Test
  def mapNList(): Unit = {

    val actualResult = (List("ha", "heh", "hmm"), List("?", "!", ".")) mapN (_ + _)

    assertEquals(List("ha?", "ha!", "ha.", "heh?", "heh!", "heh.", "hmm?", "hmm!", "hmm."), actualResult)
  }

  @Test
  def mapNOption(): Unit = {

    val actualResult = (3.some, List(4).some) mapN {
      _ :: _
    }

    assertEquals(Some(List(3, 4)), actualResult)
  }

  @Test
  def map2alias(): Unit = {


    assertEquals(Some(1), 1.some <* 2.some)
    assertEquals(Some(2), 2.some <* 1.some)
    assertEquals(None, 2.some <* none[Int])
  }

  @Test
  def map2(): Unit = {

    val actualResult = Apply[Option].map2(3.some, List(4).some)(_ :: _)

    val expected = Some(List(3, 4))
    assertEquals(expected, actualResult)
  }

  @Test
  def applyApOption(): Unit = {
    val func: Int => Int = (_: Int) + 3
    val option: Option[Int => Int] = func.some
    val inputAp: Option[Int] => Option[Int] = Apply[Option].ap[Int, Int](option)

    val actualResult = inputAp(9.some)

    assertEquals(Some(12), actualResult)
    assertEquals(Some(12), Apply[Option].ap(((_: Int) + 3).some)(9.some))
    assertEquals(Some(13), Apply[Option].ap({
      {
        (_: Int) + 3
      }.some
    })(10.some))
    assertEquals(None, Apply[Option].ap({
      {
        (_: String) + "hahah"
      }.some
    })(none[String]))
    assertEquals(None, Apply[Option].ap({
      none[String => String]
    })("woot".some))
  }

  @Test
  def applyApOptionChange(): Unit = {
    val func: String => Int = _.toInt + 1
    val option: Option[String => Int] = func.some
    val inputAp: Option[String] => Option[Int] = Apply[Option].ap[String, Int](option)

    val actualResult = inputAp("9".some)

    assertEquals(Some(10), actualResult)
    assertEquals(Some(10), Apply[Option].ap(((_: String).toInt + 1).some)("9".some))
  }


}
