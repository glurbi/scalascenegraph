package scalascenegraph.builders

import java.nio._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class BoxBuilder(width: Float, height: Float, depth: Float, l: Int, m: Int, n: Int) {

	def createBox: Node = {
		new Node {
			private val quads = createVertices
			override def doRender(context: Context) {
				context.renderer.quads(quads)
			}
		}		
	}
	
	def createVertices: Vertices = {
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
				ab ++= Vertice(i * xStep - xOffset, j * yStep - yOffset, +zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, j * yStep - yOffset, +zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, (j+1) * yStep - yOffset, +zOffset).asFloatArray
				ab ++= Vertice(i * xStep - xOffset, (j+1) * yStep - yOffset, +zOffset).asFloatArray
			}
		}

		//
		// back face
		//
		for (i <- 0 until l) {
			for (j <- 0 until m) {
				ab ++= Vertice(i * xStep - xOffset, j * yStep - yOffset, -zOffset).asFloatArray
				ab ++= Vertice(i * xStep - xOffset, (j+1) * yStep - yOffset, -zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, (j+1) * yStep - yOffset, -zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, j * yStep - yOffset, -zOffset).asFloatArray
			}
		}
		
		//
		// right face
		//
		for (i <- 0 until m) {
			for (j <- 0 until n) {
				ab ++= Vertice(+xOffset, i * yStep - yOffset, j * zStep - zOffset).asFloatArray
				ab ++= Vertice(+xOffset, (i+1) * yStep - yOffset, j * zStep - zOffset).asFloatArray
				ab ++= Vertice(+xOffset, (i+1) * yStep - yOffset, (j+1) * zStep - zOffset).asFloatArray
				ab ++= Vertice(+xOffset, i * yStep - yOffset, (j+1) * zStep - zOffset).asFloatArray
			}
		}
		
		//
		// left face
		//
		for (i <- 0 until m) {
			for (j <- 0 until n) {
				ab ++= Vertice(-xOffset, i * yStep - yOffset, j * zStep - zOffset).asFloatArray
				ab ++= Vertice(-xOffset, i * yStep - yOffset, (j+1) * zStep - zOffset).asFloatArray
				ab ++= Vertice(-xOffset, (i+1) * yStep - yOffset, (j+1) * zStep - zOffset).asFloatArray
				ab ++= Vertice(-xOffset, (i+1) * yStep - yOffset, j * zStep - zOffset).asFloatArray
			}
		}
		
		//
		// top face
		//
		for (i <- 0 until l) {
			for (j <- 0 until n) {
				ab ++= Vertice(i * xStep - xOffset, +yOffset, j * zStep - zOffset).asFloatArray
				ab ++= Vertice(i * xStep - xOffset, +yOffset, (j+1) * zStep - zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, +yOffset, (j+1) * zStep - zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, +yOffset, j * zStep - zOffset).asFloatArray
			}
		}

		//
		// bottom face
		//
		for (i <- 0 until l) {
			for (j <- 0 until n) {
				ab ++= Vertice(i * xStep - xOffset, -yOffset, j * zStep - zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, -yOffset, j * zStep - zOffset).asFloatArray
				ab ++= Vertice((i+1) * xStep - xOffset, -yOffset, (j+1) * zStep - zOffset).asFloatArray
				ab ++= Vertice(i * xStep - xOffset, -yOffset, (j+1) * zStep - zOffset).asFloatArray
			}
		}
		
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray))
	}

}
