package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._

object Example1 extends WorldBuilder {
	
	def example1 =
		world {
    		translation(0.0f, 0.0f, -5.0f) {
    			
    			triangle(Vertice(-1.0f, 0.0f, 0.0f),
    					 Vertice(1.0f, 0.0f, 0.0f),
    					 Vertice(0.0f, 2.0f, 0.0f),
   					     Color(1.0f, 0.0f, 0.0f),
   					     Color(0.0f, 1.0f, 0.0f),
   					     Color(0.0f, 0.0f, 1.0f))

				triangle(Vertice(-4.0f, 2.0f, 0.0f),
						 Vertice(-3.0f, 0.0f, 0.0f),
						 Vertice(-2.0f, 2.0f, 0.0f))

    			quad(Vertice(2.0f, 0.0f, 0.0f),
    				 Vertice(4.0f, 0.0f, 0.0f),
    				 Vertice(4.0f, 2.0f, 0.0f),
    				 Vertice(2.0f, 2.0f, 0.0f),
    				 Color(1.0f, 1.0f, 0.0f))
   		    }
	    }
	
    def main(args: Array[String]) {
        OpenglBrowser.getDefault(example1).show
    }

}
