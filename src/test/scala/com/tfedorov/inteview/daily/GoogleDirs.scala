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

  private case class PathMeta(level: Int, value: String)

  private def buildAllPathes(agg: Seq[String], descr: Seq[PathMeta]): Seq[String] = {
    if (descr.length == 1)
      return agg :+ descr.head.value

    val candidate = descr.last
    var result = candidate.value
    descr.init.foldRight(candidate.level) { (element, i) =>
      if (element.level < i) {
        result = element.value + "/" + result
        element.level
      } else
        i
    }
    val newAgg = agg :+ result

    buildAllPathes(newAgg, descr.init)
  }

  private def largestPath(workingText: String): String = {
    val allLevels: Seq[PathMeta] = buildLevelsPathes(workingText)
    val allPathes: Seq[String] = buildAllPathes(Nil, allLevels)

    allPathes.sortWith(_.length > _.length).head
  }

  private def buildLevelsPathes(workingText: String): Seq[PathMeta] = {
    var result: Seq[PathMeta] = Nil
    val rootEndI = workingText.indexOf("""\n""")
    val root = workingText.take(rootEndI)
    result = result :+ PathMeta(1, root)
    var leaveText = workingText.drop(rootEndI + 2)
    while (leaveText.nonEmpty) {
      val nextElIndex = leaveText.indexOf("""\n""")
      val (nextElChunk, level) = if (nextElIndex < 0) {
        // +1 because last element doesn't have '\n' at the end
        (leaveText, slashNumber(leaveText) + 1)
      } else {
        val elementInMiddle = leaveText.take(nextElIndex + 2)
        (elementInMiddle, slashNumber(elementInMiddle))
      }
      val nextElValue = trimN(nextElChunk.substring((level - 1) * 2))
      result = result :+ PathMeta(level, nextElValue)
      leaveText = leaveText.drop(nextElChunk.length)
    }
    result
  }

  private def slashNumber(text: String): Int = text.count(_.equals('\\'))

  private def trimN(input: String): String = {
    if (input.endsWith("""\n"""))
      return input.substring(0, input.length - """\n""".length)
    input
  }

  @Test
  def example2Test(): Unit = {
    val input = """dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext"""

    val actualResult = largestPath(input)

    val expectedResult = "dir/subdir2/subsubdir2/file2.ext"
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def example1Test(): Unit = {
    val input = """dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"""

    val actualResult = largestPath(input)

    val expectedResult = "dir/subdir2/file.ext"
    assertEquals(expectedResult, actualResult)
  }

  @Test
  def exampleCustomTest(): Unit = {
    val input = """dir\n\tsubdir1\n\t\tsubsubdir1\n\t\t\tfile2.ext\n\tsubdir2\n\t\tfile1.ext\n\t\tsubsubdir2\n\t"""

    val actualResult = largestPath(input)

    val expectedResult = "dir/subdir1/subsubdir1/file2.ext"
    assertEquals(expectedResult, actualResult)
  }
}
