package scalascenegraph.core

class Quad(vertices: Array[Float]) extends Node {

    def doRender(context: Context) {
        context.renderer.quad(vertices)
    }

}

class ColoredQuad(vertices: Array[Float], colors: Array[Float])
extends Node {

    def doRender(context: Context) {
        context.renderer.quad(vertices, colors)
    }
    
}
