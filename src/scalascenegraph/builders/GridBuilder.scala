package scalascenegraph.builders

import java.nio._
import scala.collection.mutable._
import com.jogamp.common.nio._
import javax.media.opengl._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL4._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class GridBuilder(width: Float, height: Float, m: Int, n: Int) {

	def createGrid(gl: GL4): Node = {
        val builder = new GeometryBuilder
		val positions = createPositions
        builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
               .setPrimitiveType(GL_TRIANGLES)
               .setVertexCount(positions.length / 3)
               .build(gl)
	}

	def createGrid(gl: GL4, texture: Texture): Node = {
        val builder = new GeometryBuilder
		val positions = createPositions
		val textureCoordinates = createTextureCoordinates
        builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
               .addAtribute(TEXTURE_COORDINATE_ATTRIBUTE_INDEX, 3, GL_FLOAT, textureCoordinates)
               .setPrimitiveType(GL_TRIANGLES)
               .setVertexCount(positions.length / 3)
               .build(gl)
	}
	
	def createPositions: Array[Float] = {
		val ab = new ArrayBuffer[Float]
		val xOffset = width / 2.0f
		val yOffset = height / 2.0f
		val xStep = width / m
		val yStep = height / n
		for (i <- 0 until m) {
			for (j <- 0 until n) {
				ab ++= new Vertice3D(i * xStep - xOffset, j * yStep - yOffset, 0.0f).xyz
				ab ++= new Vertice3D((i+1) * xStep - xOffset, j * yStep - yOffset, 0.0f).xyz
				ab ++= new Vertice3D((i+1) * xStep - xOffset, (j+1) * yStep - yOffset, 0.0f).xyz

				ab ++= new Vertice3D(i * xStep - xOffset, j * yStep - yOffset, 0.0f).xyz
				ab ++= new Vertice3D((i+1) * xStep - xOffset, j * yStep - yOffset, 0.0f).xyz
				ab ++= new Vertice3D(i * xStep - xOffset, (j+1) * yStep - yOffset, 0.0f).xyz
			}
		}
		ab.toArray
	}

	def createTextureCoordinates: Array[Float] = {
		val ab = new ArrayBuffer[Float]
		for (i <- 0 until m) {
			for (j <- 0 until n) {
				ab ++= Array(1.0f * i / m, 1.0f * j / n)
				ab ++= Array(1.0f * (i+1) / m, 1.0f * j / n)
				ab ++= Array(1.0f * (i+1) / m, 1.0f * (j+1) / n)

				ab ++= Array(1.0f * i / m, 1.0f * j / n)
				ab ++= Array(1.0f * (i+1) / m, 1.0f * j / n)
				ab ++= Array(1.0f * i / m, 1.0f * (j+1) / n)
			}
		}
		ab.toArray
	}
}
