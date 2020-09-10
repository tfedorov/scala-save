package com.tfedorov.tutorial.`type`

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SelfTypeAnnotationTest {

  @Test
  def typeParameterVersion(): Unit = {
    trait User {
      def username: String
    }

    trait Messenger {
      this: User => // reassign this
      def chat(message: String): String = s"$username: $message"
    }

    trait Agent extends User {
      override def username: String = s"agent Smith"
    }

    class UnSecureMessenger extends Messenger with Agent
    val unSecureMessenger = new UnSecureMessenger
    val agentMessage = unSecureMessenger.chat("Hello, Mr. Anderson")

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

    trait Component {
      def value: String
    }

    trait Cake {
      def bake: String
    }
    trait LayeredCake extends Cake {
      this: Component =>

      override def bake: String = "[layer - " + value + " | layer - waffle | layer - " + value + "]"
    }

    trait MixedCake extends Cake {
      this: Component =>

      override def bake: String = "[mixed " + value + " all in one]"
    }

    trait Cheese extends Component {
      override def value: String = "cheese"
    }

    trait Berries extends Component {
      override def value: String = "berry"
    }

    val cake1 = new LayeredCake() with Cheese
    val cake2 = new MixedCake() with Cheese
    val cake3 = new LayeredCake() with Berries
    val cake4 = new MixedCake() with Berries

    assertEquals("[layer - cheese | layer - waffle | layer - cheese]", cake1.bake)
    assertEquals("[mixed cheese all in one]", cake2.bake)
    assertEquals("[layer - berry | layer - waffle | layer - berry]", cake3.bake)
    assertEquals("[mixed berry all in one]", cake4.bake)
  }

  @Test
  def sefTypeChainTest(): Unit = {

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
