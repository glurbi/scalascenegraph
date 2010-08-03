package scalascenegraph.core

import java.nio._
import com.jogamp.common.nio._

object Cube {
	
	val vertices = Buffers.newDirectFloatBuffer(
			Array(0.0f, 0.0f, 0.0f,
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
				  1.0f, 1.0f, 0.0f))
	                     
}

class Cube() extends Node {

    def doRender(context: Context) {
        context.renderer.quads(Cube.vertices)
    }

}

class ColoredCube(colors: FloatBuffer) extends Node {

    def doRender(context: Context) {
        context.renderer.quads(Cube.vertices, colors)
    }
    
}
