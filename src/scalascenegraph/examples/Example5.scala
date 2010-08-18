package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example5 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 30.0f) % 360.0f
	}
	
	def example =
		world {
			group {
				translation(-2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				polygonMode(FrontAndBack, Line)
				cullFace(false)
				torus(30, 1.0f, 0.5f)
			}
			group {
				light(On)
				light(DiffuseLight, Position(-3.0f, 0.0f, 0.0f), JColor.white)
				material(Front, DiffuseLight, JColor.orange)
				translation(2.0f, -2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
			group {
				light(On)
				ambient(Intensity(0.3f, 0.3f, 0.3f, 1.0f))
				light(DiffuseLight, Position(-3.0f, 0.0f, 0.0f), JColor.white)
				material(Front, AmbientAndDiffuseLight, JColor.pink)
				translation(-2.0f, -2.0f, -4.0f)
				shadeModel(Flat)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
			group {
				light(On)
				light(SpecularLight, Position(-3.0f, 0.0f, 0.0f), JColor.white)
				light(DiffuseLight, Position(-3.0f, 0.0f, 0.0f), JColor.white)
				material(Front, DiffuseLight, JColor.red)
				material(Front, SpecularLight, JColor.white)
				shininess(Front, 128)
				translation(2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
		}
	
}
