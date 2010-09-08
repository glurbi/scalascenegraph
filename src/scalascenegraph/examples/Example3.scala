package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example3 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 20.0f) % 360.0f
	}
	
	def example =
		world {
			group {
				translation(-2.0f, 0.0f, -4.0f)
				polygonMode(Front, Line)
				rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
				cube
			}
			group {
				translation(2.0f, 0.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
				cube
			}
			group {
				translation(0.0f, 2.0f, -4.0f)
				cullFace(false)
				polygonMode(FrontAndBack, Line)
				rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
				cube
			}
			group {
				translation(0.0f, -2.0f, -4.0f)
				polygonMode(Front, Line)
				frontFace(ClockWise)
				rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
				cube
			}
	    }
	
}
