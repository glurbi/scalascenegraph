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

class NormalFieldBuilder(geometry: Geometry) {

	def createNormalLines: Vertices[FloatBuffer] = {
		val ab = new ArrayBuffer[Float]
		createNormalLines(geometry, ab)
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_LINES)
	}

	private def createNormalLines(g: Geometry, ab: ArrayBuffer[Float]) {
		if (g.isInstanceOf[SimpleGeometry[FloatBuffer, FloatBuffer]]) {
			val sg = g.asInstanceOf[SimpleGeometry[FloatBuffer, FloatBuffer]]
			createNormalLinesFromSimpleGeometry(sg, ab)
		} else if (geometry.isInstanceOf[CompositeGeometry]) {
			val cg = g.asInstanceOf[CompositeGeometry]
			createNormalLinesFromCompositeGeometry(cg, ab)
		}
	}

	private def createNormalLinesFromCompositeGeometry(cg: CompositeGeometry, ab: ArrayBuffer[Float]) {
		cg.geometries.foreach { g => createNormalLines(g, ab) }
	}

	private def createNormalLinesFromSimpleGeometry(sg: SimpleGeometry[FloatBuffer, FloatBuffer], ab: ArrayBuffer[Float]) {
		val vbuf = sg.vertices.buffer
		val nbuf = sg.normals.floatBuffer
		for (i <- 0 until vbuf.limit / 3) {
			val v = new Vertice3D(vbuf.get, vbuf.get, vbuf.get)
			val n = new Normal3D(nbuf.get, nbuf.get, nbuf.get)
			ab ++= v.xyz
			ab ++= new Vertice3D(v.x + n.x, v.y + n.y, v.z + n.z).xyz
		}
		vbuf.rewind
		nbuf.rewind
	}

}
