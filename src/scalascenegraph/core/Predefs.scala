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
	 * Defines the shade model used for rendering.
	 */
	abstract class ShadeModel
	case object Flat extends ShadeModel
	case object Smooth extends ShadeModel
	
	/**
	 * Defines the "active" face of a polygon. 
	 */
	abstract class Face
	case object Front extends Face
	case object Back extends Face
	case object FrontAndBack extends Face

	/**
	 * Defines a light type.
	 */
	abstract class LightType
	case object AmbientLight extends LightType
	case object DiffuseLight extends LightType
	case object SpecularLight extends LightType
	case object EmissionLight extends LightType
	case object AmbientAndDiffuseLight extends LightType

	/**
	 * Defines a polygon drawing mode.
	 */
	abstract class DrawingMode
	case object Point extends DrawingMode
	case object Line extends DrawingMode
	case object Fill extends DrawingMode

	/**
	 * Defines how the front and back face of a polygon are calculated. 
	 */
	abstract class FrontFace
	case object ClockWise extends FrontFace
	case object CounterClockWise extends FrontFace
	
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
	case class TextureId(id: Any)

	/**
	 * Implicit conversion of AWT colors to ScalaSceneGraph colors.
	 */
	implicit def toColor(c: java.awt.Color): Color = {
		new Color(c.getRed/255.0f, c.getGreen/255.0f, c.getBlue/255.0f, c.getAlpha/255.0f)
	}

	/**
	 * Defines a geometry type.
	 */
	abstract class GeometryType
	case object PointArray extends GeometryType
	case object LineArray extends GeometryType
	case object TriangleArray extends GeometryType
	case object QuadArray extends GeometryType

	/**
	 * Defines a color type.
	 */
	abstract class ColorType
	case object UnspecifiedColor extends ColorType
	case object UniColorNoAlpha extends ColorType
	case object UniColorAlpha extends ColorType
	case object MultiColorNoAlpha extends ColorType
	case object MultiColorAlpha extends ColorType
	
	/**
	 * Defines a normal type.
	 */
	abstract class NormalType
	case object UnspecifiedNormal extends NormalType
	case object UniNormal extends NormalType
	case object MultiNormal extends NormalType
	
	/**
	 * Defines an image type.
	 */
	abstract class ImageType
	case object RGB extends ImageType
	case object RGBA extends ImageType
	
	def node(parent: Node, fun: (Context) => Unit): Node = new Node(parent) {
		override def doRender(context: Context) {
			fun(context)
		}
	}

}
