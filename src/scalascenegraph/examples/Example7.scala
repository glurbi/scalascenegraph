package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example7 extends Example with WorldBuilder {
	
	val marble = getClass.getResourceAsStream("/scalascenegraph/examples/marble.png")
	val melon = getClass.getResourceAsStream("/scalascenegraph/examples/melon.png")

	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 10.0f) % 360.0f
	}
	
	def example =
		world {
			group {
				translation(1.5f, 0.0f, -3.0f)
				rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
				texture("marble", marble)
				cube("marble")
			}
			group {
				translation(-1.5f, 0.0f, -3.0f)
				rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
				texture("melon", melon)
				sphere(30, 1.0f, "melon")
			}
		}
	
}
