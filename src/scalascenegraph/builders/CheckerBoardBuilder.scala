package scalascenegraph.builders

import java.nio._
import scala.math._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._

class CheckerBoardBuilder(n: Int, m: Int, c1: Color, c2: Color) {

	def createCheckerBoard: Node = {
		val vertices = createVertices
		val colors = createColors
		new Node {
			def render(context: Context) {
				context.renderer.quads(vertices, colors)
			}
		}		
	}
	
	def createVertices: Vertices = {
		val ab = new ArrayBuffer[Float]
		val xOffset = -n / 2.0f
		val yOffset = -m / 2.0f
		for (i <- 0 until n) {
			for (j <- 0 until m) {
				ab ++= Vertice(i + xOffset, j + yOffset, 0.0f).asFloatArray
				ab ++= Vertice(i + 1.0f + xOffset, j + yOffset, 0.0f).asFloatArray
				ab ++= Vertice(i + 1.0f + xOffset, j + 1.0f + yOffset, 0.0f).asFloatArray
				ab ++= Vertice(i + xOffset, j + 1.0f + yOffset, 0.0f).asFloatArray
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray))
	}

	def createColors = {
		val ab = new ArrayBuffer[Float]
		for (i <- 0 until n) {
			for (j <- 0 until m) {
				val c = (i + j) % 2 match {
					case 0 => c1
					case 1 => c2
				}
				ab ++= c.asFloatArray
				ab ++= c.asFloatArray
				ab ++= c.asFloatArray
				ab ++= c.asFloatArray
			}
		}
		Buffers.newDirectFloatBuffer(ab.toArray)
	}
	
}
