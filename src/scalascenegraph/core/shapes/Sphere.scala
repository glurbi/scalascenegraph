package scalascenegraph.core

import java.nio._
import scala.math._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core.Predefs._

object Sphere {
	
	def create(n: Int, r: Float): Node = {
		val builder = new SphereBuilder(n, r)
		val vertices = builder.createVertices
		val normals = builder.createNormals
		new Node {
			def doRender(context: Context) {
				context.renderer.quads(vertices, normals)
			}
		}
	}
	
	def create(n: Int, r: Float, color: Color): Node = {
		val builder = new SphereBuilder(n, r)
		val vertices = builder.createVertices
		new Node {
			def doRender(context: Context) {
				context.renderer.quads(vertices, color)
			}
		}
	}
	
	def create(n: Int, r: Float, colors: Colors): Node = {
		val builder = new SphereBuilder(n, r)
		val vertices = builder.createVertices
		new Node {
			def doRender(context: Context) {
				context.renderer.quads(vertices, colors)
			}
		}
	}
	
}

class SphereBuilder(n: Int, r: Float) {

	implicit def doubleToFloat(d: Double): Float = d.asInstanceOf[Float]
	
	def createVertices = {
		val ab = new ArrayBuffer[Float]
		val stepAngle = Pi / n
		
		for (latStep <- 0 to n) {
			for (lonStep <- 0 to 2*n) {
				
				val teta = latStep * stepAngle
				val phi = lonStep * stepAngle
				
				ab += (sin(teta) * cos(phi))
				ab += (sin(teta) * sin(phi))
				ab += (cos(teta))
				
				ab += (sin(teta+stepAngle) * cos(phi))
				ab += (sin(teta+stepAngle) * sin(phi))
				ab += (cos(teta+stepAngle))
				
				ab += (sin(teta+stepAngle) * cos(phi+stepAngle))
				ab += (sin(teta+stepAngle) * sin(phi+stepAngle))
				ab += (cos(teta+stepAngle))
				
				ab += (sin(teta) * cos(phi+stepAngle))
				ab += (sin(teta) * sin(phi+stepAngle))
				ab += (cos(teta))
				
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray))
	}
	
	def createNormals = Normals(createVertices.floatBuffer)
	
}

