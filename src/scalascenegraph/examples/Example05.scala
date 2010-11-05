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
		    light(GL_LIGHT1, new Position3D(-3.0f, 0.0f, 0.0f))
		    light(GL_LIGHT1, GL_DIFFUSE, JColor.white)
			group {
				light(Off)
				translation(-2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				polygonMode(GL_FRONT_AND_BACK, GL_LINE)
				cullFace(Off)
				attach(torus(30, 1.0f, 0.5f, normals = false))
			}
			group {
				material(GL_FRONT, GL_DIFFUSE, JColor.orange)
				translation(2.0f, -2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				attach(torus(30, 1.0f, 0.5f, normals = true))
			}
			group {
				ambient(Intensity(0.3f, 0.3f, 0.3f, 1.0f))
				material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.pink)
				translation(-2.0f, -2.0f, -4.0f)
				shadeModel(GL_FLAT)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				attach(torus(30, 1.0f, 0.5f, normals = true))
			}
			group {
				light(GL_LIGHT1, GL_SPECULAR, JColor.white)
				material(GL_FRONT, GL_DIFFUSE, JColor.red)
				material(GL_FRONT, GL_SPECULAR, JColor.white)
				shininess(GL_FRONT, 128)
				translation(2.0f, 2.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.0f, 0.0f, angleHook)
				attach(torus(30, 1.0f, 0.5f, normals = true))
			}
		}
	
}
