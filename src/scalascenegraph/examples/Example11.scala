package scalascenegraph.examples

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

import scalascenegraph.core._
import scalascenegraph.core.Utils._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example11 extends Example with WorldBuilder {
    
    val sandTexture = new Texture(getClass.getResourceAsStream("/scalascenegraph/examples/sand.jpg"))
    val waveVertexShaderSource = getStreamAsString(getClass.getResourceAsStream("/scalascenegraph/examples/Example11.vert"))
    val fragmentShaderSource = getStreamAsString(getClass.getResourceAsStream("/scalascenegraph/examples/Example11.frag"))
    
    val uniformHook = (u: UniformState, c: Context) => {
        // time elapsed in seconds
        u.value = c.elapsed / 1000.0f
    }
    
    val waveVertexShader = new Shader(GL_VERTEX_SHADER, waveVertexShaderSource)
    val fragmentShader = new Shader(GL_FRAGMENT_SHADER, fragmentShaderSource)
    val waveProgram = new Program(List(waveVertexShader, fragmentShader))
    val tUniform = new Uniform(waveProgram, "t")
    val textureUniform = new Uniform(waveProgram, "texture")
    
    def example =
        world {
            attach(sandTexture)
            blending(On)
            cullFace(On)
            depthTest(On)
            group {
                color(Color(1.0f, 1.0f, 1.0f, 0.0f))
                translation(0.0f, 0.0f, -4.0f)
                attach(waveVertexShader)
                attach(fragmentShader)
                attach(waveProgram)
                attach(tUniform)
                attach(textureUniform)
                useProgram(waveProgram)
                setUniform(tUniform, 0.0f, uniformHook)
                setUniform(textureUniform, 0)
                grid(4.0f, 4.0f, 100, 100, sandTexture)
            }
        }
    
}
