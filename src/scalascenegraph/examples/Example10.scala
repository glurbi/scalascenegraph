package scalascenegraph.examples

import javax.swing._
import java.nio._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
import com.jogamp.common.nio._
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

import scalascenegraph.ui.browser._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.builders._
import scalascenegraph.core._
import scalascenegraph.shaders._
import ExampleUtils._

object Example10 {
    def main(args: Array[String]) {
        val example10 = new Example10
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example10.example, animated = true).show
    }
}

class Example10 extends WorldBuilder {
    
    val nofSamples = 10000
    val nofSignals = 300
    val amplitude = 1.0f
    val speed = 10.0f
    
    def f(x: Float): Float = { sin(x * speed).asInstanceOf[Float] * amplitude }

    val firsts = Buffers.newDirectIntBuffer(nofSignals)
    val counts = Buffers.newDirectIntBuffer(nofSignals)
    
    val vertices = {
        val buf = Buffers.newDirectFloatBuffer(nofSignals * nofSamples * 2)
        for (signal <- 0 until nofSignals) {
            val shift = signal * 0.1f
            for (sample <- 0 until nofSamples) {
                val x = sample / 1000.0f;
                buf.put(x)
                buf.put(f(x) + shift)
            }
            firsts.put(signal * nofSamples)
            counts.put(nofSamples)
        }
        buf.flip
        firsts.flip
        counts.flip
        Vertices(buf, GL_FLOAT, dim_2D, GL_LINE_STRIP)
    }

    val signalsVBO = new VertexBufferObject(vertices)
    
    def example =
        world {
            attach(signalsVBO)
            rotation(90.0f, 0.0f, 0.0f, 1.0f)
            translation(-5.0f, 0.0f, -8.0f)
            lineStrips(signalsVBO, firsts, counts)
            showFramesPerSecond
        }
    
}
