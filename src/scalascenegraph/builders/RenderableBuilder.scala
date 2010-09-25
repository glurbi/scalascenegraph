package scalascenegraph.builders

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

trait RenderableBuilder {

	def createQuadsRenderable(vertices: Vertices): Renderable = {
		new Renderable {
			def render(context: Context) {
				import context.gl
				gl.glEnableClientState(GL_VERTEX_ARRAY);
				gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
				gl.glDrawArrays(GL_QUADS, 0, vertices.count);
				gl.glDisableClientState(GL_VERTEX_ARRAY);
			}
		}
	}
	
	def createQuadsColorsRenderable(vertices: Vertices, colors: Colors): Renderable = {
		new Renderable {
			def render(context: Context) {
				import context.gl
				// FIXME: should not assume a color has alpha always
		    	val color = new Array[Float](4)
		    	gl.glGetFloatv(GL_CURRENT_COLOR, color, 0)
		        gl.glEnableClientState(GL_VERTEX_ARRAY)
		        gl.glEnableClientState(GL_COLOR_ARRAY)
		        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
		        gl.glColorPointer(4, GL_FLOAT, 0, colors.floatBuffer)
		        gl.glDrawArrays(GL_QUADS, 0, vertices.count)
		        gl.glDisableClientState(GL_VERTEX_ARRAY)
		        gl.glDisableClientState(GL_COLOR_ARRAY)
		        gl.glColor4f(color(0), color(1), color(2), color(3))
			}
		}
	}
	
}