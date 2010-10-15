package scalascenegraph.core

import java.nio._

object Predefs {

	/**
	 * A Hook defines a callback function that can be called at different stage
	 * of the rendering. 
	 */
	type Hook[T] = (T, Context) => Unit
	
	/**
	 * A Hook that takes a Node and a Context in parameter.
	 */
	type NodeHook[T <: Node] = Hook[T]
	
	/**
	 * A Hook that takes a State and a Context in parameter.
	 */
	type StateHook[T <: State] = Hook[T]
	
	/**
	 * A 3D vector.
	 */
	case class Vector3D(x: Float, y: Float, z: Float) {
		val xyz = Array(x, y, z)
	}
	
	/**
	 * A 3D point. 
	 */
	case class Vertice3D(x: Float, y: Float, z: Float)  {
		val xyz = Array(x, y, z)
	}
	
	/**
	 * Surface normal vector.
	 */
	case class Normal(x: Float, y: Float, z: Float)  {
		val xyz = Array(x, y, z)
	}
	
	/**
	 * Holds a set of vertices.
	 */
	case class Vertices[T <: Buffer](
			buffer: T,
			dataType: DataType,
			vertexDimension: VertexDimension,
			primitiveType: PrimitiveType)
	{
		val count: Int = buffer.limit / vertexDimension
	}
	
	/**
	 * A light position.
	 */
	case class Position(x: Float, y: Float, z: Float) {
		def xyz: Array[Float] = Array(x, y, z)
	}
	
	/**
	 * A light intensity.
	 */
	case class Intensity(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		def rgba: Array[Float] = Array(r, g, b, a)
	}

	/**
	 * A color.
	 */
	case class Color(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		val rgba = Array(r, g, b, a)
		val rgb = Array(r, g, b)
	}
	
	/**
	 * A set of colors.
	 */
	case class Colors(floatBuffer: FloatBuffer, colorType: ColorType)
	
	/**
	 * A set of normals.
	 */
	case class Normals(floatBuffer: FloatBuffer)
	
	/**
	 * A set of texture coordinates.
	 */
	case class TextureCoordinates(floatBuffer: FloatBuffer)
	
	/**
	 * Defines how many coordinates a vertex has.
	 */
	type VertexDimension = Int
	val dim_2D = 2
	val dim_3D = 3

	/**
	 * Defines a color type.
	 */
	type ColorType = Int
	val RGB = 3
	val RGBA = 4
	
	/**
	 * Defines a data type.
	 * GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT, GL_UNSIGNED_SHORT,
	 * GL_INT, GL_UNSIGNED_INT, GL_FLOAT, GL_DOUBLE
	 */
	type DataType = Int

	/**
	 * Defines a primitive type to smooth (antialias).
	 * GL_POINT_SMOOTH, GL_LINE_SMOOTH, GL_POLYGON_SMOOTH
	 */
	type SmoothType = Int
	
	/**
	 * Defines the projection type
	 */
	abstract class ProjectionType
	case object Perspective extends ProjectionType
	case object Parallel extends ProjectionType
	
	/**
	 * A texture identifier.
	 */
	type TextureId = Int

	/**
	 * A shader identifier.
	 */
	type ShaderId = Int
	
	/**
	 * A program identifier.
	 */
	type ProgramId = Int
	
	/**
	 * A uniform identifier
	 */
	type UniformId = Int

	/**
	 * A vertex buffer object (VBO) id
	 */
	type VBOId = Int
	
	/**
	 * Defines the shade model used for rendering.
	 */
	type ShadeModel = Int
	
	/**
	 * Defines the "active" face of a polygon. 
	 */
	type Face = Int

	/**
	 * Defines a light type.
	 */
	type LightType = Int

	/**
	 * Defines a polygon drawing mode.
	 */
	type DrawingMode = Int

	/**
	 * Defines how the front and back face of a polygon are calculated. 
	 */
	type FrontFace = Int
	
	/**
	 * Defines the type of primitive to draw.
	 * GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_TRIANGLE_STRIP,
	 * GL_TRIANGLE_FAN, GL_TRIANGLES, GL_QUAD_STRIP, GL_QUADS
	 */
	type PrimitiveType = Int
	
	/**
	 * Defines a state that can have on/off as a value.
	 */
	abstract class OnOffState
	case object On extends OnOffState
	case object Off extends OnOffState

	/**
	 * Defines the equation used for calculating the fog component.
	 */
	abstract class FogMode
	case class Linear(start: Float, end: Float) extends FogMode
	case class Exp(density: Float) extends FogMode
	case class Exp2(density: Float) extends FogMode
	
	/**
	 * Defines the different light instances.
	 */
	type LightInstance = Int

	/**
	 * Defines a shader type.
	 */
	type ShaderType = Int
	
	/**
	 * Defines an image type.
	 */
	type ImageType = Int
	
	/**
	 * Implicit conversion of AWT colors to ScalaSceneGraph colors.
	 */
	implicit def toColor(c: java.awt.Color): Color = {
		new Color(c.getRed/255.0f, c.getGreen/255.0f, c.getBlue/255.0f, c.getAlpha/255.0f)
	}

}
