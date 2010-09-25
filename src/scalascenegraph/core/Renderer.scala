package scalascenegraph.core

import java.nio._
import java.awt.image._
import javax.media.opengl._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import com.jogamp.opengl.util._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class Renderer(val gl: GL3bc) {
 
	def lineStrip(vbo: VertexBufferObject) {
		gl.glEnableClientState(GL_VERTEX_ARRAY)
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.id)
		gl.glVertexPointer(2, GL_FLOAT, 0, 0);
        gl.glDrawArrays(GL_LINE_STRIP, 0, vbo.count)
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
		gl.glDisableClientState(GL_VERTEX_ARRAY)
	}

	def lineStrips(vbo: VertexBufferObject, firsts: IntBuffer, counts: IntBuffer) {
		gl.glEnableClientState(GL_VERTEX_ARRAY)
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.id)
		gl.glVertexPointer(2, GL_FLOAT, 0, 0);
        gl.glMultiDrawArrays(GL_LINE_STRIP, firsts, counts, firsts.capacity)
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
		gl.glDisableClientState(GL_VERTEX_ARRAY)
	}
	
}
