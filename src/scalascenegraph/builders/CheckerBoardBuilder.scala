package scalascenegraph.builders

import java.nio._
import scala.math._
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
import scalascenegraph.core.Utils._

class CheckerBoardBuilder(n: Int, m: Int, c1: Color, c2: Color) {
    
    def createCheckerBoard(gl: GL4): Node = {
        val builder = new GeometryBuilder
        val positions = createPositions
        val colors = createColors
        builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
               .addAtribute(COLOR_ATTRIBUTE_INDEX, 3, GL_FLOAT, colors)
               .setPrimitiveType(GL_TRIANGLES)
               .setVertexCount(positions.length / 3)
               .build(gl)
    }
    
    def createPositions: Array[Float] = {
        val ab = new ArrayBuffer[Float]
        val xOffset = -n / 2.0f
        val yOffset = -m / 2.0f
        for (i <- 0 until n) {
            for (j <- 0 until m) {
                // 2 triangles to make a quad
                ab ++= new Vertice3D(i + xOffset, j + yOffset, 0.0f).xyz
                ab ++= new Vertice3D(i + 1.0f + xOffset, j + yOffset, 0.0f).xyz
                ab ++= new Vertice3D(i + 1.0f + xOffset, j + 1.0f + yOffset, 0.0f).xyz

                ab ++= new Vertice3D(i + xOffset, j + yOffset, 0.0f).xyz
                ab ++= new Vertice3D(i + 1.0f + xOffset, j + yOffset, 0.0f).xyz
                ab ++= new Vertice3D(i + xOffset, j + 1.0f + yOffset, 0.0f).xyz
            }
        }
        ab.toArray
    }

    def createColors: Array[Float] = {
        val ab = new ArrayBuffer[Float]
        for (i <- 0 until n) {
            for (j <- 0 until m) {
                val c = (i + j) % 2 match {
                    case 0 => c1
                    case 1 => c2
                }
                for (k <- 1 to 4) {
                    ab ++= c.rgba
                }
            }
        }
        ab.toArray
    }
    
}
