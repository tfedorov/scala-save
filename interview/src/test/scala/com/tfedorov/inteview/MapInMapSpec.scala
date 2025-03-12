package com.tfedorov.inteview

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import java.text.SimpleDateFormat
import java.util.Locale

class MapInMapSpec extends FlatSpec {

  type Key = Int
  type TimeStamp = String
  type Val = Int
  private var insideContainer = Map.empty[Key, Map[TimeStamp, Val]]

  def put(key: Key, value: Val): Unit = {

    val timestamp = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(new java.util.Date())
    val actualValueOpt = insideContainer.get(key)

    val newKeyVal = actualValueOpt.map { oldValueMap =>
      Map(key -> (oldValueMap + (timestamp -> value)))
    }.getOrElse(Map(key -> Map(timestamp -> value)))
    insideContainer ++= newKeyVal
  }

  def get(key: Key): Option[Val] = {
    if (!insideContainer.contains(key))
      return None

    val valueMap: Option[Map[TimeStamp, Val]] = insideContainer.get(key)

    val lastTimestamp = valueMap.get.keys.max
    Some(valueMap.get(lastTimestamp))

  }

  def get(key: Key, timeStamp: TimeStamp): Option[Val] = {
    if (!insideContainer.contains(key))
      return None

    val valueMap = insideContainer.get(key)
    valueMap.get.get(timeStamp)
  }

  "put and get" should "changes" in {
    put(1, 1)
    Thread.sleep(1000)
    put(1, 2)
    val actualResult = get(1)
    actualResult should be(Some(2))
    insideContainer.foreach(println)
    //println(get(1, 1607708093418l))
  }

  object Inside {
    private[MapInMapSpec] var container = Map[Int, String]()

    def search(key: Int, valQuerry: String): Option[String] = {
      container.get(key).flatMap { valueStr =>
        val searchedKey = "'" + valQuerry + "' :"
        val index: Int = valueStr.indexOf(searchedKey)
        if (index < 0)
          None
        else {
          val afterText = valueStr.substring(index + searchedKey.length)
          var valueZone = afterText.split("[},]")(0).trim
          if (valueZone.startsWith("'"))
            valueZone = valueZone
              .replaceFirst("'", "")
              .reverse
              .replaceFirst("'", "")
              .reverse
          Some(valueZone)
        }
      }
    }
  }

  "dsl example" should "changes" in {
    Inside.container = Inside.container + (1 -> "{'c' : 1 ,'a' : 'b'}")
    val actualResult = Inside.search(1, "a")
    actualResult should be(Some("b"))

    val actualResult2 = Inside.search(1, "c")
    actualResult2 should be(Some("1"))
  }

  "dsl eample" should "changes" in {

  }

}
