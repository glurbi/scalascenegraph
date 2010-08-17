package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example6 extends Example with WorldBuilder {
	
	def example =
		world {
			group {
				translation(0.0f, 0.0f, -5.0f)
				checkerBoard(8, 8, JColor.white, JColor.black)
			}
		}
	
}
