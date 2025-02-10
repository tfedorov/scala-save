package com.tfedorov.formats

import com.tfedorov.utils.FileUtils
import com.tfedorov.utils.FileUtils._
import org.apache.avro.Schema.Parser
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.generic.{GenericData, GenericDatumReader, GenericDatumWriter, GenericRecord}
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import java.io.File

//https://zymeworks.github.io/avro-viewer/
class AvroTest extends FlatSpec {

  private case class DataR(left: String, right: String)

  private def writeAvro(schemaVal: String, data: Seq[DataR], fileOut: String) = {
    val parser = new Parser()
    val schema = parser.parse(schemaVal)

    val datum = new GenericData.Record(schema)
    data.foreach { data =>
      datum.put("left", data.left)
      datum.put("right", data.right)
    }

    val file = new File(fileOut)
    val writer = new GenericDatumWriter[GenericRecord](schema)
    val dataFileWriter = new DataFileWriter[GenericRecord](writer)
    dataFileWriter.create(schema, file)
    dataFileWriter.append(datum)
    dataFileWriter.close()
  }

  "writeAvro" should "create a specific file" in {
    val file2Save = FileUtils.randomTempFile()
    val schemaVal: String =
      """
        |{
        |  "type": "record",
        |  "name": "Pair",
        |  "doc": "A pair of strings.",
        |  "fields": [
        |    {"name": "left", "type": "string"},
        |    {"name": "right", "type": "string"}
        |  ]
        |}
        |""".stripMargin

    writeAvro(schemaVal, DataR("left", "right") :: Nil, file2Save)
    val actualResult = FileUtils.readBytes(file2Save).get

    val expectedFile = FileUtils.readResourceBytes("parquete/DataExample.avro").get
    actualResult.take(150) should be(expectedFile.take(150))
  }

  private def readAvro(fileInput: String) = {
    val file = new File(fileInput)
    val reader = new GenericDatumReader[GenericRecord]();
    val dataFileReader = new DataFileReader[GenericRecord](file, reader);
    val result: GenericRecord = dataFileReader.next();
    System.out.println("data" + result.get("left").toString())
    System.out.println("data" + result.get("right").toString())

  }

  "readAvro" should "create a specific file" in {
    readAvro(resourceFullPath("parquete/DataExample.avro"))
  }
}
