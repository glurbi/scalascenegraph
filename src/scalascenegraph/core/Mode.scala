package scalascenegraph.core

import scalascenegraph.core.Predefs._

class Mode extends Group {

}

class PolygonMode(face: Face, mode: DrawingMode) extends Mode {

    override def render(renderer: Renderer) {
    	renderer.pushPolygonMode
    	renderer.setPolygonMode(face, mode)
        super.render(renderer)
        renderer.popPolygonMode
    }
	
}
