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

object Example01 {
    def main(args: Array[String]) {
        val example01 = new Example01
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example01.example, animated = true).show
    }
}

class Example01 extends WorldBuilder {

    val rightTriangle = bufferedTriangle(
                new Vertice3D(4.0f, 0.0f, 0.0f),
                new Vertice3D(3.0f, 2.0f, 0.0f),
                new Vertice3D(2.0f, 0.0f, 0.0f))
                
    val middleTriangle = bufferedTriangle(
                new Vertice3D(-1.0f, 0.0f, 0.0f),
                new Vertice3D(0.0f, 2.0f, 0.0f),
                new Vertice3D(1.0f, 0.0f, 0.0f))
                
    val leftTriangle = bufferedTriangle(
                new Vertice3D(-4.0f, 0.0f, 0.0f),
                new Vertice3D(-3.0f, 2.0f, 0.0f),
                new Vertice3D(-2.0f, 0.0f, 0.0f),
                Color(1.0f, 0.0f, 0.0f),
                Color(0.0f, 1.0f, 0.0f),
                Color(0.0f, 0.0f, 1.0f))

    val rightQuad = bufferedQuad(
                new Vertice3D(4.0f, 0.0f, 0.0f),
                new Vertice3D(2.0f, 0.0f, 0.0f),
                new Vertice3D(2.0f, 2.0f, 0.0f),
                new Vertice3D(4.0f, 2.0f, 0.0f),
                Color(0.0f, 1.0f, 0.0f),
                Color(1.0f, 0.0f, 1.0f),
                Color(0.0f, 0.5f, 1.0f),
                Color(0.0f, 0.0f, 1.0f))

    val middleQuad = bufferedQuad(
                new Vertice3D(-1.0f, 0.0f, 0.0f),
                new Vertice3D(1.0f, 0.0f, 0.0f),
                new Vertice3D(1.0f, 2.0f, 0.0f),
                new Vertice3D(-1.0f, 2.0f, 0.0f))
                
    val leftQuad = bufferedQuad(
                new Vertice3D(-4.0f, 0.0f, 0.0f),
                new Vertice3D(-2.0f, 0.0f, 0.0f),
                new Vertice3D(-2.0f, 2.0f, 0.0f),
                new Vertice3D(-4.0f, 2.0f, 0.0f))
                
    val triangles =
        detached {
            translation(0.0f, 3.0f, 0.0f)
            group {
                color(JColor.magenta)
                useProgram(ShaderFactory.default)
                attach(middleTriangle)
            }
            group {
                useProgram(ShaderFactory.vcolor)
                attach(leftTriangle)
            }
            group {
                useProgram(ShaderFactory.default)
                attach(rightTriangle)
            }
        }

    val quads =
        detached {
            translation(0.0f, -5.0f, 0.0f)
            color(JColor.green)
            group {
                color(JColor.white)
                useProgram(ShaderFactory.default)
                attach(leftQuad)
            }
            group {
                useProgram(ShaderFactory.default)
                attach(middleQuad)
            }
            group {
                useProgram(ShaderFactory.vcolor)
                attach(rightQuad)
            }
        }

    def pointSpiral(xtrans: Float, ytrans: Float, color: JColor) =
        detached {
            translation(xtrans, ytrans, 0.0f)
            for (i <- 0 to 100) {
                val a = 0.01f * i + 0.1f
                val x = (a * cos(a*25)).asInstanceOf[Float]
                val y = (a * sin(a*25)).asInstanceOf[Float]
                group {
                    pointSize(a*10)
                    useProgram(ShaderFactory.vcolor)
                    attach(bufferedPoint(new Vertice3D(x, y, 0.0f), color))
                }
            }
        }

    val points =
        detached {
            translation(0.0f, 0.0f, 0.0f)
            attach(pointSpiral(0.0f, 0.0f, JColor.blue.brighter))
            group {
                smooth(GL_POINT_SMOOTH)
                attach(pointSpiral(-3.0f, 0.0f, JColor.green.brighter))
            }
        }

    val lines =
        detached {
            translation(2.0f, 0.0f, 0.0f)
            group {
                for (i <- 1 to 5) {
                    val x = 1.0f * i / 5
                    group {
                        lineWidth(i+1)
                        line(new Vertice3D(x, -1.0f, 0.0f), new Vertice3D(x, 1.0f, 0.0f))
                    }
                }
            }
            group {
                color(JColor.red.brighter)
                smooth(GL_LINE_SMOOTH)
                for (i <- 1 to 5) {
                    group {
                        lineStipple(i, 0x5505)
                        line(new Vertice3D(0.0f + i / 5.0f, -1.0f, 0.0f),
                             new Vertice3D(2.0f + i / 5.0f, 1.0f, 0.0f))
                    }
                }
            }
        }

    def example =
        world {
            attach(ShaderFactory.default)
            attach(ShaderFactory.vcolor)
            translation(0.0f, 0.0f, -8.0f)
            attach(points)
            attach(triangles)
            attach(quads)
            attach(lines)
            showFramesPerSecond
        }

}
