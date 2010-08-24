package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example7 extends Example with WorldBuilder {
	
	val marble = getClass.getResourceAsStream("/scalascenegraph/examples/marble.png")
	
	def example =
		world {
			translation(0.0f, 0.0f, -5.0f)
			texture("marble", marble)
			cube("marble")
		}
	
}
