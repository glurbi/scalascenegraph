package scalascenegraph.examples

import java.awt.{Color => JColor }
import javax.swing._
import java.util._
import java.nio._
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

import scalascenegraph.ui.browser._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.builders._
import scalascenegraph.core._
import scalascenegraph.shaders._
import ExampleUtils._

object Example02 {
    def main(args: Array[String]) {
        val example02 = new Example02
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example02.example, animated = true).show
    }
}

class Example02 extends WorldBuilder {

    val ellipsoid1 = bufferedEllipsoid(20, 20, 0.5f, 0.5f, 1.0f)
    val ellipsoid2 = bufferedEllipsoid(20, 20, 2.0f, 2.0f, 0.3f)
    
    val ellipsoids =
        detached {
            attach(ellipsoid1.attributes)
            attach(ellipsoid2.attributes)
            group {
                polygonMode(GL_FRONT, GL_LINE)
                translation(-1.5f, 1.5f, -3.0f)
                rotation(0.0f, 0.0f, 0.0f, 0.0f, rotationHook(500.0f))
                useProgram(ShaderFactory.default)
                attach(ellipsoid1)
            }
            group {
                cullFace(Off)
                polygonMode(GL_FRONT_AND_BACK, GL_LINE)
                translation(1.5f, 1.5f, -3.0f)
                rotation(0.0f, 0.0f, 0.0f, 0.0f, rotationHook(2000.0f))
                useProgram(ShaderFactory.default)
                attach(ellipsoid1)
            }
            group {
                translation(0.0f, -1.5f, -5.0f)
                rotation(0.0f, 0.0f, 0.0f, 0.0f, rotationHook(1000.0f))
                group {
                    frontFace(GL_CW)
                    useProgram(ShaderFactory.default)
                    attach(ellipsoid2)
                }
                group {
                    color(JColor.black)
                    polygonMode(GL_FRONT, GL_LINE)
                    useProgram(ShaderFactory.default)
                    attach(ellipsoid2)
                }
            }
        }

    def example =
        world {
            cullFace(On)
            depthTest(On)
            color(JColor.white)
            attach(ShaderFactory.default)
            attach(ellipsoids)
            showFramesPerSecond
        }

}
