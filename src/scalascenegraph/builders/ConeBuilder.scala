package scalascenegraph.builders

import java.nio._
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
import scalascenegraph.core.Utils._
import scalascenegraph.core.Predefs._

class ConeBuilder(n: Int, m: Int, r: Float, h: Float) extends RenderableBuilder {

	def createCone(normals: Boolean): Node = {
		val geometry = new Geometry
		normals match {
			case false => {
				geometry.addRenderable(createRenderable(createSideVertices))
				geometry.addRenderable(createRenderable(createBottomVertices))
			}
			case true => {
				geometry.addRenderable(createRenderable(createSideVertices, createSideNormals))
				geometry.addRenderable(createRenderable(createBottomVertices, createBottomNormals))
			}
		}
		geometry
	}

	def createSideVertices: Vertices[FloatBuffer] = {
		def coneSideVertex(teta: Float, z: Float): Vertice3D = {
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
				ab ++= coneSideVertex(teta, z).xyz
				ab ++= coneSideVertex(teta, z+zStep).xyz
				ab ++= coneSideVertex(teta+tetaStep, z+zStep).xyz
				ab ++= coneSideVertex(teta+tetaStep, z).xyz
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
	}
	
	def createBottomVertices: Vertices[FloatBuffer] = {
		val ab = new ArrayBuffer[Float]
		ab ++= Vertice3D(0.0f, 0.0f, 0.0f).xyz
		val tetaStep = 2 * Pi / n
		for (i <- 0 to n) {
			val teta = i * tetaStep
			val x = r * sin(teta)
			val y = r * cos(teta)
			ab ++= Vertice3D(x, y, 0.0f).xyz
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_TRIANGLE_FAN)
	}

	def createSideNormals: Normals = {
		def coneSideNormal(teta: Float): Normal = {
			val x = - r * sin(teta)
			val y = r * cos(teta)
			val z = r / Math.sqrt(r*r+h*h)
			normalize(Normal(x, y, z))
		}
		val ab = new ArrayBuffer[Float]
		val tetaStep = 2 * Pi / n
		for (i <- 0 to n-1) {
			for (j <- 0 to m-1) {
				val teta = i * tetaStep
				ab ++= coneSideNormal(teta).xyz
				ab ++= coneSideNormal(teta).xyz
				ab ++= coneSideNormal(teta+tetaStep).xyz
				ab ++= coneSideNormal(teta+tetaStep).xyz
			}
		}
		Normals(Buffers.newDirectFloatBuffer(ab.toArray))
	}

	def createBottomNormals: Normals = {
		val ab = new ArrayBuffer[Float]
		val normal = Normal(0.0f, 0.0f, -1.0f)
		val tetaStep = 2 * Pi / n
		for (i <- 0 to n) {
			ab ++= normal.xyz
		}
		Normals(Buffers.newDirectFloatBuffer(ab.toArray))
	}

}

