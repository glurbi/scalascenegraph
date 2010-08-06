package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.dsl._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._

object Example2 extends WorldBuilder {
	
    val colors = Array(1.0f, 0.0f, 0.0f, 0.0f,
    		           1.0f, 0.0f, 0.0f, 0.0f,
    		           1.0f, 0.0f, 0.0f, 0.0f,
    		           1.0f, 0.0f, 0.0f, 0.0f,
			             
                       0.0f, 1.0f, 0.0f, 0.0f,
                       0.0f, 1.0f, 0.0f, 0.0f,
                       0.0f, 1.0f, 0.0f, 0.0f,
                       0.0f, 1.0f, 0.0f, 0.0f,
			             
                       0.0f, 0.0f, 1.0f, 0.0f,
                       0.0f, 0.0f, 1.0f, 0.0f,
                       0.0f, 0.0f, 1.0f, 0.0f,
                       0.0f, 0.0f, 1.0f, 0.0f,
	                     
                       1.0f, 0.0f, 1.0f, 0.0f,
                       1.0f, 0.0f, 1.0f, 0.0f,
                       1.0f, 0.0f, 1.0f, 0.0f,
                       1.0f, 0.0f, 1.0f, 0.0f,
			             
                       1.0f, 1.0f, 0.0f, 0.0f,
                       1.0f, 1.0f, 0.0f, 0.0f,
                       1.0f, 1.0f, 0.0f, 0.0f,
                       1.0f, 1.0f, 0.0f, 0.0f,

                       0.0f, 1.0f, 1.0f, 0.0f,
                       0.0f, 1.0f, 1.0f, 0.0f,
                       0.0f, 1.0f, 1.0f, 0.0f,
                       0.0f, 1.0f, 1.0f, 0.0f)

	def example2 =
		world {
    		translation(0.0f, 2.0f, -5.0f) {
    			
    			triangle(
   					vertices = Array(2.0f, 0.0f, 0.0f, 4.0f, 0.0f, 0.0f, 3.0f, 2.0f, 0.0f),
   					colors = Array(1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f))
   					
   			    translation(0.0f, -3.0f, 2.0f) {
    				rotation(45.0f, 0.5f, 0.0f, 1.0f) {
    					cube(colors)
    				}
    			}
    			
				triangle(
					vertices = Array(-4.0f, 2.0f, 0.0f, -3.0f, 0.0f, 0.0f, -2.0f, 2.0f, 0.0f))
    			
   		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example2).show
    }

}
