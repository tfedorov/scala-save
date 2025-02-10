package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Test

class SoftwareTestingHelp {

  @Test
  def syntaxTest(): Unit = {

    val input = "abcsagfzsacvaasfbg"
    val splited = input.map(el => el)

    val doubled = splited.groupBy(leter => leter)
      .filter(_._2.length > 1).keys.toSeq

    doubled.foreach(println)

    println("First letter = ")
    println(input.filter(letter => !doubled.contains(letter)).head)

    val result = input.collect {
      case char if input.count(_.equals(char)) == 1 => char
    }

    println("First letter = " + result)

    input.distinct.sortWith(_ > _).foreach(println)

  }
}
