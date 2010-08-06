package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._


object Example4 extends WorldBuilder {
	
	val angleHook = (n: Node, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		n.asInstanceOf[Rotation].angle = (elapsed / 20.0f) % 360.0f
	}
	
	val translationHook = (n: Node, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		n.asInstanceOf[Translation].x = 1.0f + cos(elapsed / 1000.0f).asInstanceOf[Float]
		n.asInstanceOf[Translation].y = -1.0f + sin(elapsed / 1000.0f).asInstanceOf[Float]
	}
	
	def example4 =
		world {
		    polygonMode(Front, Line) {
        		translation(-2.0f, 2.0f, -4.0f) {
	    			rotation(0.0f, -1.0f, -0.5f, 1.0f) {
	    				preRenderHook(angleHook)
	    				sphere(30)
	    			}
        		}
        		translation(-2.0f, -2.0f, -4.0f) {
	    			rotation(1.0f, -1.0f, -0.5f, 1.0f) {
	    				preRenderHook(angleHook)
	    				cube
	    			}
        		}
		    }
		    light(On) {
		    	ambient(Intensity(0.4f, 0.4f, 0.4f, 1.0f)) {
	    			light(DiffuseLight, Position(0.0f, 0.0f, -4.0f), JColor.white) {
	    				translation(2.0f, -2.0f, -4.0f) {
	    					preRenderHook(translationHook)
	    					rotation(1.0f, -1.0f, -0.5f, 1.0f) {
	    						preRenderHook(angleHook)
	    						material(Front, AmbientLight, JColor.green) {
	    						material(Front, DiffuseLight, JColor.green) {
	    							sphere(30)
	    						}}
	    					}
	    				}
	    				translation(2.0f, 2.0f, -4.0f) {
	    					rotation(1.0f, -1.0f, -0.5f, 1.0f) {
	    						preRenderHook(angleHook)
	    						material(Front, AmbientLight, JColor.red) {
	    						material(Front, DiffuseLight, JColor.red) {
	    							cube
	    						}}
	    					}
	    				}
	    			}
		    	}
		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example4, true).show
    }

}
