package scalascenegraph.core

import java.nio._

object Predefs {

	type Hook = (Node, Context) => Unit
	
	abstract class Face
	case object Front extends Face
	case object Back extends Face
	case object FrontAndBack extends Face

	abstract class LightType
	case object AmbientLight extends LightType
	case object DiffuseLight extends LightType
	case object SpecularLight extends LightType
	case object EmissionLight extends LightType
	
	abstract class DrawingMode
	case object Point extends DrawingMode
	case object Line extends DrawingMode
	case object Fill extends DrawingMode

	abstract class FrontFace
	case object ClockWise extends FrontFace
	case object CounterClockWise extends FrontFace
	
	abstract class OnOffMode
	case object On extends OnOffMode
	case object Off extends OnOffMode

	case class Vertices(val floatBuffer: FloatBuffer) {
		def count: Int = floatBuffer.limit /3
	}
	case class Colors(val floatBuffer: FloatBuffer)
	case class Normals(val floatBuffer: FloatBuffer)
	
	case class Position(x: Float, y: Float, z: Float) {
		def asFloatArray: Array[Float] = Array(x, y, z)
	}
	
	case class Intensity(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		def asFloatArray: Array[Float] = Array(r, g, b, a)
	}
	
	object Color {
		val white = new Color(1.0f, 1.0f, 1.0f)
		val grey = new Color(0.5f, 0.5f, 0.5f)
		val blue = new Color(0.0f, 0.0f, 1.0f)
		val red = new Color(1.0f, 0.0f, 0.0f)
	}

	case class Color(val red: Float, val green: Float, val blue: Float, val alpha: Float = 1.0f) {
		val asFloatArray = Array(red, green, blue, alpha)
	}

}
