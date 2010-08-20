package scalascenegraph.core.shapes

import java.nio._
import com.jogamp.common.nio._
import scalascenegraph.core._
import scalascenegraph.core.Predefs._


object Cube {
	
	def apply(): Node = {
		node(context => context.renderer.quads(Cube.vertices, Cube.normals))
	}

	def apply(colors: Colors): Node = {
		node(context => context.renderer.quads(Cube.vertices, colors))
	}

	def apply(color: Color): Node = {
		node(context => context.renderer.quads(Cube.vertices, color))
	}
	
	private val vertices = Vertices(Buffers.newDirectFloatBuffer(
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
				  
	private val normals = Normals(Buffers.newDirectFloatBuffer(
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

