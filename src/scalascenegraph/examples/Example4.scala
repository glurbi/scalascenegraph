package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example4 extends Example with WorldBuilder {
	
	val angleHook = (n: Node, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		n.asInstanceOf[Rotation].angle = (elapsed / 10.0f) % 360.0f
	}
	
	def translationHook(phase: Float): (Node, Context) => Unit =
		(n: Node, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		n.asInstanceOf[Translation].x = 2 * cos(phase + elapsed / 1000.0f).asInstanceOf[Float]
		n.asInstanceOf[Translation].y = 2 * sin(phase + elapsed / 1000.0f).asInstanceOf[Float]
	}
	
    def greenSphere =
    	translation(0.0f, 0.0f, -4.0f) {
			preRenderHook(translationHook((-Pi / 2.0).asInstanceOf[Float]))
			rotation(1.0f, -1.0f, -0.5f, 1.0f) {
				preRenderHook(angleHook)
				material(Front, AmbientLight, JColor.green) {
				material(Front, DiffuseLight, JColor.green) {
					sphere(30)
				}}
			}
		}

    def redCube =
		translation(0.0f, 0.0f, -4.0f) {
			preRenderHook(translationHook((0.0).asInstanceOf[Float]))
			rotation(1.0f, -1.0f, -0.5f, 1.0f) {
				preRenderHook(angleHook)
				material(Front, AmbientAndDiffuseLight, JColor.red) {
					cube
				}
			}
		}
    
    def wireCube = 
		polygonMode(FrontAndBack, Line) {
			translation(0.0f, 0.0f, -4.0f) {
				preRenderHook(translationHook((Pi).asInstanceOf[Float]))
				rotation(1.0f, -1.0f, -0.5f, 1.0f) {
					preRenderHook(angleHook)
					material(FrontAndBack, AmbientLight, JColor.blue) {
					material(FrontAndBack, DiffuseLight, JColor.blue) {
						cube
					}}
				}
			}
		}

    def wireSphere =
		polygonMode(FrontAndBack, Line) {
			translation(0.0f, 0.0f, -4.0f) {
				preRenderHook(translationHook((Pi / 2.0).asInstanceOf[Float]))
				rotation(0.0f, -1.0f, -0.5f, 1.0f) {
					preRenderHook(angleHook)
					material(FrontAndBack, AmbientLight, JColor.blue) {
					material(FrontAndBack, DiffuseLight, JColor.blue) {
						sphere(15)
					}}
				}
			}
		}
    
	def example =
		world {
		    light(On) {
		    	ambient(Intensity(0.4f, 0.4f, 0.4f, 1.0f)) {
	    			light(DiffuseLight, Position(2.0f, 2.0f, 0.0f), JColor.white) {
						cullFace(false) {
		    				lineWidth(4.0f) {
		    					wireSphere
		    					wireCube
		    				}
						}
	    				greenSphere
	    				redCube
	    			}
		    	}
		    }
	    }
	
}
