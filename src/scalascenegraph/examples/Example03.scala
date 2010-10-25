package scalascenegraph.examples

import java.awt.{Color => JColor }
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
	
	def example =
		world {
			depthTest(On)
			cullFace(On)
		    light(On)
		    light(GL_LIGHT0, On)
		    light(GL_LIGHT0, GL_POSITION, Array(0.0f, 1.0f, 1.0f, 0.0f))
		    light(GL_LIGHT0, GL_DIFFUSE, JColor.white)
			colorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE)
    		translation(0.0f, 0.0f, -8.0f)
		    ambient(Intensity(0.4f, 0.4f, 0.4f, 1.0f))
			group {
				color(JColor.cyan)
    			translation(-2.0f, 0.0f, 1.0f)
    			sphere(30, 1.0f, normals = true)
			}
			group {
				color(JColor.red)
    			translation(2.0f, 0.0f, 0.0f)
				cone(20, 10, 1.0f, 2.0f, normals = true);
			}
			group {
				color(JColor.lightGray)
				box(12.0f, 8.0f, 0.1f, 40, 30, 1, normals = true)
			}
	    }

}
