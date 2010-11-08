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
import scalascenegraph.core.Utils._

class Example05 extends Example with WorldBuilder {
	
	val rotation = (r: Rotation, c: Context) => {
		r.angle = sin(c.elapsed / 180.0f) * 45.0f + 90.0f
		val x = sin(c.elapsed / 180.0f)
		val y = 1.0f
		val z = cos(c.elapsed / 180.0f)
		val rot = normalize(new Vector3D(x, y, z))
		val r.x = rot.x
		val r.y = rot.y
		val r.z = rot.z
	}
	
	def example =
		world {
    		depthTest(On)
			cullFace(On)
			light(On)
		    light(GL_LIGHT1, On)
		    light(GL_LIGHT1, new Position3D(0.0f, 0.0f, 0.0f))
		    light(GL_LIGHT1, GL_DIFFUSE, JColor.white)
			group {
				group {
					ambient(Intensity(0.3f, 0.3f, 0.3f, 1.0f))
					material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.pink)
					material(GL_FRONT, GL_SPECULAR, JColor.white)
					shininess(GL_FRONT, 128)
					translation(-2.0f, 0.0f, -4.0f)
					shadeModel(GL_FLAT)
					rotation(0.0f, 0.0f, 0.0f, 0.0f, rotation)
					attach(torus(30, 1.0f, 0.5f, normals = true))
				}
				group {
					light(GL_LIGHT1, GL_SPECULAR, JColor.white)
					material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.red)
					material(GL_FRONT, GL_SPECULAR, JColor.white)
					shininess(GL_FRONT, 128)
					translation(2.0f, 0.0f, -4.0f)
					rotation(0.0f, 0.0f, 0.0f, 0.0f, rotation)
					attach(torus(30, 1.0f, 0.5f, normals = true))
				}
			}
		}
	
}
