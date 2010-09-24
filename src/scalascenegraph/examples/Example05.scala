package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example05 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 30.0f) % 360.0f
	}
	
	def example =
		world {
    		depthTest(On)
			cullFace(On)
			light(On)
		    light(GL_LIGHT1, On)
		    light(GL_LIGHT1, Position(-3.0f, 0.0f, 0.0f))
		    light(GL_LIGHT1, DiffuseLight, JColor.white)
			group {
				light(Off)
				translation(-2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				polygonMode(GL_FRONT_AND_BACK, GL_LINE)
				cullFace(Off)
				torus(30, 1.0f, 0.5f)
			}
			group {
				material(GL_FRONT, DiffuseLight, JColor.orange)
				translation(2.0f, -2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
			group {
				ambient(Intensity(0.3f, 0.3f, 0.3f, 1.0f))
				material(GL_FRONT, AmbientAndDiffuseLight, JColor.pink)
				translation(-2.0f, -2.0f, -4.0f)
				shadeModel(Flat)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
			group {
				light(GL_LIGHT1, SpecularLight, JColor.white)
				material(GL_FRONT, DiffuseLight, JColor.red)
				material(GL_FRONT, SpecularLight, JColor.white)
				shininess(GL_FRONT, 128)
				translation(2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				torus(30, 1.0f, 0.5f)
			}
		}
	
}
