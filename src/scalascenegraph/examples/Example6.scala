package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example6 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 30.0f) % 360.0f
	}
	
	def example =
		world {
			fog(JColor.blue, Exp(0.1f))
			quad(
				Vertice(-10.0f, -10.0f, -9.9f),
				Vertice(10.0f, -10.0f, -9.9f),
				Vertice(10.0f, 10.0f, -9.9f),
				Vertice(-10.0f, 10.0f, -9.9f))
			group {
				translation(0.0f, -1.0f, -5.0f)
				rotation(-90.0f, 1.0f, 0.0f, 0.0f)
				checkerBoard(20, 20, JColor.white, JColor.black)
			}
			group {
				light(On)
				light(Light0, On)
				light(Light0, Position(0.0f, 0.0f, 0.0f))
				light(Light0, DiffuseLight, JColor.white)
				material(Front, DiffuseLight, JColor.orange)
				group {
					translation(-1.5f, 0.5f, -3.5f)
					rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
					torus(30, 1.0f, 0.5f)
				}
				group {
					translation(1.0f, 0.5f, -6.0f)
					rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
					torus(30, 1.0f, 0.5f)
				}
				group {
					translation(5.0f, 0.5f, -8.5f)
					rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
					torus(30, 1.0f, 0.5f)
				}
			}
		}
	
}
