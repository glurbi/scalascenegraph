package scalascenegraph.examples

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
	
	def example4 =
		world {
		    polygonMode(Front, Line) {
        		translation(-2.0f, 2.0f, -4.0f) {
	    			rotation(0.0f, -1.0f, -0.5f, 1.0f) {
	    				preRenderHook(angleHook)
	    				sphere(30, Color.blue)
	    			}
        		}
        		translation(-2.0f, -2.0f, -4.0f) {
	    			rotation(1.0f, -1.0f, -0.5f, 1.0f) {
	    				preRenderHook(angleHook)
	    				cube(Color.red)
	    			}
        		}
		    }
		    light(On) {
		    	translation(2.0f, -2.0f, -4.0f) {
		    		rotation(1.0f, -1.0f, -0.5f, 1.0f) {
		    			preRenderHook(angleHook)
		    			sphere(30, Color.blue)
		    		}
		    	}
		    	translation(2.0f, 2.0f, -4.0f) {
		    		rotation(1.0f, -1.0f, -0.5f, 1.0f) {
		    			preRenderHook(angleHook)
		    			cube(Color.red)
		    		}
		    	}
		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example4, true).show
    }

}
