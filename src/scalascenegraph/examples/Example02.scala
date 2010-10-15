package scalascenegraph.examples

import java.awt.{Color => JColor }
import java.util._
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

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example02 extends Example with WorldBuilder {

	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 50.0f) % 360.0f
	}

	val perFaceColorCube =
		detached {
			val faceColors =
				Array(
					Color(1.0f, 0.0f, 0.0f),
					Color(0.0f, 1.0f, 0.0f),
					Color(0.0f, 0.0f, 1.0f),
					Color(1.0f, 0.0f, 1.0f),
					Color(1.0f, 1.0f, 0.0f),
					Color(0.0f, 1.0f, 1.0f))
			val colors = {
				val buf = Buffers.newDirectFloatBuffer(6 * 4 * 3)
				for (i <- 0 to 23) {
					buf.put(faceColors(i/4).rgb)
				}
				buf.flip
				Colors(buf, RGB)
			}
    		translation(-3.0f, 0.0f, 0.0f)
    		rotation(45.0f, 0.5f, 0.3f, 1.0f)
    		cube(colors)
		}

	val perVertexColorCube =
		detached {
			val colors = {
				val r = new Random
				val buf = Buffers.newDirectFloatBuffer(6 * 4 * 3)
				for (i <- 1 to 6 * 4) {
					buf.put(Color(r.nextFloat, r.nextFloat, r.nextFloat).rgb)
				}
				buf.flip
				Colors(buf, RGB)
			}
    		translation(-1.0f, 0.0f, 0.0f)
    		rotation(45.0f, 0.5f, 0.3f, 1.0f)
    		cube(colors)
		}

	
	val whiteCubes =
		detached {
    		depthTest(Off)
			cullFace(On)
			group {
				translation(-3.0f, 2.0f, 0.0f)
				polygonMode(GL_FRONT, GL_LINE)
				rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
				cube
			}
			group {
				translation(-1.0f, 2.0f, 0.0f)
				rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
				cube
			}
			group {
				translation(1.0f, 2.0f, 0.0f)
				cullFace(Off)
				polygonMode(GL_FRONT_AND_BACK, GL_LINE)
				rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
				cube
			}
			group {
				translation(3.0f, 2.0f, 0.0f)
				polygonMode(GL_FRONT, GL_LINE)
				frontFace(GL_CW)
				rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
				cube
			}
	    }

    val spheres =
    	detached {
			group {
				color(JColor.orange)
    			polygonMode(GL_FRONT_AND_BACK, GL_LINE)
    			translation(1.0f, 0.0f, 0.0f)
    			sphere(20, 0.8f)
			}
			group {
				cullFace(On)
				color(JColor.red)
    			polygonMode(GL_FRONT, GL_LINE)
    			translation(3.0f, 0.0f, 0.0f)
    			sphere(20, 0.8f)
			}
    	}

	val ellipsoids =
		detached {
			cullFace(On)
			group {
    			polygonMode(GL_FRONT, GL_LINE)
    			translation(-3.0f, -2.0f, 0.0f)
				ellipsoid(20, 20, 0.5f, 0.5f, 1.0f)
			}
			group {
				cullFace(Off)
    			polygonMode(GL_FRONT_AND_BACK, GL_LINE)
    			translation(3.0f, -2.0f, 0.0f)
				ellipsoid(20, 20, 0.5f, 0.5f, 1.0f)
			}
			group {
    			translation(0.0f, -2.0f, 0.0f)
				rotation(90.0f, 1.0f, 0.0f, 0.0f)
				group {
					frontFace(GL_CW)
					ellipsoid(20, 20, 2.0f, 2.0f, 0.3f)
				}
				group {
					color(JColor.black)
    				polygonMode(GL_FRONT, GL_LINE)
					ellipsoid(20, 20, 2.0f, 2.0f, 0.3f)
				}
			}
		}

	def example =
		world {
    		depthTest(On)
    		translation(0.0f, 0.0f, -5.0f)
			attach(perFaceColorCube)
			attach(perVertexColorCube)
			attach(whiteCubes)
			attach(spheres)
			attach(ellipsoids)
    	}

}
