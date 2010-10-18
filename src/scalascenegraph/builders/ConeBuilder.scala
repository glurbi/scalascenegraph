package scalascenegraph.builders

import scala.math._
import scala.collection.mutable._
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

class ConeBuilder(n: Int, m: Int, r: Float, h: Float) extends RenderableBuilder {

	def createCone: Node = {
		val sideVertices = createSideVertices
		val bottomVertices = createBottomVertices
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(sideVertices))
		geometry.addRenderable(createRenderable(bottomVertices))
		geometry
	}

	def createSideVertices = {
		def coneSide(teta: Float, z: Float): Vertice3D = {
			val x = (r * z / h) * cos(teta)
			val y = (r * z / h) * sin(teta)
			Vertice3D(x, y, h-z)
		}
		val ab = new ArrayBuffer[Float]
		val tetaStep = 2 * Pi / n
		val zStep = h / m
		for (i <- 0 to n-1) {
			for (j <- 0 to m-1) {
				val teta = i * tetaStep
				val z = j * zStep
				ab ++= coneSide(teta, z).xyz
				ab ++= coneSide(teta, z+zStep).xyz
				ab ++= coneSide(teta+tetaStep, z+zStep).xyz
				ab ++= coneSide(teta+tetaStep, z).xyz
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
	}
	
	def createBottomVertices = {
		val ab = new ArrayBuffer[Float]
		ab ++= Vertice3D(0.0f, 0.0f, 0.0f).xyz
		val tetaStep = 2 * Pi / n
		for (i <- 0 to n) {
			val teta = i * tetaStep
			val x = r * cos(teta)
			val y = r * sin(teta)
			ab ++= Vertice3D(x, y, 0.0f).xyz
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_TRIANGLE_FAN)
	}
}

