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

class Example04 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 10.0f) % 360.0f
	}
	
	def translationHook(phase: Float) = (t: Translation, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		t.x = 2 * cos(phase + elapsed / 1000.0f).asInstanceOf[Float]
		t.y = 2 * sin(phase + elapsed / 1000.0f).asInstanceOf[Float]
	}
	
    def greenSphere =
    	group {
    		translation(0.0f, 0.0f, -4.0f, translationHook((-Pi / 2.0).asInstanceOf[Float]))
    		rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
    		material(GL_FRONT, GL_AMBIENT, JColor.green)
    		material(GL_FRONT, GL_DIFFUSE, JColor.green)
    		sphere(30, 1.0f)
    	}

    def redCube =
    	group {
    		translation(0.0f, 0.0f, -4.0f, translationHook((0.0).asInstanceOf[Float]))
    		rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
    		material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.red)
    		cube(normals = true)
    	}
    
    def wireCube = 
    	group {
	    	polygonMode(GL_FRONT_AND_BACK, GL_LINE)
	    	translation(0.0f, 0.0f, -4.0f, translationHook((Pi).asInstanceOf[Float]))
	    	rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
	    	material(GL_FRONT_AND_BACK, GL_AMBIENT, JColor.blue)
	    	material(GL_FRONT_AND_BACK, GL_DIFFUSE, JColor.blue)
	    	cube(normals = true)
    	}

    def wireSphere =
    	group {
    		polygonMode(GL_FRONT_AND_BACK, GL_LINE)
    		translation(0.0f, 0.0f, -4.0f, translationHook((Pi / 2.0).asInstanceOf[Float]))
    		rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
    		material(GL_FRONT_AND_BACK, GL_AMBIENT, JColor.blue)
    		material(GL_FRONT_AND_BACK, GL_DIFFUSE, JColor.blue)
    		sphere(15, 1.0f)
    	}
    
	def example =
		world {
			depthTest(On)
			cullFace(On)
		    light(On)
		    ambient(Intensity(0.4f, 0.4f, 0.4f, 1.0f))
		    light(GL_LIGHT0, On)
		    light(GL_LIGHT0, Position(2.0f, 2.0f, 0.0f))
		    light(GL_LIGHT0, GL_DIFFUSE, JColor.white)
		    group {
		    	cullFace(Off)
		    	lineWidth(4.0f)
		    	wireSphere
		    	wireCube
		    }
		    greenSphere
		    redCube
		}
	
}
