package com.tfedorov.utils

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import org.junit.jupiter.api.Assertions.{assertArrayEquals, assertEquals}
import org.junit.jupiter.api.Test

class Serialization {

  @Test
  def serializeTest(): Unit = {
    val nflx = Stock("NFLX", BigDecimal(85.00))
    val file2Write = FileUtils.randomTempFile()
    val oos = new ObjectOutputStream(new FileOutputStream(file2Write))
    try {
      oos.writeObject(nflx)

      val actualResult = FileUtils.readBytes(file2Write).get

      val expectedResult = FileUtils.readBytes("src/test/resources/stocks_example.txt").get
      assertArrayEquals(expectedResult, actualResult)
    } finally {
      oos.close()
    }
  }


  @Test
  def deSerializeTest(): Unit = {

    val ois = new ObjectInputStream(new FileInputStream("src/test/resources/stocks_example.txt"))
    try {
      val actualResult = ois.readObject.asInstanceOf[Stock]
      val expectedResult = Stock("NFLX", BigDecimal(85.00))
      assertEquals(expectedResult, actualResult)
    }
    finally {
      ois.close()
    }
  }
}

@SerialVersionUID(123L)
case class Stock(var symbol: String, var price: BigDecimal) extends java.io.Serializable {
  override def toString = f"$symbol%s is ${price.toDouble}%.2f"
}
