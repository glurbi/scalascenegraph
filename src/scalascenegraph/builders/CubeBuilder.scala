package scalascenegraph.builders

import java.nio._
import com.jogamp.common.nio._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._


import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class CubeBuilder extends RenderableBuilder {
	
	def createCube: Node = {
		val vertices = createVertices
		val normals = createNormals
		val geometry = new Geometry
		geometry.addRenderable(createQuadsNormalsRenderable(vertices, normals))
		geometry
	}

	def createCube(colors: Colors): Node = {
		val vertices = createVertices
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(GL_QUADS, vertices, colors))
		geometry
	}

	def createCube(color: Color): Node = {
		val vertices = createVertices
		val geometry = new Geometry
		geometry.addRenderable(createQuadsColorRenderable(vertices, color))
		geometry
	}
	
	def createCube(texture: Texture): Node = {
		val vertices = createVertices
		val textureCoordinates = createTextureCoordinates
		val geometry = new Geometry
		geometry.addRenderable(createQuadsTextureRenderable(vertices, textureCoordinates, texture))
		geometry
	}
	
	def createCube(texture: Texture, light: OnOffState): Node = {
		val vertices = createVertices
		val normals = createNormals
		val textureCoordinates = createTextureCoordinates
		val geometry = new Geometry
		geometry.addRenderable(light match {
			case On => createQuadsTextureNormalsRenderable(vertices, textureCoordinates, texture, normals)
			case Off => createQuadsTextureRenderable(vertices, textureCoordinates, texture)
		})
		geometry
	}
	
	private val createVertices = Vertices(Buffers.newDirectFloatBuffer(
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
				  0.5f, 0.5f, -0.5f)), dim_3D, GL_QUADS)
				  
	val createNormals = Normals(Buffers.newDirectFloatBuffer(
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
	                     
	val createTextureCoordinates = TextureCoordinates(Buffers.newDirectFloatBuffer(
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

