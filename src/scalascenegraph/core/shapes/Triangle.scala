package scalascenegraph.core

import scalascenegraph.core.Predefs._

object Triangle {

    def apply(parent: Node, v1: Vertice, v2: Vertice, v3: Vertice): Node = {
        node(parent, context => context.renderer.triangle(v1, v2, v3))
    }

    def apply(parent: Node, v1: Vertice, v2: Vertice, v3: Vertice,
	          c1: Color, c2: Color, c3: Color): Node = {
    	node(parent, context =>	context.renderer.triangle(v1, v2, v3, c1, c2, c3))
    }
	
}