package scalascenegraph.core

class Quad(vertices: Array[Float]) extends Node {

    def render(renderer: Renderer) {
        renderer.quad(vertices)
    }

}

class ColoredQuad(vertices: Array[Float], colors: Array[Float])
extends Node {

    def render(renderer: Renderer) {
        renderer.quad(vertices, colors)
    }
    
}
