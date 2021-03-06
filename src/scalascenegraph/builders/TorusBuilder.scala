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

class TorusBuilder(n: Int, R: Float, r: Float) {
	
	def createTorus: Node = {
		val vertices = createVertices
		val normals = createNormals
		new SimpleGeometry(vertices, normals)
	}
	
	val stepAngle = 2.0 * Pi / n
	def angle(step: Int) = step * stepAngle

	private def torus(u: Float, v: Float): Vertice3D = {
		// cf http://en.wikipedia.org/wiki/Torus
		val x = (R + r * cos(v)) * cos(u)
		val y = (R + r * cos(v)) * sin(u)
		val z = r * sin(v)
		new Vertice3D(x, y, z)
	}
	
	private def torusNormal(uStep: Int, vStep: Int): Vector3D = {
		val v1 = torus(angle(uStep), angle(vStep-1))
		val v2 = torus(angle(uStep), angle(vStep+1))
		val v3 = torus(angle(uStep-1), angle(vStep))
		val v4 = torus(angle(uStep+1), angle(vStep))
		normalize(crossProduct(vector(v2, v1), vector(v3, v4)))
	}
	
	def createVertices: Vertices[FloatBuffer] = {
		val ab = new ArrayBuffer[Float]
		for (uStep <- 0 to n) {
			for (vStep <- 0 to n) {
				ab ++= (torus(angle(uStep), angle(vStep))).xyz
				ab ++= (torus(angle(uStep+1), angle(vStep))).xyz
				ab ++= (torus(angle(uStep+1), angle(vStep+1))).xyz
				ab ++= (torus(angle(uStep), angle(vStep+1))).xyz
			}
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
	}

	def createNormals: Normals = {
		val ab = new ArrayBuffer[Float]
		for (uStep <- 0 to n) {
			for (vStep <- 0 to n) {
				ab ++= torusNormal(uStep, vStep).xyz
				ab ++= torusNormal(uStep+1, vStep).xyz
				ab ++= torusNormal(uStep+1, vStep+1).xyz
				ab ++= torusNormal(uStep, vStep+1).xyz
			}
		}
		Normals(Buffers.newDirectFloatBuffer(ab.toArray))
	}
	
    def createPositions = {
        val ab = new ArrayBuffer[Float]
        for (uStep <- 0 to n) {
            for (vStep <- 0 to n) {
                ab ++= (torus(angle(uStep), angle(vStep))).xyz
                ab ++= (torus(angle(uStep+1), angle(vStep))).xyz
                ab ++= (torus(angle(uStep+1), angle(vStep+1))).xyz
                
                ab ++= (torus(angle(uStep), angle(vStep))).xyz
                ab ++= (torus(angle(uStep+1), angle(vStep+1))).xyz
                ab ++= (torus(angle(uStep), angle(vStep+1))).xyz
            }
        }
        Buffers.newDirectFloatBuffer(ab.toArray)
	}

	def createNormals2 = {
		val ab = new ArrayBuffer[Float]
		for (uStep <- 0 to n) {
			for (vStep <- 0 to n) {
				ab ++= torusNormal(uStep, vStep).xyz
				ab ++= torusNormal(uStep+1, vStep).xyz
				ab ++= torusNormal(uStep+1, vStep+1).xyz
                
				ab ++= torusNormal(uStep, vStep).xyz
				ab ++= torusNormal(uStep+1, vStep+1).xyz
				ab ++= torusNormal(uStep, vStep+1).xyz
			}
		}
		Buffers.newDirectFloatBuffer(ab.toArray)
	}
	
}
