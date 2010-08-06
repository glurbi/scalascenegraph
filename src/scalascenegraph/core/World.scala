package scalascenegraph.core

import java.awt.{Color => JColor}
import scalascenegraph.core.Predefs._

class World extends Group {

	var foreground: Color = JColor.white
	var background: Color = JColor.lightGray
	
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
