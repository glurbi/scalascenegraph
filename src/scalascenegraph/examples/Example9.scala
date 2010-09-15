package scalascenegraph.examples

import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example9 extends Example with WorldBuilder {

	val myvertexshadersource =
	"""
	uniform float r;
	void main (void)
    {
	    vec4 v = gl_Vertex;
	    v.z = v.z + sin(r * v.x) * 0.25;
        gl_Position = gl_ModelViewProjectionMatrix * v;
    }
	"""
		
	val myfragmentshadersource =
	"""
	uniform vec4 color;
	void main (void)
    {
        gl_FragColor = color;
    }
	"""

	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 50.0f) % 360.0f
	}
	
	val uniformHook = (u: UniformState, c: Context) => {
		u.value = (Math.sin(c.elapsed / 1000.0f) * 10.0f).asInstanceOf[Float]
	}
		
	def example =
		world {
			cullFace(On)
			shader("myvertexshader", VertexShader, myvertexshadersource)
			shader("myfragmentshader", FragmentShader, myfragmentshadersource)
			program("myprogram", "myvertexshader", "myfragmentshader")
			uniform("myprogram", "r")
			uniform("myprogram", "color")
			useProgram("myprogram")
			group {
				setUniform("color", JColor.orange)
				setUniform("r", 0.0f, uniformHook)
				translation(0.0f, 0.0f, -4.0f)
				polygonMode(Front, Line)
				rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
				box(4.0f, 2.0f, 1.0f, 40, 20, 10)
			}
		}
	
}
