package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
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
    			
    			triangle(Vertice(2.0f, 0.0f, 0.0f),
    					 Vertice(4.0f, 0.0f, 0.0f),
    					 Vertice(3.0f, 2.0f, 0.0f),
   					     Color(1.0f, 0.0f, 0.0f),
   					     Color(0.0f, 1.0f, 0.0f),
   					     Color(0.0f, 0.0f, 1.0f))
   					
   			    translation(0.0f, -3.0f, 2.0f) {
    				rotation(45.0f, 0.5f, 0.0f, 1.0f) {
    					cube(colors)
    				}
    			}
    			
				triangle(Vertice(-4.0f, 2.0f, 0.0f),
						 Vertice(-3.0f, 0.0f, 0.0f),
						 Vertice(-2.0f, 2.0f, 0.0f))
    			
   		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example2).show
    }

}
