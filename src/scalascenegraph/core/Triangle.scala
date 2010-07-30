package scalascenegraph.core

class Triangle(vertices: Array[Float]) extends Node {

    def doRender(renderer: Renderer, context: Context) {
        renderer.triangle(vertices)
    }
    
}

class ColoredTriangle(vertices: Array[Float], colors: Array[Float])
extends Node {

    def doRender(renderer: Renderer, context: Context) {
        renderer.triangle(vertices, colors)
    }
	
}