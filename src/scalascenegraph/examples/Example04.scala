package scalascenegraph.examples

import java.util._
import java.nio._
import java.awt.{Color => JColor }
import scala.math._
import scala.collection.mutable._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import com.jogamp.common.nio._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example04 extends Example with WorldBuilder {
    
    val r = new Random

    val smiley = new Texture(getClass.getResourceAsStream("/scalascenegraph/examples/smiley.png"))

    def createVertices: Vertices[FloatBuffer] = {
        val ab = new ArrayBuffer[Float]
        for (i <- 0 to 50000) {
            val x = (r.nextFloat - 0.5f) * 20
            val y = (r.nextFloat - 0.5f) * 20
            val z = (r.nextFloat - 0.5f) * 20
            ab ++= new Vertice3D(x, y, z).xyz
        }
        Vertices(Buffers.newDirectFloatBuffer(ab.toArray), GL_FLOAT, dim_3D, GL_POINTS)
    }

    def example =
        world {
            blending(On)
            pointSprite(On)
            pointSize(16.0f)
            attach(smiley)
            bindTexture(GL_TEXTURE_2D, smiley)
            attach(new SimpleGeometry(createVertices))
        }
    
}
