package scalascenegraph.core.shapes

import java.nio._
import scala.math._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._

class Torus(n: Int, R: Float, r: Float) extends Node {

	implicit def doubleToFloat(d: Double): Float = d.asInstanceOf[Float]

	val stepAngle = 2.0 * Pi / n

	def angle(step: Int) = step * stepAngle
	
	/**
	 * cf http://en.wikipedia.org/wiki/Torus
	 */
	private def torus(u: Float, v: Float): Vertice = {
		val x = (R + r * cos(v)) * cos(u)
		val y = (R + r * cos(v)) * sin(u)
		val z = r * sin(v)
		Vertice(x, y, z)
	}
	
	protected val vertices = {
		val ab = new ArrayBuffer[Float]
		for (uStep <- 0 to n) {
			for (vStep <- 0 to n) {
				ab ++= (torus(angle(uStep), angle(vStep))).asFloatArray
				ab ++= (torus(angle(uStep+1), angle(vStep))).asFloatArray
				ab ++= (torus(angle(uStep+1), angle(vStep+1))).asFloatArray
				ab ++= (torus(angle(uStep), angle(vStep+1))).asFloatArray
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray))
	}

	private def torusNormal(uStep: Int, vStep: Int): Vector = {
		val v1 = torus(angle(uStep), angle(vStep-1))
		val v2 = torus(angle(uStep), angle(vStep+1))
		val v3 = torus(angle(uStep-1), angle(vStep))
		val v4 = torus(angle(uStep+1), angle(vStep))
		normalize(crossProduct(vector(v2, v1), vector(v3, v4)))
	}
	
	protected val normals = {
		val ab = new ArrayBuffer[Float]
		for (uStep <- 0 to n) {
			for (vStep <- 0 to n) {
				ab ++= torusNormal(uStep, vStep).asFloatArray
				ab ++= torusNormal(uStep+1, vStep).asFloatArray
				ab ++= torusNormal(uStep+1, vStep+1).asFloatArray
				ab ++= torusNormal(uStep, vStep+1).asFloatArray
			}
		}
		Normals(Buffers.newDirectFloatBuffer(ab.toArray))
	}
	
    def doRender(context: Context) {
        context.renderer.quads(vertices, normals)
    }
    
}