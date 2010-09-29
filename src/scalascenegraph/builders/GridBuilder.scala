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

class GridBuilder(width: Float, height: Float, m: Int, n: Int)
extends RenderableBuilder
{

	def createGrid: Node = {
		val vertices = createVertices
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices))
		geometry
	}
	
	def createVertices: Vertices[FloatBuffer] = {
		val ab = new ArrayBuffer[Float]
		val xOffset = width / 2.0f
		val yOffset = height / 2.0f
		val xStep = width / m
		val yStep = height / n
		
		for (i <- 0 until m) {
			for (j <- 0 until n) {
				ab ++= Vertice3D(i * xStep - xOffset, j * yStep - yOffset, 0.0f).asFloatArray
				ab ++= Vertice3D((i+1) * xStep - xOffset, j * yStep - yOffset, 0.0f).asFloatArray
				ab ++= Vertice3D((i+1) * xStep - xOffset, (j+1) * yStep - yOffset, 0.0f).asFloatArray
				ab ++= Vertice3D(i * xStep - xOffset, (j+1) * yStep - yOffset, 0.0f).asFloatArray
			}
		}
		
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
	}

}
