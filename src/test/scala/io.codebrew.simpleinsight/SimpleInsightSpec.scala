package io.codebrew.simpleinsight

import org.specs2._

class SimpleInsightSpec extends Specification { def is = s2"""
  Simple Insight
    must instrument top level identifiers $toplevelIdent
  """

  def toplevelIdent = {
    val code = 
      """|val a = List((1,1))
         |a
         |val b = "value"
         |b""".stripMargin

    def show(a: Any) = a.toString
    val instrument = new Instrument
    val expected : List[(Instrument.Line, Instrument.Repr)] = List(
      (2, show(List((1,1))) ),
      (4, show("value") )
    )
    instrument(code) ==== expected
  }
}