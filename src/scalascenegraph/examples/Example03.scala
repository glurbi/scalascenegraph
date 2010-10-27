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

	val mycone = cone(20, 10, 4.0f, 6.0f, normals = true)
	val mysphere = sphere(20, 4.0f, normals = true)
	
	def example =
		world {
			depthTest(On)
			cullFace(On)
		    light(On)
		    light(GL_LIGHT0, On)
		    light(GL_LIGHT0, GL_POSITION, Array(0.0f, 1.0f, 1.0f, 0.0f))
		    light(GL_LIGHT0, GL_DIFFUSE, JColor.white)
			colorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE)
    		translation(0.0f, 0.0f, -15.0f)
		    ambient(Intensity(0.1f, 0.1f, 0.1f, 1.0f))
			group {
				color(JColor.cyan)
    			translation(-6.0f, 0.0f, 1.0f)
    			attach(mysphere)
				attach(normals(mysphere))
			}
			group {
				color(JColor.red)
    			translation(6.0f, 0.0f, 0.0f)
				attach(mycone)
				attach(normals(mycone))
			}
	    }

}
