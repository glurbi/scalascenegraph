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

class Example01 extends Example with WorldBuilder {

	val triangles =
		detached {
    		translation(0.0f, 3.0f, 0.0f)
    		triangle(
    			Vertice3D(-1.0f, 0.0f, 0.0f),
    			Vertice3D(0.0f, 2.0f, 0.0f),
    			Vertice3D(1.0f, 0.0f, 0.0f),
				JColor.magenta)
			triangle(
    			Vertice3D(-4.0f, 0.0f, 0.0f),
    			Vertice3D(-3.0f, 2.0f, 0.0f),
    			Vertice3D(-2.0f, 0.0f, 0.0f),
				Color(1.0f, 0.0f, 0.0f),
				Color(0.0f, 1.0f, 0.0f),
				Color(0.0f, 0.0f, 1.0f))
			triangle(
    			Vertice3D(4.0f, 0.0f, 0.0f),
    			Vertice3D(3.0f, 2.0f, 0.0f),
    			Vertice3D(2.0f, 0.0f, 0.0f))
		}

	val quads =
		detached {
    		translation(0.0f, -5.0f, 0.0f)
			color(JColor.green)
			quad(
				Vertice3D(-1.0f, 0.0f, 0.0f),
				Vertice3D(1.0f, 0.0f, 0.0f),
				Vertice3D(1.0f, 2.0f, 0.0f),
				Vertice3D(-1.0f, 2.0f, 0.0f))
			quad(
				Vertice3D(-4.0f, 0.0f, 0.0f),
				Vertice3D(-2.0f, 0.0f, 0.0f),
				Vertice3D(-2.0f, 2.0f, 0.0f),
				Vertice3D(-4.0f, 2.0f, 0.0f),
				Color(1.0f, 1.0f, 1.0f))
			quad(
				Vertice3D(4.0f, 0.0f, 0.0f),
				Vertice3D(2.0f, 0.0f, 0.0f),
				Vertice3D(2.0f, 2.0f, 0.0f),
				Vertice3D(4.0f, 2.0f, 0.0f),
				Color(0.0f, 1.0f, 0.0f),
				Color(1.0f, 0.0f, 1.0f),
				Color(0.0f, 0.5f, 1.0f),
				Color(0.0f, 0.0f, 1.0f))
		}

	def pointSpiral(xtrans: Float, ytrans: Float, color: JColor) =
		detached {
    		translation(xtrans, ytrans, 0.0f)
			for (i <- 0 to 100) {
				val a = 0.01f * i
				val x = (a * cos(a*25)).asInstanceOf[Float]
				val y = (a * sin(a*25)).asInstanceOf[Float]
				group {
					pointSize(a*15)
					point(Vertice3D(x, y, 0.0f), color)
				}
			}
		}

	val points =
		detached {
			attach(pointSpiral(0.0f, 0.0f, JColor.blue.brighter))
			group {
				smooth(GL_POINT_SMOOTH)
				attach(pointSpiral(-3.0f, 0.0f, JColor.green.brighter))
			}
		}
	
	def example =
		world {
    		translation(0.0f, 0.0f, -8.0f)
			attach(points)
    		attach(triangles)
			attach(quads)
	    }

}
