package scalascenegraph.core

import scalascenegraph.core.Predefs._

class Triangle(v1: Vertice, v2: Vertice, v3: Vertice) extends Node {

    def doRender(context: Context) {
        context.renderer.triangle(v1, v2, v3)
    }
    
}

class ColoredTriangle(v1: Vertice, v2: Vertice, v3: Vertice,
    		          c1: Color, c2: Color, c3: Color)
extends Node {

    def doRender(context: Context) {
        context.renderer.triangle(v1, v2, v3, c1, c2, c3)
    }
	
}