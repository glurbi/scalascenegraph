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

class ConeBuilder(n: Int, m: Int, r: Float, h: Float) {

    def createCone(normals: Boolean): CompositeGeometry = {
        val geometry = new CompositeGeometry
        normals match {
            case false => {
                geometry.addGeometry(new SimpleGeometry(createSideVertices))
                geometry.addGeometry(new SimpleGeometry(createBottomVertices))
            }
            case true => {
                geometry.addGeometry(new SimpleGeometry(createSideVertices, createSideNormals))
                geometry.addGeometry(new SimpleGeometry(createBottomVertices, createBottomNormals))
            }
        }
        geometry
    }

    def createSideVertices: Vertices[FloatBuffer] = {
        def coneSideVertex(teta: Float, z: Float): Vertice3D = {
            val x = (r * z / h) * cos(teta)
            val y = (r * z / h) * sin(teta)
            new Vertice3D(x, y, h-z)
        }
        val ab = new ArrayBuffer[Float]
        val tetaStep = 2 * Pi / n
        val zStep = h / m
        for (i <- 0 to n-1) {
            for (j <- 0 to m-1) {
                val teta = i * tetaStep
                val z = j * zStep
                ab ++= coneSideVertex(teta, z).xyz
                ab ++= coneSideVertex(teta, z+zStep).xyz
                ab ++= coneSideVertex(teta+tetaStep, z+zStep).xyz
                ab ++= coneSideVertex(teta+tetaStep, z).xyz
            }
        }
        Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_QUADS)
    }
    
    def createBottomVertices: Vertices[FloatBuffer] = {
        val ab = new ArrayBuffer[Float]
        ab ++= new Vertice3D(0.0f, 0.0f, 0.0f).xyz
        val tetaStep = 2 * Pi / n
        for (i <- 0 to n) {
            val teta = i * tetaStep
            val x = r * sin(teta)
            val y = r * cos(teta)
            ab ++= new Vertice3D(x, y, 0.0f).xyz
        }
        Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_TRIANGLE_FAN)
    }

    def createSideNormals: Normals = {
        def coneSideNormal(teta: Float): Normal3D = {
            val x = r * cos(teta)
            val y = r * sin(teta)
            val z = r / sqrt(r*r+h*h)
            normalize(new Normal3D(x, y, z))
        }
        val ab = new ArrayBuffer[Float]
        val tetaStep = 2 * Pi / n
        for (i <- 0 to n-1) {
            for (j <- 0 to m-1) {
                val teta = i * tetaStep
                ab ++= coneSideNormal(teta).xyz
                ab ++= coneSideNormal(teta).xyz
                ab ++= coneSideNormal(teta+tetaStep).xyz
                ab ++= coneSideNormal(teta+tetaStep).xyz
            }
        }
        Normals(Buffers.newDirectFloatBuffer(ab.toArray))
    }

    def createBottomNormals: Normals = {
        val ab = new ArrayBuffer[Float]
        val normal = new Normal3D(0.0f, 0.0f, -1.0f)
        val tetaStep = 2 * Pi / n
        ab ++= normal.xyz
        for (i <- 0 to n) {
            ab ++= normal.xyz
        }
        Normals(Buffers.newDirectFloatBuffer(ab.toArray))
    }

}

