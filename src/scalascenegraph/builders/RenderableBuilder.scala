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
	
}