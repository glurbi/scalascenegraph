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

class LightMode(var state: OnOffMode) extends Mode {

    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushLightMode
    	renderer.setLightMode(state)
        super.doRender(context)
        renderer.popLightMode
    }
	
}

class AmbientLightMode(intensity: Intensity) extends Mode {
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushLightMode
    	renderer.setAmbientLight(intensity)
        super.doRender(context)
        renderer.popLightMode
    }
	
}

class MaterialMode(face: Face, lightType: LightType, color: Color)  extends Mode {
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushLightMode
    	renderer.setMaterial(face, lightType, color)
        super.doRender(context)
        renderer.popLightMode
    }
	
}

class LineWidthMode(width: Float) extends Mode {
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushLineMode
    	renderer.setLineWidth(width)
        super.doRender(context)
        renderer.popLineMode
    }
    
}