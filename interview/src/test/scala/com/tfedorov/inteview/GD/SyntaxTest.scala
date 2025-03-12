package com.tfedorov.inteview.GD

import org.junit.jupiter.api.Test

class SyntaxTest {

  case class Node[L, R](left: L, op: String, right: R) {
    def add(element: Float, newElementOp: String): Node[_, _] = {
      right match {
        case rightNode: Node[_, _] =>
          val newOp = rightNode.add(element: Float, newElementOp: String)
          copy(right = newOp)

        case rightFloat if higherOperation(op, newElementOp) =>
          copy(right = Node(rightFloat, newElementOp, element))

        case _ =>
          copy(left = this, newElementOp, element)
      }
    }

    def calculate(): Float = {
      val leftRes: Float = left match {
        case value: Node[_, _] => value.calculate()
        case value: Float => value
      }
      val rightRes: Float = right match {
        case value: Node[_, _] => value.calculate()
        case value: Float => value
      }

      op match {
        case "+" => leftRes + rightRes
        case "*" => leftRes * rightRes
        case "/" => 1.0f * leftRes / rightRes
        case "-" => leftRes - rightRes
      }
    }
  }

  @Test
  def syntaxTest(): Unit = {
    println(evaluate2("1+2*4/3"))
    println(evaluate2("2+2*2+3"))
    println(evaluate2("2*2-2+3*2"))
  }


  def higherOperation(basicOperation: String, newOperation: String): Boolean = {
    (basicOperation, newOperation) match {
      case ("+", "*") => true
      case ("*", "+") => false
      case ("+", "/") => true
      case ("*", "/") => false
      case ("*", "-") => false
      case ("-", "-") => false
      case ("-", "+") => false
    }
  }


  def evaluate2(input: String): Float = {
    var splittedVals = input.split("\\D")
    var splittedOps: Array[String] = input.split("\\d").filter(_.nonEmpty)
    var nodeRoot: Node[_, _] = Node(splittedVals(0).toFloat, splittedOps.head, splittedVals(1).toFloat)
    splittedVals = splittedVals.tail.tail
    splittedOps = splittedOps.tail
    splittedVals.foreach { el =>
      nodeRoot = nodeRoot.add(el.toFloat, splittedOps.head)
      splittedOps = splittedOps.tail
    }
    println(nodeRoot)
    nodeRoot.calculate()
  }
}
