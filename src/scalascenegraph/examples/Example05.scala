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
	
	val rotation1 = (r: Rotation, c: Context) => animate(r, c, 360.0f)
	val rotation2 = (r: Rotation, c: Context) => animate(r, c, 540.0f)

	val mytorus = torus(30, 1.0f, 0.5f, normals = true)

	val lightPosition = new Position3D(5.0f, 5.0f, 0.0f)

	def animate(r: Rotation, c: Context, f: Float) {
		r.angle = sin(c.elapsed / f) * 45.0f + 90.0f
		val x = sin(c.elapsed / f)
		val y = 1.0f
		val z = cos(1.7*c.elapsed / f)
		val rot = normalize(new Vector3D(x, y, z))
		r.x = rot.x
		r.y = rot.y
		r.z = rot.z
	}
	
	def example =
		world {
    		depthTest(On)
			cullFace(On)
			light(On)
		    light(GL_LIGHT1, On)
		    light(GL_LIGHT1, lightPosition)
		    light(GL_LIGHT1, GL_DIFFUSE, JColor.white)
			group {
				translation(0.0f, 0.0f, -5.0f)
				group {
					group {
						ambient(Intensity(0.3f, 0.3f, 0.3f, 1.0f))
						material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.pink)
						material(GL_FRONT, GL_SPECULAR, JColor.white)
						shininess(GL_FRONT, 128)
						translation(-2.0f, 0.0f, 0.0f)
						shadeModel(GL_FLAT)
						rotation(0.0f, 0.0f, 0.0f, 0.0f, rotation1)
						attach(mytorus)
					}
					group {
						light(GL_LIGHT1, GL_SPECULAR, JColor.white)
						material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.red)
						material(GL_FRONT, GL_SPECULAR, JColor.white)
						shininess(GL_FRONT, 128)
						translation(2.0f, 0.0f, 0.0f)
						rotation(0.0f, 0.0f, 0.0f, 0.0f, rotation2)
						attach(mytorus)
					}
				}
				group {
					color(JColor.black)
					cullFace(Off)
					light(Off)
					multMatrix(shadowMatrix(new Plane3D(0.0f, 1.0f, 0.0f, 1.5f), lightPosition))
					group {
						translation(2.0f, 0.0f, 0.0f)
						rotation(0.0f, 0.0f, 0.0f, 0.0f, rotation2)
						attach(mytorus)
					}
					group {
						translation(-2.0f, 0.0f, 0.0f)
						rotation(0.0f, 0.0f, 0.0f, 0.0f, rotation1)
						attach(mytorus)
					}
				}
				group {
					polygonOffset(1.0f, 1.0f)
					quad(
						new Vertice3D(-20.0f, -1.5f, -20.0f),
						new Vertice3D(-20.0f, -1.5f, 20.0f),
						new Vertice3D(20.0f, -1.5f, 20.0f),
						new Vertice3D(20.0f, -1.5f, -20.0f))
				}
			}
		}
	
}
