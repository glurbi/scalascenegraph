package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example4 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		r.angle = (elapsed / 10.0f) % 360.0f
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
    		material(Front, AmbientLight, JColor.green)
    		material(Front, DiffuseLight, JColor.green)
    		sphere(30)
    	}

    def redCube =
    	group {
    		translation(0.0f, 0.0f, -4.0f, translationHook((0.0).asInstanceOf[Float]))
    		rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
    		material(Front, AmbientAndDiffuseLight, JColor.red)
    		cube
    	}
    
    def wireCube = 
    	group {
	    	polygonMode(FrontAndBack, Line)
	    	translation(0.0f, 0.0f, -4.0f, translationHook((Pi).asInstanceOf[Float]))
	    	rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
	    	material(FrontAndBack, AmbientLight, JColor.blue)
	    	material(FrontAndBack, DiffuseLight, JColor.blue)
	    	cube
    	}

    def wireSphere =
    	group {
    		polygonMode(FrontAndBack, Line)
    		translation(0.0f, 0.0f, -4.0f, translationHook((Pi / 2.0).asInstanceOf[Float]))
    		rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
    		material(FrontAndBack, AmbientLight, JColor.blue)
    		material(FrontAndBack, DiffuseLight, JColor.blue)
    		sphere(15)
    	}
    
	def example =
		world {
		    light(On)
		    ambient(Intensity(0.4f, 0.4f, 0.4f, 1.0f))
		    light(DiffuseLight, Position(2.0f, 2.0f, 0.0f), JColor.white)
		    group {
		    	cullFace(false)
		    	lineWidth(4.0f)
		    	wireSphere
		    	wireCube
		    }
		    greenSphere
		    redCube
		}
	
}
