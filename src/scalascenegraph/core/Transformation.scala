package scalascenegraph.core

class Transformation extends Group {
  
}

class Translation(var x: Float, var y: Float, var z: Float) extends Transformation {
  
      override def doRender(renderer: Renderer, context: Context) {
    	  renderer.pushMatrix
          renderer.translate(x, y, z)
          super.doRender(renderer, context)
          renderer.popMatrix
      }
      
}

class Rotation(var angle: Float, var x: Float, var y: Float, var z: Float) extends Transformation {
  
      override def doRender(renderer: Renderer, context: Context) {
    	  renderer.pushMatrix
          renderer.rotate(angle, x, y, z)
          super.doRender(renderer, context)
          renderer.popMatrix
      }
      
}