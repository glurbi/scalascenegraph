package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example5 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 30.0f) % 360.0f
	}
	
	def example =
		world {
			cullFace(On)
			light(On)
		    light(Light1, On)
		    light(Light1, Position(-3.0f, 0.0f, 0.0f))
		    light(Light1, DiffuseLight, JColor.white)
			group {
				light(Off)
				translation(-2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				polygonMode(FrontAndBack, Line)
				cullFace(Off)
				torus(30, 1.0f, 0.5f)
			}
			group {
				material(Front, DiffuseLight, JColor.orange)
				translation(2.0f, -2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
			group {
				ambient(Intensity(0.3f, 0.3f, 0.3f, 1.0f))
				material(Front, AmbientAndDiffuseLight, JColor.pink)
				translation(-2.0f, -2.0f, -4.0f)
				shadeModel(Flat)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
			group {
				light(Light1, SpecularLight, JColor.white)
				material(Front, DiffuseLight, JColor.red)
				material(Front, SpecularLight, JColor.white)
				shininess(Front, 128)
				translation(2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
		}
	
}
