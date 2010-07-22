package scalascenegraph.core

class Triangle(v1: Vertex, v2: Vertex, v3: Vertex) extends Node {

    def render(renderer: Renderer) {
        renderer.triangle(v1, v2, v3)
    }
    
}

class ColoredTriangle(v1: Vertex, c1: Color, v2: Vertex, c2: Color, v3: Vertex, c3: Color)
extends Node {

    def render(renderer: Renderer) {
        renderer.triangle(v1, c1, v2, c2, v3, c3)
    }
	
}