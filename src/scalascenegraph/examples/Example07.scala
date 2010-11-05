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

class Example07 extends Example with WorldBuilder {
	
	val marbleTexture = new Texture(getClass.getResourceAsStream("/scalascenegraph/examples/marble.png"))
	val melonTexture = new Texture(getClass.getResourceAsStream("/scalascenegraph/examples/melon.png"))

	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 10.0f) % 360.0f
	}
	
	def example =
		world {
    		depthTest(On)
			attach(marbleTexture)
			attach(melonTexture)
			group {
				translation(1.5f, 1.5f, -3.0f)
				rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
				cube(marbleTexture, normals = false)
			}
			group {
				translation(-1.5f, 1.5f, -3.0f)
				rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
				sphere(30, 1.0f, melonTexture, normals = false)
			}
			group {
				light(On)
				light(GL_LIGHT1, On)
				light(GL_LIGHT1, new Position3D(0.0f, 5.0f, -3.0f))
				light(GL_LIGHT1, GL_DIFFUSE, JColor.white)
				ambient(Intensity(0.8f, 0.8f, 0.8f, 1.0f))
				group {
					translation(1.5f, -1.5f, -3.0f)
					rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
					cube(marbleTexture, normals = true)
				}
				group {
					translation(-1.5f, -1.5f, -3.0f)
					rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
					sphere(30, 1.0f, melonTexture, normals = true)
				}
			}
		}
	
}
