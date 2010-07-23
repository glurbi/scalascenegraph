package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.dsl._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._

object Example1 {
	
	val builder = new WorldBuilder
	import builder._

	def example1 =
		world {
    		translation(0.0f, 0.0f, -5.0f) {
    			
    			coloredTriangle(Vertex(-1.0f, 0.0f, 0.0f),
    					        Color(1.0f, 0.0f, 0.0f),
    					        Vertex(0.0f, 2.0f, 0.0f),
    					        Color(0.0f, 1.0f, 0.0f),
    					        Vertex(1.0f, 0.0f, 0.0f),
    					        Color(0.0f, 0.0f, 1.0f))
    					        
				triangle(Vertex(-4.0f, 2.0f, 0.0f),
						 Vertex(-3.0f, 0.0f, 0.0f),
    					 Vertex(-2.0f, 2.0f, 0.0f))
    					 
    			unicoloredQuad(Vertex(2.0f, 0.0f, 0.0f),
    						   Vertex(2.0f, 2.0f, 0.0f),
    						   Vertex(4.0f, 2.0f, 0.0f),
    						   Vertex(4.0f, 0.0f, 0.0f),
    						   Color(1.0f, 1.0f, 0.0f))
   		    }
	    }
	
    def main(args: Array[String]) {
        val browserConfig = this.getClass.getResourceAsStream("/browser.properties")
        val cameraConfig = this.getClass.getResourceAsStream("/camera.properties")
        val camera = Camera.get(cameraConfig)
        val browser = OpenglBrowser.get(example1, browserConfig)
        browser.setCamera(camera)
        browser.show
    }

}
