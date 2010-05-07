package scalascenegraph.examples

import scalascenegraph.core.Color
import scalascenegraph.core.Vertex
import scalascenegraph.core.World
import scalascenegraph.core.Triangle
import scalascenegraph.core.Quad
import scalascenegraph.core.Translation
import scalascenegraph.core.PerspectiveCamera
import scalascenegraph.opengl.OpenglBrowser

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
        val browser = new OpenglBrowser(world, camera)
        browser.show
    }

}
