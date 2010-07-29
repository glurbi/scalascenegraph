package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._

object Example3 extends WorldBuilder {
	
	def example2 =
		world {
    		translation(-0.5f, -0.5f, -2.5f) {
    			polygonMode(Front, Line) {
    				rotation(0.0f, 1.0f, 1.0f, 1.0f) {
    					cube
    				}
    			}
   		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example2, true).show
    }

}
