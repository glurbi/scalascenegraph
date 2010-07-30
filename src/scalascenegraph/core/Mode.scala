package scalascenegraph.core

import scalascenegraph.core.Predefs._

class Mode extends Group {

}

class PolygonMode(var face: Face, var mode: DrawingMode) extends Mode {

    override def doRender(renderer: Renderer, context: Context) {
    	renderer.pushPolygonMode
    	renderer.setPolygonMode(face, mode)
        super.doRender(renderer, context)
        renderer.popPolygonMode
    }
	
}
