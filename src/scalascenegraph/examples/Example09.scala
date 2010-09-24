package scalascenegraph.examples

import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
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

class Example09 extends Example with WorldBuilder {

	val myvertexshadersource =
	"""
	uniform float r;
	uniform vec4 color;
    const vec3 lightPos = vec3(-6.0, -6.0, 0.0);
	void main (void)
    {
	    vec4 v = gl_Vertex;
	    v.z = v.z + sin(r * v.x) * 0.25;
        gl_Position = gl_ModelViewProjectionMatrix * v;
        
        vec3 N = gl_NormalMatrix * normalize(v.xyz);
        vec4 V = gl_ModelViewMatrix * gl_Vertex;
        vec3 L = normalize(lightPos - V.xyz);
        float NdotL = dot(N, L);
        vec4 ambient = vec4(0.2, 0.2, 0.2, 1.0);
        vec4 diffuse = vec4(max(0.0, NdotL));
        gl_FrontColor = color * (ambient + diffuse);
    }
	"""
		
	val myfragmentshadersource =
	"""
	void main (void)
    {
        gl_FragColor = gl_Color;
    }
	"""

	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 50.0f) % 360.0f
	}
	
	val uniformHook = (u: UniformState, c: Context) => {
		u.value = (sin(c.elapsed / 1000.0f) * 10.0f).asInstanceOf[Float]
	}
		
	def example =
		world {
			cullFace(On)
    		depthTest(On)
			shader("myvertexshader", GL_VERTEX_SHADER, myvertexshadersource)
			shader("myfragmentshader", GL_FRAGMENT_SHADER, myfragmentshadersource)
			program("myprogram", "myvertexshader", "myfragmentshader")
			uniform("myprogram", "r")
			uniform("myprogram", "color")
			useProgram("myprogram")
			group {
				setUniform("color", JColor.orange)
				setUniform("r", 0.0f, uniformHook)
				translation(0.0f, 0.0f, -4.0f)
				rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
				box(4.0f, 2.0f, 1.0f, 40, 20, 10)
			}
			showFramesPerSecond
		}
	
}