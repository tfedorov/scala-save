package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
This problem was asked by Apple.

Implement a job scheduler which takes in a function f and an integer n, and calls f after n milliseconds.
 */
class AppleTest {

  def scheduler(f: () => Any, timeOut: Int): Unit = {
    Thread.sleep(timeOut)
    f()
  }

  @Test
  def schedulerUnitTest(): Unit = {
    println("Start")
    scheduler(() => println("Hello world"), 10000)
  }

  @Test
  def schedulerIntTest(): Unit = {
    println("Start")
    scheduler(() => {
      println("Hello world")
      return 5
    }, 10000)
  }
}
