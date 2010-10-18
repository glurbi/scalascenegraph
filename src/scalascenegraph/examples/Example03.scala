package scalascenegraph.examples

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

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example03 extends Example with WorldBuilder {
	
	def example =
		world {
			depthTest(On)
			cullFace(On)
    		translation(0.0f, 0.0f, -8.0f)
			group {
				color(JColor.black)
				frontFace(GL_CW)
				cone(20, 10, 0.6f, 1.0f);
			}
			group {
    			polygonMode(GL_FRONT, GL_LINE)
				cone(20, 10, 0.6f, 1.0f);
			}
			group {
    			translation(0.0f, 0.0f, -0.1f)
				color(JColor.green)
    			polygonMode(GL_FRONT_AND_BACK, GL_LINE)
				cullFace(Off)
				grid(6.0f, 4.0f, 150, 100)
			}
	    }

}
