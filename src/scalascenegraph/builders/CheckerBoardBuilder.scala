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
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._

class CheckerBoardBuilder(n: Int, m: Int, c1: Color, c2: Color)
extends RenderableBuilder {
	
	def createCheckerBoard: Node = {
		val vertices = createVertices
		val colors = createColors
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, colors))
		geometry
	}
	
	def createVertices: Vertices[FloatBuffer] = {
		val ab = new ArrayBuffer[Float]
		val xOffset = -n / 2.0f
		val yOffset = -m / 2.0f
		for (i <- 0 until n) {
			for (j <- 0 until m) {
				ab ++= Vertice3D(i + xOffset, j + yOffset, 0.0f).xyz
				ab ++= Vertice3D(i + 1.0f + xOffset, j + yOffset, 0.0f).xyz
				ab ++= Vertice3D(i + 1.0f + xOffset, j + 1.0f + yOffset, 0.0f).xyz
				ab ++= Vertice3D(i + xOffset, j + 1.0f + yOffset, 0.0f).xyz
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
	}

	def createColors = {
		val ab = new ArrayBuffer[Float]
		for (i <- 0 until n) {
			for (j <- 0 until m) {
				val c = (i + j) % 2 match {
					case 0 => c1
					case 1 => c2
				}
				ab ++= c.rgba
				ab ++= c.rgba
				ab ++= c.rgba
				ab ++= c.rgba
			}
		}
		Colors(Buffers.newDirectFloatBuffer(ab.toArray), RGBA)
	}
	
}
