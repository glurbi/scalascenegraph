package scalascenegraph.builders

import scala.math._
import scala.collection.mutable._
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
import scalascenegraph.core.Utils._

class EllipsoidBuilder(n: Int, m: Int, a: Float, b: Float, c: Float) {

	private def ellipsoid(teta: Float, phi: Float): Vertice3D = {
		// cf http://en.wikipedia.org/wiki/Ellipsoid
		val x = a * sin(phi) * cos(teta)
		val y = b * sin(phi) * sin(teta)
		val z = c * cos(phi)
		new Vertice3D(x, y, z)
	}
	
	def createVertices: Vertices[FloatBuffer] = {
		val ab = new ArrayBuffer[Float]
		val tetaStep = 2 * Pi / n
		val phiStep = Pi / m
		for (i <- 0 to n-1) {
			for (j <- 0 to m-1) {
				val teta = i * tetaStep
				val phi = j * phiStep
				ab ++= ellipsoid(teta, phi).xyz
				ab ++= ellipsoid(teta, phi+phiStep).xyz
				ab ++= ellipsoid(teta+tetaStep, phi+phiStep).xyz
				ab ++= ellipsoid(teta+tetaStep, phi).xyz
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
	}

	def createNormals: Normals = {
		val buf = createVertices.buffer
		val ab = new ArrayBuffer[Float]
		for (i <- 0 until buf.limit / 3) {
			val x = buf.get
			val y = buf.get
			val z = buf.get
			ab ++= normalize(new Normal3D(2*x/(a*a), 2*y/(b*b), 2*z/(c*c))).xyz
		}
		Normals(Buffers.newDirectFloatBuffer(ab.toArray))
	}

    // TODO: use TRIANGLE_STRIPS
	def createPositions = {
		val ab = new ArrayBuffer[Float]
		val tetaStep = 2 * Pi / n
		val phiStep = Pi / m
		for (i <- 0 to n-1) {
			for (j <- 0 to m-1) {
				val teta = i * tetaStep
				val phi = j * phiStep
                
				ab ++= ellipsoid(teta, phi).xyz
				ab ++= ellipsoid(teta, phi+phiStep).xyz
				ab ++= ellipsoid(teta+tetaStep, phi+phiStep).xyz
                
				ab ++= ellipsoid(teta, phi).xyz
				ab ++= ellipsoid(teta+tetaStep, phi+phiStep).xyz
				ab ++= ellipsoid(teta+tetaStep, phi).xyz
			}
		}
		Buffers.newDirectFloatBuffer(ab.toArray)
	}
    
}
