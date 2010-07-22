package scalascenegraph.examples

import scalascenegraph.core._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._

object Main {
  
    def main(args: Array[String]) {
      
        val world = new World
        val translation = new Translation(0.0f, 0.0f, -5.0f)
        val triangle = new ColoredTriangle(Vertex(-1.0f, 0.0f, 0.0f),
        		                           Color(1.0f, 0.0f, 0.0f),
        		                           Vertex(0.0f, 2.0f, 0.0f),
        		                           Color(0.0f, 1.0f, 0.0f),
        		                           Vertex(1.0f, 0.0f, 0.0f),
        		                           Color(0.0f, 0.0f, 1.0f))
        val triangle2 = new Triangle(Vertex(-3.0f, 0.0f, 0.0f),
        		                     Vertex(-3.0f, 2.0f, -1.0f),
        		                     Vertex(-2.5f, 0.0f, -2.0f))
        val quad = new UnicoloredQuad(Vertex(2.0f, 0.0f, 0.0f),
                                      Vertex(2.0f, 2.0f, 0.0f),
                                      Vertex(3.0f, 2.0f, 0.0f),
                                      Vertex(3.0f, 0.0f, 0.0f),
                                      Color(1.0f, 1.0f, 0.0f))
        world.add(translation)
        translation.add(triangle)
        translation.add(triangle2)
        translation.add(quad)
      
        val browser = new OpenglBrowser(world)
        val config = getClass.getResourceAsStream("/camera.properties")
        browser.setCamera(Camera.get(config))
        browser.show
    }

}
