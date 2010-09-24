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
	case class Vector(x: Float, y: Float, z: Float) {
		val asFloatArray = Array(x, y, z)
	}
	
	/**
	 * A 3D point. 
	 */
	case class Vertice(x: Float, y: Float, z: Float)  {
		val asFloatArray = Array(x, y, z)
	}
	
	/**
	 * Surface normal vector.
	 */
	case class Normal(x: Float, y: Float, z: Float)  {
		val asFloatArray = Array(x, y, z)
	}
	
	/**
	 * Holds a set of vertices.
	 */
	case class Vertices(floatBuffer: FloatBuffer) {
		def count: Int = floatBuffer.limit / 3
	}
	
	/**
	 * A light position.
	 */
	case class Position(x: Float, y: Float, z: Float) {
		def asFloatArray: Array[Float] = Array(x, y, z)
	}
	
	/**
	 * A light intensity.
	 */
	case class Intensity(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		def asFloatArray: Array[Float] = Array(r, g, b, a)
	}

	/**
	 * A color.
	 */
	case class Color(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		val asFloatArray = Array(r, g, b, a)
	}
	
	/**
	 * A set of colors.
	 */
	case class Colors(floatBuffer: FloatBuffer)
	
	/**
	 * A set of normals.
	 */
	case class Normals(floatBuffer: FloatBuffer)
	
	/**
	 * A set of texture coordinates.
	 */
	case class TextureCoordinates(floatBuffer: FloatBuffer)
	
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
	case class UniformId(id: Any)

	/**
	 * A vertex buffer object (VBO) id
	 */
	case class VertexBufferObjectId(id: Any)
	
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
