package scalascenegraph.builders

import java.nio._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

// TODO: T --> VertexBuffer
trait GeometryBuilder extends RenderableBuilder {

	def createGeometry[T <: Buffer](vertices: Vertices[T]): Geometry = {
		val geometry = new Geometry
		val renderable = createRenderable(vertices)
		geometry.addRenderable(renderable)
		geometry
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									normals: Normals): Geometry =
	{
		val geometry = new Geometry
		val renderable = createRenderable(vertices, normals)
		geometry.addRenderable(renderable)
		geometry
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									color: Color): Geometry =
	{
		val geometry = new Geometry
		val renderable = createRenderable(vertices, color)
		geometry.addRenderable(renderable)
		geometry
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									color: Color,
									normals: Normals): Geometry =
    {
		val geometry = new Geometry
		val renderable = createRenderable(vertices, color, normals)
		geometry.addRenderable(renderable)
		geometry
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									textureCoordinates: TextureCoordinates,
									texture: Texture): Geometry =
	{
		val geometry = new Geometry
		val renderable = createRenderable(vertices, textureCoordinates, texture)
		geometry.addRenderable(renderable)
		geometry
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									textureCoordinates: TextureCoordinates,
									texture: Texture,
									normals: Normals): Geometry =
	{
		val geometry = new Geometry
		val renderable = createRenderable(vertices, textureCoordinates, texture, normals)
		geometry.addRenderable(renderable)
		geometry
	}

	def createGeometry[VertexBuffer <: Buffer, ColorBuffer <: Buffer](
		vertices: Vertices[VertexBuffer],
		colors: Colors[ColorBuffer]): Geometry =
	{
		val geometry = new Geometry
		val renderable = createRenderable(vertices, colors)
		geometry.addRenderable(renderable)
		geometry
	}

	def createGeometry[VertexBuffer <: Buffer, ColorBuffer <: Buffer](
		vertices: Vertices[VertexBuffer],
		colors: Colors[ColorBuffer],
		normals: Normals): Geometry =
	{
		val geometry = new Geometry
		val renderable = createRenderable(vertices, colors, normals)
		geometry.addRenderable(renderable)
		geometry
	}

}
