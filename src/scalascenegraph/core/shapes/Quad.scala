package scalascenegraph.core

import scalascenegraph.core.Predefs._

object Quad {
	
	def apply(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice): Node = {
		node(context => context.renderer.quad(v1, v2, v3, v4))
	}
	
	def apply(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice,
   		      c1: Color, c2: Color, c3: Color, c4: Color): Node = {
		node(context => context.renderer.quad(v1, v2, v3, v4, c1, c2, c3, c4))
	}
	
}
