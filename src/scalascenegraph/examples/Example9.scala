package scalascenegraph.examples

import java.awt._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example9 extends Example with WorldBuilder {

	val myshadersource =
	"""
	void main (void)
    {
        gl_FragColor = vec4 (0.0, 1.0, 0.0, 1.0);
    }
	"""

	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 20.0f) % 360.0f
	}
		
	def example =
		world {
			cullFace(On)
			shader("myshader", FragmentShader, myshadersource)
			program("myprogram", "myshader")
			useProgram("myprogram")
			group {
				translation(0.0f, 0.0f, -3.0f)
				polygonMode(Front, Line)
				rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
				cube
			}
		}
	
}
