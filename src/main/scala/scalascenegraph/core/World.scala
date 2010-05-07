package scalascenegraph.core

class World(val background: Color) extends Group {

    override def render(renderer: Renderer) {
        renderer.clear
        super.render(renderer)
        renderer.flush
    }
  
}
