package scalascenegraph.core

class Transformation extends Group {
  
}

class Translation(var x: Float, var y: Float, var z: Float) extends Transformation {
  
      override def render(renderer: Renderer) {
          renderer.translate(x, y, z)
          super.render(renderer)
      }
      
}