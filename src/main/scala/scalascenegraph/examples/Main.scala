package scalascenegraph.examples

import javax.swing._

import scalascenegraph.core._
import scalascenegraph.opengl._
import scalascenegraph.ui.browser._

object Main {
  
    def main(args: Array[String]) {
      
        val camera = new PerspectiveCamera(-0.5f, 0.5f, -0.5, 0.5f, 1.0f, 100.0f);
        val world = new World(Color.grey);
        val translation = new Translation(0.0f, 0.0f, -5.0f)
        val triangle = new Triangle(Vertex(-1.0f, 0.0f, 0.0f), Vertex(0.0f, 2.0f, 0.0f), Vertex(1.0f, 0.0f, 0.0f))
        val quad = new Quad(Vertex(2.0f, 0.0f, 0.0f), Vertex(2.0f, 2.0f, 0.0f), Vertex(3.0f, 2.0f, 0.0f), Vertex(3.0f, 0.0f, 0.0f))
        world.add(translation)
        translation.add(triangle)
        translation.add(quad)
      
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        val browser = new OpenglBrowser(world, camera)
       	val controlFrame = new BrowserControlFrame
        browser.show
        controlFrame.setLocation(500, 0)
        controlFrame.setVisible(true)
    }

}
