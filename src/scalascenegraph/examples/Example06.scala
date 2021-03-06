package scalascenegraph.examples

import javax.swing._
import java.awt.{Color => JColor }
import scala.math._
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

object Example06 {
    def main(args: Array[String]) {
        val example06 = new Example06
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example06.example, animated = true).show
    }
}

class Example06 extends WorldBuilder {
    
    val angleHook = (r: Rotation, c: Context) => {
        r.angle = (c.elapsed / 30.0f) % 360.0f
    }
    
    def example =
        world {
            depthTest(On)
            fog(JColor.blue, Exp(0.1f))
            quad(
                new Vertice3D(-10.0f, -10.0f, -9.9f),
                new Vertice3D(10.0f, -10.0f, -9.9f),
                new Vertice3D(10.0f, 10.0f, -9.9f),
                new Vertice3D(-10.0f, 10.0f, -9.9f))
            group {
                translation(0.0f, -1.0f, -5.0f)
                rotation(-90.0f, 1.0f, 0.0f, 0.0f)
                checkerBoard(20, 20, JColor.white, JColor.black)
            }
            group {
                light(On)
                light(GL_LIGHT0, On)
                light(GL_LIGHT0, new Position3D(0.0f, 0.0f, 0.0f))
                light(GL_LIGHT0, GL_DIFFUSE, JColor.white)
                material(GL_FRONT, GL_DIFFUSE, JColor.orange)
                group {
                    translation(-1.5f, 0.5f, -3.5f)
                    rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
                    attach(torus(30, 1.0f, 0.5f, normals = true))
                }
                group {
                    translation(1.0f, 0.5f, -6.0f)
                    rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
                    attach(torus(30, 1.0f, 0.5f, normals = true))
                }
                group {
                    translation(5.0f, 0.5f, -8.5f)
                    rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
                    attach(torus(30, 1.0f, 0.5f, normals = true))
                }
            }
        }
    
}
