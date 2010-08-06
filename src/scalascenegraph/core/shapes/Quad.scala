package scalascenegraph.core

import scalascenegraph.core.Predefs._

class Quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice) extends Node {

    def doRender(context: Context) {
        context.renderer.quad(v1, v2, v3, v4)
    }

}

class ColoredQuad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice,
    		      c1: Color, c2: Color, c3: Color, c4: Color)
extends Node {

    def doRender(context: Context) {
        context.renderer.quad(v1, v2, v3, v4, c1, c2, c3, c4)
    }
    
}
