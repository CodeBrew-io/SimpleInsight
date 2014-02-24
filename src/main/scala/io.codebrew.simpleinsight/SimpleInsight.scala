package io.codebrew.simpleinsight

object Instrument {
	type Line = Int
	type Repr = String
}

class Instrument {
	import Instrument._
	import scala.reflect.runtime.{currentMirror => cm}
	import scala.reflect.runtime.universe._
	import scala.tools.reflect.ToolBox
	val tb = cm.mkToolBox()

	def apply(code: String):List[(Line, Repr)] = {
		val show = "implicit def html(a: Any) = a.toString"
		val nl = sys.props("line.separator")

		val tree = tb.parse(show + nl + code)
		val identifiers = tree.children.collect{ case i: Ident => i }
		val instrumentation = identifiers.map{ i => 
			Apply(
				Select(Ident("scala"), TermName("Tuple2")),
				List(
					Literal(Constant(i.pos.line - 1)), 
					Apply(Ident(TermName("html")), List(i))
				)
			)
		}
		val result = Apply(Ident(TermName("List")), instrumentation)
		tb.eval(Block(tree.children, result)).asInstanceOf[List[(Line, Repr)]]
	}
}