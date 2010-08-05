package scalascenegraph.core

import scalascenegraph.core.Predefs._

class World(val foreground: Color = Color.white,
            val background: Color = Color.grey)
extends Group {

    override def doRender(context: Context) {
    	val renderer = context.renderer
        renderer.clear
        renderer.clearColor(background);
        renderer.color(foreground)
        renderer.enableDepthTest
        renderer.enableCullFace
        super.doRender(context)
        renderer.flush
    }
  
}
