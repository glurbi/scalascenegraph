package scalascenegraph.core

object Cube {
	
	val vertices = Array(0.0f, 0.0f, 0.0f,
			             1.0f, 0.0f, 0.0f,
			             1.0f, 1.0f, 0.0f,
			             0.0f, 1.0f, 0.0f,
			             
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
	                     1.0f, 1.0f, 0.0f,
	                     1.0f, 1.0f, 1.0f,
	                     0.0f, 1.0f, 1.0f)
	                     
}

class Cube() extends Node {
	
    def render(renderer: Renderer) {
        renderer.quads(Cube.vertices)
    }

}

class ColoredCube(colors: Array[Float]) extends Node {

    def render(renderer: Renderer) {
        renderer.quads(Cube.vertices, colors)
    }
    
}
