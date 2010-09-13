package scalascenegraph.core.shapes

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

trait QuadArray extends Node {
	def quads: Vertices
	def render(context: Context) {
		context.renderer.quads(quads)
	}
}
