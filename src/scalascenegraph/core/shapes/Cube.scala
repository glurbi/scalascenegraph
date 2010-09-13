package scalascenegraph.core.shapes

import java.nio._
import com.jogamp.common.nio._
import scalascenegraph.core._
import scalascenegraph.core.Predefs._

object Cube {
	
	def create: Node = {
		node(context => context.renderer.quads(Cube.vertices, Cube.normals))
	}

	def apply(colors: Colors): Node = {
		node(context => context.renderer.quads(Cube.vertices, colors))
	}

	def apply(color: Color): Node = {
		node(context => context.renderer.quads(Cube.vertices, color))
	}
	
	def apply(texture: Texture): Node = {
		new Node {
			def render(context: Context) {
				context.renderer.quads(vertices, textureCoordinates, texture)
			}
		}
	}
	
	def apply(texture: Texture, light: OnOffState): Node = {
		light match {
			case On => new Node {
				def render(context: Context) {
					context.renderer.quads(vertices, textureCoordinates, texture, normals)
				}
			}
			case Off => Cube(texture)
		}
		
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
	                     
	private val textureCoordinates = TextureCoordinates(Buffers.newDirectFloatBuffer(
			Array(0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 1.0f)))
}

