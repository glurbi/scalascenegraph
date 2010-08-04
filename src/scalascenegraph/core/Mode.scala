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

class CullFaceMode(var cullFace: Boolean) extends Mode {
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushCullFace
    	cullFace match {
    		case true => renderer.enableCullFace
    		case false => renderer.disableCullFace
    	}
        super.doRender(context)
        renderer.popCullFace
    }
	
}

class FrontFaceMode(var frontFace: FrontFace) extends Mode {
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushFrontFace
    	renderer.setFrontFace(frontFace)
        super.doRender(context)
        renderer.popFrontFace
    }
	
}

class LightMode(var mode: OnOffMode) extends Mode {

    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushLightMode
    	renderer.setLightMode(mode)
        super.doRender(context)
        renderer.popLightMode
    }
	
}
