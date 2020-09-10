package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SelfTypeCakeTest {

  @Test
  def typeAnnotationTest(): Unit = {
    trait User {
      def username: String
    }

    trait Messenger {
      this: User => // reassign this
      def chat(message: String): String = s"$username: $message"
    }

    trait Bot extends User {
      override def username: String = s"agent Smith"
    }

    val agentMessage = (new Messenger with Bot).chat("Hello, Mr. Anderson")

    class SecureMessenger(val username_ : String) extends Messenger with User {
      def username = s"real $username_"
    }
    class Human extends User {
      override def username: String = "Neo"
    }

    val secureMessenger = new SecureMessenger("Neo")
    val neoMessage = secureMessenger.chat("My name.........Is Neo!")

    assertEquals("agent Smith: Hello, Mr. Anderson", agentMessage)
    assertEquals("real Neo: My name.........Is Neo!", neoMessage)
  }

  @Test
  def cakePatternTest(): Unit = {

    trait ComponentBottom {
      def comp1Value: String
    }

    trait ComponentMiddle extends ComponentBottom {
      this: ComponentBottom =>
      def comp2Value: String
    }

    trait ComponentTop extends ComponentMiddle {
      this: ComponentBottom =>
      def comp3Value: String
    }
    trait CakeBottom extends ComponentBottom {
      override def comp1Value: String = "Cake bottom  "
    }

    trait ChocolateMiddle extends ComponentMiddle {
      override def comp2Value: String = " Chocolate  "
    }

    trait BerryMiddle extends ComponentMiddle {
      override def comp2Value: String = " Berry      "
    }

    trait CherryTop extends ComponentTop {
      override def comp3Value: String = " Cherry top"
    }

    trait CreamTop extends ComponentTop {
      override def comp3Value: String = " Cream top"
    }

    trait Baking extends ComponentTop {
      this: ComponentTop =>

      def bake(): String = s"prepare [$comp1Value|$comp2Value|$comp3Value]"
    }

    /*
        // Components are nailed
        class Baking extends ComponentTop {
          override def comp3Value: String = ???

          override def comp2Value: String = ???

          override def comp1Value: String = ???
        }
    */

    val chocoCakeBacking = new Baking with CreamTop with ChocolateMiddle with CakeBottom
    // self changed with BerryMiddle
    // val chocoCakeBacking = new Baking with CreamTop with ChocolateMiddle with BerryMiddle with CakeBottom
    val beriesCakeBacking = new Baking with CreamTop with BerryMiddle with CakeBottom
    val mixedBacking = new Baking with CherryTop with ChocolateMiddle with CakeBottom
    /*
        trait MixCake extends CherryTop with ChocolateMiddle with CakeBottom
        // Components are also nailed
        class MixBaking extends MixCake {
          def bake(): String = s"prepare [$comp1Value|$comp2Value|$comp3Value]"
        }
        val mixedBacking = new MixBaking
    */
    assertEquals("prepare [Cake bottom  | Chocolate  | Cream top]", chocoCakeBacking.bake())
    assertEquals("prepare [Cake bottom  | Berry      | Cream top]", beriesCakeBacking.bake())
    assertEquals("prepare [Cake bottom  | Chocolate  | Cherry top]", mixedBacking.bake())
  }
}
