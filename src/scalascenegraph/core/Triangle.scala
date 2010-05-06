package scalascenegraph.core

class Triangle(v1: Vertex, v2: Vertex, v3: Vertex) extends Node {

    def render(renderer: Renderer) {
        renderer.triangle(v1, v2, v3)
    }
    
}
