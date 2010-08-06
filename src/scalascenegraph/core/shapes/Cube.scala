package scalascenegraph.core.shapes

import java.nio._
import com.jogamp.common.nio._
import scalascenegraph.core._
import scalascenegraph.core.Predefs._


object Cube {
	
	val vertices = Vertices(Buffers.newDirectFloatBuffer(
			Array(-0.5f, -0.5f, -0.5f,
				  -0.5f, 0.5f, -0.5f,
				  0.5f, 0.5f, -0.5f,
				  0.5f, -0.5f, -0.5f,

				  -0.5f, -0.5f, -0.5f,
				  -0.5f, -0.5f, 0.5f,
				  -0.5f, 0.5f, 0.5f,
				  -0.5f, 0.5f, -0.5f,

				  -0.5f, -0.5f, -0.5f,
				  0.5f, -0.5f, -0.5f,
				  0.5f, -0.5f, 0.5f,
				  -0.5f, -0.5f, 0.5f,

				  -0.5f, -0.5f, 0.5f,
				  0.5f, -0.5f, 0.5f,
				  0.5f, 0.5f, 0.5f,
				  -0.5f, 0.5f, 0.5f,

				  0.5f, -0.5f, -0.5f,
				  0.5f, 0.5f, -0.5f,
				  0.5f, 0.5f, 0.5f,
				  0.5f, -0.5f, 0.5f,

				  -0.5f, 0.5f, -0.5f,
				  -0.5f, 0.5f, 0.5f,
				  0.5f, 0.5f, 0.5f,
				  0.5f, 0.5f, -0.5f)))
				  
	val normals = Normals(Buffers.newDirectFloatBuffer(
			Array(0.0f, 0.0f, -1.0f,
				  0.0f, 0.0f, -1.0f,
				  0.0f, 0.0f, -1.0f,
				  0.0f, 0.0f, -1.0f,

				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,

				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,

				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,

				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,

				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f)))
	                     
}

class Cube extends Node {

    def doRender(context: Context) {
        context.renderer.quads(Cube.vertices, Cube.normals)
    }

}

class ColoredCube(colors: Colors) extends Node {

    def doRender(context: Context) {
        context.renderer.quads(Cube.vertices, colors)
    }
    
}

class UnicoloredCube(color: Color) extends Node {

    def doRender(context: Context) {
        context.renderer.quads(Cube.vertices, color)
    }
    
}
