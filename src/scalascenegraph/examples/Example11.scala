package scalascenegraph.examples

import java.nio._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
import com.jogamp.common.nio._
import scala.math._
import scala.collection.mutable._
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

class Example11 extends Example with WorldBuilder {
	
	/**
	 * inspired from http://http.developer.nvidia.com/GPUGems/gpugems_ch01.html
	 */
	val waveVertexShader =
	"""
	struct wave {
		float A;
		vec2 dir;
	    float w;
		float phi;
	};
	const wave wave0 = wave(0.1, vec2(1.0, 0.0), 10.0, 5.0);
	const wave wave1 = wave(0.05, vec2(1.0, 1.0), 8.0, 10.0);
	const wave wave2 = wave(0.02, vec2(-1.0, -1.0), 20.0, 20.0);
	uniform float t;
	void main (void)
    {
	    vec4 v = gl_Vertex;
	    vec2 xy = vec2(v.x, v.y);
		wave waves[3];
		waves[0] = wave0;
		waves[1] = wave1;
		waves[2] = wave2;
	    
	    for (int i = 0; i < 3; i++) {
			float A = waves[i].A;
			vec2 dir = waves[i].dir;
			float w = waves[i].w;
			float phi = waves[i].phi;
		    v.z += A * sin(dot(dir, xy)*w + phi*t);
	    }
	    
        gl_Position = gl_ModelViewProjectionMatrix * v;
        gl_FrontColor = gl_Color;
    }
	"""
	
	val uniformHook = (u: UniformState, c: Context) => {
		// time elapsed in seconds
		u.value = c.elapsed / 1000.0f
	}
	
	
	def example =
		world {
			cullFace(On)
    		depthTest(On)
			shader("waveVertexShader", GL_VERTEX_SHADER, waveVertexShader)
			program("waveProgram", "waveVertexShader")
			uniform("waveProgram", "t")
			useProgram("waveProgram")
			setUniform("t", 0.0f, uniformHook)
			translation(0.0f, 0.0f, -4.0f)
			polygonMode(GL_FRONT, GL_LINE)
			grid(3.0f, 5.0f, 60, 100)
		}
	
}
