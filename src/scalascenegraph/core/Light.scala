package scalascenegraph.core

import scalascenegraph.core.Predefs._

class Light(lightType: LightType, position: Position, color: Color) extends Group {
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
    	renderer.pushLightMode
    	renderer.enableLight(lightType, position, color)
        super.doRender(context)
        renderer.popLightMode
    }

}