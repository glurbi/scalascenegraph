package scalascenegraph.core

class Quad(vertices: Array[Float]) extends Node {

    def doRender(renderer: Renderer, context: Context) {
        renderer.quad(vertices)
    }

}

class ColoredQuad(vertices: Array[Float], colors: Array[Float])
extends Node {

    def doRender(renderer: Renderer, context: Context) {
        renderer.quad(vertices, colors)
    }
    
}
