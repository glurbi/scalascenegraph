package scalascenegraph.core

class World(val foreground: Color = Color.white,
            val background: Color = Color.grey)
extends Group {

    override def render(context: Context) {
    	val renderer = context.renderer
        renderer.clear
        renderer.clearColor(background);
        renderer.color(foreground)
        renderer.enableDepthTest
        renderer.enableCullFace
        doRender(context)
        renderer.flush
    }
  
}
