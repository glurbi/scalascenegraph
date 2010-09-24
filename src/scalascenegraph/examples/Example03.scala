package scalascenegraph.examples

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

class Example03 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 20.0f) % 360.0f
	}
	
	def example =
		world {
			cullFace(On)
			group {
				translation(-2.0f, 0.0f, -4.0f)
				polygonMode(Front, GL_LINE)
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
				cullFace(On)
				polygonMode(FrontAndBack, GL_LINE)
				rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
				cube
			}
			group {
				translation(0.0f, -2.0f, -4.0f)
				polygonMode(Front, GL_LINE)
				frontFace(GL_CW)
				rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
				cube
			}
	    }
	
}
