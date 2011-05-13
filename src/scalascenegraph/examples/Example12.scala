package scalascenegraph.examples

import scala.math._
import java.awt.{Color => JColor }
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core.Predefs._
import scalascenegraph.builders._
import scalascenegraph.core._
import scalascenegraph.shaders._

class Example12 extends Example with WorldBuilder {

    val mytorus = bufferedTorus(30, 1.0f, 0.5f)

    def example =
        world {
            attach(ShaderFactory.default)
            translation(0.0f, 0.0f, -5.0f)
            useProgram(ShaderFactory.default)
            attach(mytorus.attributes)
            attach(mytorus)
            showFramesPerSecond
        }

}
