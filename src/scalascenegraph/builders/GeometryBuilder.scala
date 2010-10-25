package scalascenegraph.builders

import java.nio._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

// TODO: T --> VertexBuffer
// TODO: dont need this class any more...
trait GeometryBuilder extends RenderableBuilder {

	def createGeometry[T <: Buffer](vertices: Vertices[T]): Geometry = {
		new SimpleGeometry(createRenderable(vertices))
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									normals: Normals): Geometry =
	{
		new SimpleGeometry(createRenderable(vertices, normals))
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									color: Color): Geometry =
	{
		new SimpleGeometry(createRenderable(vertices, color))
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									color: Color,
									normals: Normals): Geometry =
    {
		new SimpleGeometry(createRenderable(vertices, color, normals))
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									textureCoordinates: TextureCoordinates,
									texture: Texture): Geometry =
	{
		new SimpleGeometry(createRenderable(vertices, textureCoordinates, texture))
	}

	def createGeometry[T <: Buffer](vertices: Vertices[T],
									textureCoordinates: TextureCoordinates,
									texture: Texture,
									normals: Normals): Geometry =
	{
		new SimpleGeometry(createRenderable(vertices, textureCoordinates, texture, normals))
	}

	def createGeometry[VertexBuffer <: Buffer, ColorBuffer <: Buffer](
		vertices: Vertices[VertexBuffer],
		colors: Colors[ColorBuffer]): Geometry =
	{
		new SimpleGeometry(createRenderable(vertices, colors))
	}

	def createGeometry[VertexBuffer <: Buffer, ColorBuffer <: Buffer](
		vertices: Vertices[VertexBuffer],
		colors: Colors[ColorBuffer],
		normals: Normals): Geometry =
	{
		new SimpleGeometry(createRenderable(vertices, colors, normals))
	}

}
