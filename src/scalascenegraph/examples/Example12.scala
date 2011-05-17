package scalascenegraph.examples

import scala.math._
import javax.swing._
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

import scalascenegraph.ui.browser._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.builders._
import scalascenegraph.core._
import scalascenegraph.shaders._
import ExampleUtils._

object Example12 extends WorldBuilder {

    val mytorus = bufferedTorus(30, 1.0f, 0.5f)

    def example =
        world {
            depthTest(On)
            cullFace(On)
            attach(ShaderFactory.default)
            translation(0.0f, 0.0f, -5.0f)
            rotation(0.0f, 0.0f, 0.0f, 0.0f, rotationHook(1000.0f))
            attach(mytorus.attributes)
            group {
                polygonOffset(1.0f, 1.0f)
                color(JColor.green)
                useProgram(ShaderFactory.default)
                attach(mytorus)
            }
            group {
                polygonMode(GL_FRONT, GL_LINE)
                color(JColor.gray)
                useProgram(ShaderFactory.default)
                attach(mytorus)
            }
            showFramesPerSecond
        }

    def main(args: Array[String]) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(example, true).show
    }
}
