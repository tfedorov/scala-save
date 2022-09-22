package com.tfedorov.formats

import com.tfedorov.utils.FileUtils
import org.apache.avro.Schema.Parser
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.hadoop.fs.Path
import org.apache.parquet.avro.AvroParquetWriter
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class ParquetTest extends FlatSpec {

  private case class DataR(left: String, right: String)

  private def writeAvro(schemaVal: String, data: Seq[DataR], fileOut: String): Unit = {
    val parser = new Parser()
    val schema = parser.parse(schemaVal)
    val writer = AvroParquetWriter.builder[GenericRecord](new Path(fileOut))
      .withSchema(schema).build()
    data.foreach { dr =>
      val record = new GenericData.Record(schema)
      record.put("left", dr.left)
      record.put("right", dr.left)
      writer.write(record)
    }

    writer.close()
  }

  "writeAvro" should "create a specific file" in {
    val file2Save = FileUtils.randomTempFile()
    println("parquet-tools cat --json " + file2Save)
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

    writeAvro(schemaVal, DataR("left", "right") :: DataR("left2", "right2") :: Nil, file2Save)

    FileUtils.exist(file2Save) should be(true)
    FileUtils.readBytes(file2Save).get.length should be(621)
  }

}
