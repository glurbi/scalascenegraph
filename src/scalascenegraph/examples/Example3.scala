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
	
	def example2 =
		world {
    		translation(-0.5f, -0.5f, -5.0f) {
    			polygonMode(Front, Line) {
    				rotation(0.0f, 1.0f, 0.5f, 1.0f) {
    					preRenderHook(angleHook)
    					cube
    				}
    			}
   		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example2, true).show
    }

}
