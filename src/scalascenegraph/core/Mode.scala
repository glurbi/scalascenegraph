package scalascenegraph.core

import scalascenegraph.core.Predefs._

class Mode extends Group {

}

class PolygonMode(var face: Face, var mode: DrawingMode) extends Mode {

    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushPolygonMode
    	renderer.setPolygonMode(face, mode)
        super.doRender(context)
        renderer.popPolygonMode
    }
	
}
