package scalascenegraph.core

import java.nio._

object Predefs {

	type Hook[T] = (T, Context) => Unit
	
	type NodeHook = Hook[Node]
	type StateHook[T <: State] = Hook[T]
	
	abstract class ShadeModel
	case object Flat extends ShadeModel
	case object Smooth extends ShadeModel
	
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

	abstract class FogMode
	case class Linear(start: Float, end: Float) extends FogMode
	case class Exp(density: Float) extends FogMode
	case class Exp2(density: Float) extends FogMode
	
	class LightInstance(val n: Int)
	object LightInstance {
		def apply(n: Int): LightInstance = new LightInstance(n)
		def unapply(instance: LightInstance): Option[Int] = Some(instance.n)
	}
	case object Light0 extends LightInstance(0)
	case object Light1 extends LightInstance(1)
	case object Light2 extends LightInstance(2)
	case object Light3 extends LightInstance(3)
	case object Light4 extends LightInstance(4)
	case object Light5 extends LightInstance(5)
	case object Light6 extends LightInstance(6)
	case object Light7 extends LightInstance(7)
	
	case class Vector(x: Float, y: Float, z: Float) {
		val asFloatArray = Array(x, y, z)
	}
	case class Vertice(x: Float, y: Float, z: Float)  {
		val asFloatArray = Array(x, y, z)
	}
	case class Normal(x: Float, y: Float, z: Float)  {
		val asFloatArray = Array(x, y, z)
	}
	
	case class Vertices(floatBuffer: FloatBuffer) {
		def count: Int = floatBuffer.limit /3
	}
	case class Position(x: Float, y: Float, z: Float) {
		def asFloatArray: Array[Float] = Array(x, y, z)
	}
	
	case class Intensity(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		def asFloatArray: Array[Float] = Array(r, g, b, a)
	}

	case class Color(r: Float, g: Float, b: Float, a: Float = 1.0f) {
		val asFloatArray = Array(r, g, b, a)
	}
	
	case class Colors(floatBuffer: FloatBuffer)
	case class Normals(floatBuffer: FloatBuffer)
	
	implicit def toColor(c: java.awt.Color): Color = {
		new Color(c.getRed/255.0f, c.getGreen/255.0f, c.getBlue/255.0f, c.getAlpha/255.0f)
	}
	
}
