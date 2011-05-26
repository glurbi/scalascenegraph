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

object Example13 extends WorldBuilder {

    val mysphere = bufferedSphere(15, 1.0f)

    def example =
        world {
            depthTest(On)
            cullFace(On)
            translation(0.0f, 0.0f, -5.0f)
            attach(mysphere.attributes)
            group {
                translation(0.0f, 1.5f, 0.0f)
                rotation(0.0f, 0.0f, 0.0f, 0.0f, rotationHook(1000.0f))
                group {
                    polygonOffset(1.0f, 1.0f)
                    useProgram(ShaderFactory.vorientcolor)
                    attach(mysphere)
                }
                group {
                    polygonMode(GL_FRONT, GL_LINE)
                    color(JColor.gray)
                    useProgram(ShaderFactory.default)
                    attach(mysphere)
                }
            }
            group {
                translation(0.0f, -1.5f, 0.0f)
                rotation(0.0f, 0.0f, 0.0f, 0.0f, rotationHook(1000.0f))
                group {
                    polygonOffset(1.0f, 1.0f)
                    useProgram(ShaderFactory.flatvcolor)
                    attach(mysphere)
                }
                group {
                    polygonMode(GL_FRONT, GL_LINE)
                    color(JColor.gray)
                    useProgram(ShaderFactory.default)
                    attach(mysphere)
                }
            }
            showFramesPerSecond
        }

    def main(args: Array[String]) {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example, animated = true).show
    }

}
