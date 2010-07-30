package scalascenegraph.core

object Cube {
	
	val vertices = Array(0.0f, 0.0f, 0.0f,
						 0.0f, 1.0f, 0.0f,
			             1.0f, 1.0f, 0.0f,
			             1.0f, 0.0f, 0.0f,
			             
			             0.0f, 0.0f, 0.0f,
			             0.0f, 0.0f, 1.0f,
			             0.0f, 1.0f, 1.0f,
			             0.0f, 1.0f, 0.0f,
			             
	                     0.0f, 0.0f, 0.0f,
	                     1.0f, 0.0f, 0.0f,
	                     1.0f, 0.0f, 1.0f,
	                     0.0f, 0.0f, 1.0f,

                         0.0f, 0.0f, 1.0f,
                         1.0f, 0.0f, 1.0f,
			             1.0f, 1.0f, 1.0f,
			             0.0f, 1.0f, 1.0f,
			             
			             1.0f, 0.0f, 0.0f,
			             1.0f, 1.0f, 0.0f,
			             1.0f, 1.0f, 1.0f,
			             1.0f, 0.0f, 1.0f,
			             
	                     0.0f, 1.0f, 0.0f,
	                     0.0f, 1.0f, 1.0f,
	                     1.0f, 1.0f, 1.0f,
	                     1.0f, 1.0f, 0.0f)
	                     
}

class Cube() extends Node {
	
    def doRender(renderer: Renderer, context: Context) {
        renderer.quads(Cube.vertices)
    }

}

class ColoredCube(colors: Array[Float]) extends Node {

    def doRender(renderer: Renderer, context: Context) {
        renderer.quads(Cube.vertices, colors)
    }
    
}
