package scalascenegraph.core

class Triangle(vertices: Array[Float]) extends Node {

    def render(renderer: Renderer) {
        renderer.triangle(vertices)
    }
    
}

class ColoredTriangle(vertices: Array[Float], colors: Array[Float])
extends Node {

    def render(renderer: Renderer) {
        renderer.triangle(vertices, colors)
    }
	
}