package scalascenegraph.core

class Triangle(vertices: Array[Float]) extends Node {

    def doRender(context: Context) {
        context.renderer.triangle(vertices)
    }
    
}

class ColoredTriangle(vertices: Array[Float], colors: Array[Float])
extends Node {

    def doRender(context: Context) {
        context.renderer.triangle(vertices, colors)
    }
	
}