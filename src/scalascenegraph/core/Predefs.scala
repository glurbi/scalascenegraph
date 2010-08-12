package scalascenegraph.core

import java.nio._

object Predefs {

	type Hook[T] = (T, Context) => Unit
	
	type NodeHook = Hook[Node]
	type StateHook[T <: State] = Hook[T]
	
	abstract class Face
	case object Front extends Face
	case object Back extends Face
	case object FrontAndBack extends Face

	abstract class LightType
	case object AmbientLight extends LightType
	case object DiffuseLight extends LightType
	case object SpecularLight extends LightType
	case object EmissionLight extends LightType
	case object AmbientAndDiffuseLight extends LightType
	
	abstract class DrawingMode
	case object Point extends DrawingMode
	case object Line extends DrawingMode
	case object Fill extends DrawingMode

	abstract class FrontFace
	case object ClockWise extends FrontFace
	case object CounterClockWise extends FrontFace
	
	abstract class OnOffState
	case object On extends OnOffState
	case object Off extends OnOffState

	case class Vertice(x: Float, y: Float, z: Float) {
		val asFloatArray = Array(x, y, z) 
	}
	
	case class Vertices(floatBuffer: FloatBuffer) {
		def count: Int = floatBuffer.limit /3
	}
	case class Colors(floatBuffer: FloatBuffer)
	case class Normals(floatBuffer: FloatBuffer)
	
	case class Position(x: Float, y: Float, z: Float) {
		def asFloatArray: Array[Float] = Array(x, y, z)
	}
	
	case class Intensity(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		def asFloatArray: Array[Float] = Array(r, g, b, a)
	}

	case class Color(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		val asFloatArray = Array(r, g, b, a)
	}
	
	implicit def toColor(c: java.awt.Color): Color = {
		new Color(c.getRed/255.0f, c.getGreen/255.0f, c.getBlue/255.0f, c.getAlpha/255.0f)
	}
	
}
