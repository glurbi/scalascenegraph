package scalascenegraph.examples

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

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example06 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 30.0f) % 360.0f
	}
	
	def example =
		world {
    		depthTest(On)
			fog(JColor.blue, Exp(0.1f))
			quad(
				Vertice3D(-10.0f, -10.0f, -9.9f),
				Vertice3D(10.0f, -10.0f, -9.9f),
				Vertice3D(10.0f, 10.0f, -9.9f),
				Vertice3D(-10.0f, 10.0f, -9.9f))
			group {
				translation(0.0f, -1.0f, -5.0f)
				rotation(-90.0f, 1.0f, 0.0f, 0.0f)
				checkerBoard(20, 20, JColor.white, JColor.black)
			}
			group {
				light(On)
				light(GL_LIGHT0, On)
				light(GL_LIGHT0, Position(0.0f, 0.0f, 0.0f))
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
