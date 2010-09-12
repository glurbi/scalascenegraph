package scalascenegraph.core.shapes

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

trait QuadArray extends Node {
	def quads: Vertices
	override def doRender(context: Context) {
		context.renderer.quads(quads)
	}
}
