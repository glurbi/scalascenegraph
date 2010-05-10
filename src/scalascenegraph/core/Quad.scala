package scalascenegraph.core

class Quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex) extends Node {

    def render(renderer: Renderer) {
        renderer.quad(v1, v2, v3, v4)
    }
    
}
