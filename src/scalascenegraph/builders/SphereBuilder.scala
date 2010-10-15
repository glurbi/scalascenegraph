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

class SphereBuilder(n: Int, r: Float) extends RenderableBuilder {

	def createSphere: Node = {
		val vertices = createVertices
		val normals = createNormals
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, normals))
		geometry
	}

	def createSphere(color: Color): Node = {
		val vertices = createVertices
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, color))
		geometry
	}

	def createSphere(colors: Colors): Node = {
		val vertices = createVertices
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, colors))
		geometry
	}

	def createSphere(texture: Texture): Node = {
		createSphere(texture, Off)
	}

	def createSphere(texture: Texture, light: OnOffState): Node = {
		val vertices = createVertices
		val textureCoordinates = createTextureCoordinates
		val geometry = new Geometry
		geometry.addRenderable(light match {
			case Off => createRenderable(vertices, textureCoordinates, texture)
			case On => {
				val normals = createNormals
				createRenderable(vertices, textureCoordinates, texture, normals)
			}
		})
		geometry
	}
	
	private def sphere(teta: Float, phi: Float): Vertice3D = {
		// cf http://en.wikipedia.org/wiki/Sphere
		val x = r * sin(teta) * cos(phi)
		val y = r * sin(teta) * sin(phi)
		val z = r * cos(teta)
		Vertice3D(x, y, z)
	}
	
	def createVertices = {
		val ab = new ArrayBuffer[Float]
		val stepAngle = Pi / n
		for (tetaStep <- 0 to n-1) {
			for (phiStep <- 0 to 2*n) {
				val teta = tetaStep * stepAngle
				val phi = phiStep * stepAngle
				ab ++= sphere(teta, phi).xyz
				ab ++= sphere(teta+stepAngle, phi).xyz
				ab ++= sphere(teta+stepAngle, phi+stepAngle).xyz
				ab ++= sphere(teta, phi+stepAngle).xyz
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)	
	}
	
	def createNormals = Normals(createVertices.buffer)
	
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

