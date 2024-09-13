package com.tfedorov.inteview.daily_coding_problem

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*Snapchat.

Given an array of time intervals (start, end) for classroom lectures (possibly overlapping),
find the minimum number of rooms required.

For example, given [(30, 75), (0, 50), (60, 150)], you should return 2.

 */
class SnapchatTest {

  private case class TI(start: Int, end: Int) {

    def in(timeMoment: Int): Boolean = {
      if (timeMoment >= start && timeMoment <= end)
        return true
      false
    }
  }

  private def findRooms(inputSlots: List[TI]): Int = {

    val timeMoments = inputSlots.flatMap(ti => Seq(ti.start, ti.end))
    timeMoments.foldLeft(0) { (maxNumberOfRoom, moment) =>
      val usedRoomInMoment = inputSlots.count(_.in(moment))
      Math.max(maxNumberOfRoom, usedRoomInMoment)
    }

  }

  @Test
  def defaultDataTest(): Unit = {

    val inputData = TI(30, 75) :: TI(0, 50) :: TI(60, 150) :: Nil

    val actualResult: Int = findRooms(inputData)

    val expectedResult = 2
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def changedDataTest(): Unit = {

    val inputData = TI(0, 50) :: TI(30, 75) :: TI(35, 60) :: Nil

    val actualResult: Int = findRooms(inputData)

    val expectedResult = 3
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def changed2DataTest(): Unit = {

    val inputData = TI(0, 50) :: TI(51, 75) :: TI(76, 100) :: Nil

    val actualResult: Int = findRooms(inputData)

    val expectedResult = 1
    assertEquals(expectedResult, actualResult)
  }
}
