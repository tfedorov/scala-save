package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Test

import scala.collection.mutable

class Maria {

  @Test
  def partialTest(): Unit = {
    val internalMap: mutable.Map[String, Integer] = collection.mutable.Map[String, Integer]()
    internalMap.put("AA.1", 5)
    internalMap.put("B.1", 5)
    internalMap.put("AA.3", 6)
    internalMap.put("B.tyt", 5)

    internalMap.put("A.1", 3)
    internalMap.put("A.2", 2)
    internalMap.put("B.2", 4)
    internalMap.put("C.1", 7)
    internalMap.put("B.1", 3)

    internalMap.put("C.1", 6)
    internalMap.put("D.1", 7)


    def partial(key: String): Integer = {
      val values: Seq[Integer] = internalMap.filter(_._1.startsWith(key + ".")).values.toSeq
      values.foreach(println)
      println("-----")
      values.foldLeft(0)(_ + _)
    }

    println(partial("AA"))
    println("*****")
    println(partial("B"))
  }

  /*
  Rotate “n x n” matrix 90 degree counterclockwise:
   Input:
     a b c
     e f g
     h i j
   Output:
     c g j
     b f i
     a e h
  Estimate code complexity.
   */

  @Test
  def rotateTest(): Unit = {
    val input = Seq("a", "b", "c") :: Seq("e", "f", "g") :: Seq("h", "i", "j") :: Nil
    input.foreach(println)
    println("output")
    input.flatMap(_.zipWithIndex)
      .groupBy(_._2).map(_._2.map(_._1))
      .foreach(println)
  }
}
