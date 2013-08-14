package fpcommons

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

class StateSpecs extends WordSpec with ShouldMatchers {

  "State" should {
    "be created from unit()" in {
      val st :State[String, Int] = State.unit(3)

      val (a, s) = st.run("foo")

      a should be(3)
      s should be("foo")
    }

    "run correctly" in {
      val st :State[String, Int] = State(s => (s.length, s + "*")) // string grows with every call

      val (a, s) = st.run("--")

      a should be(2)
      s should be("--*")
    }

    "be be mapped over" in {
      val st :State[String, Int] = State(s => (s.length, s + "*"))

      val st2 = st map (2 *)

      val (a, s) = st2.run("--")

      a should be(4)
      s should be("--*")
    }

    "be flatMapped over" in {

      val st :State[String, Int] = State(s => (s.length, s + "*"))

      val st2 = st flatMap (in => State(st => (in * 2, st + "+")))

      val (a, s) = st2.run("--")

      a should be(4)
      s should be("--*+")
    }
  }
  "State for comperhension" should {
    "work" in {

      val st = for {
        in1   <- State[String, Int](s => (s.length, s + "*"))
        in2   <- State[String, Int](s => (in1 * 2,  s + "+"))
        in3   <- State[String, String](s => ((in2+1).toString + "X",  s + "#"))
      } yield in3

      val (a, s) = st.run("--")

      a should be("5X")
      s should be("--*+#")

    }
  }

  "collection of states" should {
    "be sequenced" in {
      val list :List[State[String, Int]] = List(
        State(s => (s.length, s + "*")),
        State(s => (s.length, s + "+")),
        State(s => (s.length, s + "#"))
      )

      val state = State.sequence(list)

      val (a,s)  = state.run("--")

      a.toSeq should be (Seq(2,3,4))

      s should be ("--*+#")
    }
  }

  "empty collection of states" should {
    "be sequenced" in {
      val list :List[State[String, Int]] = Nil

      val state = State.sequence(list)

      val (a,s)  = state.run("--")

      a.toSeq should be ('empty)

      s should be ("--")
    }
  }

}
