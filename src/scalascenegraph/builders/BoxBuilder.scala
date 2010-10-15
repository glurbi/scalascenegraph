package scalascenegraph.builders

import java.nio._
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

class BoxBuilder(width: Float, height: Float, depth: Float, l: Int, m: Int, n: Int)
extends RenderableBuilder
{

	def createBox: Node = {
		val vertices = createVertices
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices))
		geometry
	}
	
	def createVertices: Vertices[FloatBuffer] = {
		val ab = new ArrayBuffer[Float]
		val xOffset = width / 2.0f
		val yOffset = height / 2.0f
		val zOffset = depth / 2.0f
		val xStep = width / l
		val yStep = height / m
		val zStep = depth / n
		
		//
		// front face
		//
		for (i <- 0 until l) {
			for (j <- 0 until m) {
				ab ++= Vertice3D(i * xStep - xOffset, j * yStep - yOffset, +zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, j * yStep - yOffset, +zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, (j+1) * yStep - yOffset, +zOffset).xyz
				ab ++= Vertice3D(i * xStep - xOffset, (j+1) * yStep - yOffset, +zOffset).xyz
			}
		}

		//
		// back face
		//
		for (i <- 0 until l) {
			for (j <- 0 until m) {
				ab ++= Vertice3D(i * xStep - xOffset, j * yStep - yOffset, -zOffset).xyz
				ab ++= Vertice3D(i * xStep - xOffset, (j+1) * yStep - yOffset, -zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, (j+1) * yStep - yOffset, -zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, j * yStep - yOffset, -zOffset).xyz
			}
		}
		
		//
		// right face
		//
		for (i <- 0 until m) {
			for (j <- 0 until n) {
				ab ++= Vertice3D(+xOffset, i * yStep - yOffset, j * zStep - zOffset).xyz
				ab ++= Vertice3D(+xOffset, (i+1) * yStep - yOffset, j * zStep - zOffset).xyz
				ab ++= Vertice3D(+xOffset, (i+1) * yStep - yOffset, (j+1) * zStep - zOffset).xyz
				ab ++= Vertice3D(+xOffset, i * yStep - yOffset, (j+1) * zStep - zOffset).xyz
			}
		}
		
		//
		// left face
		//
		for (i <- 0 until m) {
			for (j <- 0 until n) {
				ab ++= Vertice3D(-xOffset, i * yStep - yOffset, j * zStep - zOffset).xyz
				ab ++= Vertice3D(-xOffset, i * yStep - yOffset, (j+1) * zStep - zOffset).xyz
				ab ++= Vertice3D(-xOffset, (i+1) * yStep - yOffset, (j+1) * zStep - zOffset).xyz
				ab ++= Vertice3D(-xOffset, (i+1) * yStep - yOffset, j * zStep - zOffset).xyz
			}
		}
		
		//
		// top face
		//
		for (i <- 0 until l) {
			for (j <- 0 until n) {
				ab ++= Vertice3D(i * xStep - xOffset, +yOffset, j * zStep - zOffset).xyz
				ab ++= Vertice3D(i * xStep - xOffset, +yOffset, (j+1) * zStep - zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, +yOffset, (j+1) * zStep - zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, +yOffset, j * zStep - zOffset).xyz
			}
		}

		//
		// bottom face
		//
		for (i <- 0 until l) {
			for (j <- 0 until n) {
				ab ++= Vertice3D(i * xStep - xOffset, -yOffset, j * zStep - zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, -yOffset, j * zStep - zOffset).xyz
				ab ++= Vertice3D((i+1) * xStep - xOffset, -yOffset, (j+1) * zStep - zOffset).xyz
				ab ++= Vertice3D(i * xStep - xOffset, -yOffset, (j+1) * zStep - zOffset).xyz
			}
		}
		
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
	}

}
