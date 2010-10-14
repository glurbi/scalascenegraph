package scalascenegraph.examples

import java.util._
import com.jogamp.common.nio._

import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example02 extends Example with WorldBuilder {

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
					buf.put(faceColors(i/4).RGB)
				}
				buf.flip
				Colors(buf, RGB)
			}
    		blending(Off)
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
					buf.put(Color(r.nextFloat, r.nextFloat, r.nextFloat).RGB)
				}
				buf.flip
				Colors(buf, RGB)
			}
    		blending(Off)
    		translation(-1.0f, 0.0f, 0.0f)
    		rotation(45.0f, 0.5f, 0.3f, 1.0f)
    		cube(colors)
		}

	def example =
		world {
    		depthTest(On)
    		translation(0.0f, 0.0f, -5.0f)
			attach(perFaceColorCube)
			attach(perVertexColorCube)
    	}
	
}
