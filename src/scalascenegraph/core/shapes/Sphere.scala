package scalascenegraph.core

import java.nio._
import scala.math._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core.Predefs._

object Sphere {
	
	def apply(parent: Node, n: Int, r: Float): Node = {
		val builder = new SphereBuilder(n, r)
		val vertices = builder.createVertices
		val normals = builder.createNormals
		node(parent, context => context.renderer.quads(vertices, normals))
	}
	
	def apply(parent: Node, n: Int, r: Float, color: Color): Node = {
		val builder = new SphereBuilder(n, r)
		val vertices = builder.createVertices
		node(parent, context => context.renderer.quads(vertices, color))
	}
	
	def apply(parent: Node, n: Int, r: Float, colors: Colors): Node = {
		val builder = new SphereBuilder(n, r)
		val vertices = builder.createVertices
		node(parent, context => context.renderer.quads(vertices, colors))
	}
	
	def apply(parent: Node, n: Int, r: Float, texture: Texture): Node = {
		val builder = new SphereBuilder(n, r)
		val vertices = builder.createVertices
		val textureCoordinates = builder.createTextureCoordinates
		new Node(parent) {
			override def doRender(context: Context) {
				context.renderer.quads(vertices, textureCoordinates, texture)
			}
			override def prepare(context: Context) {
				// TODO: move where it should be!
				texture.prepare(context)
			}
		}
	}
	
}

private class SphereBuilder(n: Int, r: Float) {

	implicit def doubleToFloat(d: Double): Float = d.asInstanceOf[Float]
	
	private def sphere(teta: Float, phi: Float): Vertice = {
		// cf http://en.wikipedia.org/wiki/Sphere
		val x = r * sin(teta) * cos(phi)
		val y = r * sin(teta) * sin(phi)
		val z = r * cos(teta)
		Vertice(x, y, z)
	}
	
	def createVertices = {
		val ab = new ArrayBuffer[Float]
		val stepAngle = Pi / n
		for (tetaStep <- 0 to n) {
			for (phiStep <- 0 to 2*n) {
				val teta = tetaStep * stepAngle
				val phi = phiStep * stepAngle
				ab ++= sphere(teta, phi).asFloatArray
				ab ++= sphere(teta+stepAngle, phi).asFloatArray
				ab ++= sphere(teta+stepAngle, phi+stepAngle).asFloatArray
				ab ++= sphere(teta, phi+stepAngle).asFloatArray
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray))
	}
	
	def createNormals = Normals(createVertices.floatBuffer)
	
	def createTextureCoordinates = {
		val ab = new ArrayBuffer[Float]
		for (i <- 0 to n) {
			for (j <- 0 to 2*n) {
				ab ++= Array(1.0f * i / n, 1.0f * j / n)
				ab ++= Array(1.0f * (i+1) / n, 1.0f * j / n)
				ab ++= Array(1.0f * (i+1) / n, 1.0f * (j+1) / n)
				ab ++= Array(1.0f * i / n, 1.0f * (j+1) / n)
			}
		}
		TextureCoordinates(Buffers.newDirectFloatBuffer(ab.toArray))
	}
	
}

