package com.tfedorov.inteview.daily

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/*
This problem was asked by Google.

Suppose we represent our file system by a string in the following manner:

The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

dir
    subdir1
    subdir2
        file.ext
The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:

dir
    subdir1
        file1.ext
        subsubdir1
    subdir2
        subsubdir2
            file2.ext
The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty
 second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.

We are interested in finding the longest (number of characters) absolute path to a file within our file system.
 For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext",
 and its length is 32 (not including the double quotes).

Given a string representing the file system in the above format, return the length of the longest absolute path to a
file in the abstracted file system. If there is no file in the system, return 0.


 */
class GoogleDirs {

  class File(var path: Seq[String], val descr: String, val leave2Process: String)

  def findAllParents(file: File): File = {
    if (file.leave2Process.length <= 2)
      return file
    if (file.descr.equalsIgnoreCase("""\n"""))
      return file
    val parentDescrValue = file.descr.init.init
    val parentDescrI = findLastIndex(file.leave2Process, parentDescrValue).get
    val beforeDescr = file.leave2Process.take(parentDescrI)
    val afterDecr = file.leave2Process.drop(parentDescrI)
    val parentValue = if (parentDescrI > 0)
      afterDecr.drop(parentDescrValue.length).replace("""\n""", "").replace("""\t""", "")
    else
      file.leave2Process.take(file.leave2Process.indexOf(parentDescrValue))
    val result = new File(parentValue +: file.path, parentDescrValue, beforeDescr)
    findAllParents(result)
  }

  def findLastIndex(input: String, descr: String): Option[Int] = {
    if (input.length <= 2)
      return None
    val index = input.lastIndexOf(descr)
    if (index < 0)
      return Some(0)

    val descrCandidate = input.substring(index + descr.length, index + descr.length + 2)
    if (!"""\n""".equalsIgnoreCase(descrCandidate) && !"""\t""".equalsIgnoreCase(descrCandidate))
      return Some(index)
    val shorted = input.take(index)
    findLastIndex(shorted, descr)
  }

  def findForFile(path: Seq[String], input: String): String = {
    val lastI = input.lastIndexOf("""\t""")
    if (lastI < 0)
      return path.sortWith(_.length > _.length).head
    val filePath = input.drop(lastI + 2)
    val beforeFilePath = input.take(lastI + 2)
    val fileDescrIn = beforeFilePath.lastIndexOf("""\n""")
    val fileDescr = beforeFilePath.drop(fileDescrIn)
    val pathBeforeDescr = beforeFilePath.take(fileDescrIn)
    val file = findAllParents(new File(filePath :: Nil, fileDescr, pathBeforeDescr))

    val foundedFilePath = file.path.mkString("/")
    findForFile(path :+ foundedFilePath, pathBeforeDescr)
  }


  @Test
  def example2Test(): Unit = {
    val input = """dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"""

    val actualResult = findForFile(Nil, input)

    val expectedResult = "dir/subdir2/subsubdir2/file2.ext"
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def example1Test(): Unit = {
    val input = "dir\\n\\tsubdir1\\n\\tsubdir2\\n\\t\\tfile.ext"

    val actualResult = findForFile(Nil, input)

    val expectedResult = "dir/subdir2/file.ext"
    assertEquals(expectedResult, actualResult)
  }
}
