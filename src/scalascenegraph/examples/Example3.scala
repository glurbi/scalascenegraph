package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._

object Example3 extends WorldBuilder {
	
	val angleHook = (n: Node, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		n.asInstanceOf[Rotation].angle = (elapsed / 20.0f) % 360.0f
	}
	
	def example3 =
		world {
    		translation(-2.0f, -0.5f, -4.0f) {
    			polygonMode(Front, Line) {
    				rotation(0.0f, 1.0f, 0.5f, 1.0f) {
    					preRenderHook(angleHook)
    					cube
    				}
    			}
   		    }
    		translation(1.0f, -0.5f, -4.0f) {
    			rotation(0.0f, 1.0f, 0.5f, 1.0f) {
    				preRenderHook(angleHook)
    				cube
    			}
   		    }
    		translation(0.0f, 1.5f, -4.0f) {
    			cullFace(false) {
    				polygonMode(FrontAndBack, Line) {
    					rotation(0.0f, 0.0f, 1.0f, 0.0f) {
    						preRenderHook(angleHook)
    						cube
    					}
    				}
    			}
   		    }
    		translation(0.0f, -2.5f, -4.0f) {
    			polygonMode(Front, Line) {
    				frontFace(ClockWise) {
    					rotation(0.0f, 0.0f, 1.0f, 0.0f) {
    						preRenderHook(angleHook)
    						cube
    					}
    				}
    			}
   		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example3, true).show
    }

}