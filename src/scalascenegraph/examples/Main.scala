package scalascenegraph.examples

import scalascenegraph.core.Color
import scalascenegraph.core.World
import scalascenegraph.core.Triangle
import scalascenegraph.core.PerspectiveCamera
import scalascenegraph.opengl.OpenglBrowser

object Main {
  
    def main(args: Array[String]) {
        val camera = new PerspectiveCamera(-0.5f, 0.5f, -0.5, 0.5f, 1.0f, 100.0f);
        val world = new World(Color.grey);
        val triangle = new Triangle
        world.add(triangle)
        val browser = new OpenglBrowser(world, camera)
        browser.show
    }

}
