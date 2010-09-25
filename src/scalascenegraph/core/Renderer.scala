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
 
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice) {
        gl.glBegin(GL_TRIANGLES)
        gl.glVertex3f(v1.x, v1.y, v1.z)
        gl.glVertex3f(v2.x, v2.y, v2.z)
        gl.glVertex3f(v3.x, v3.y, v3.z)
        gl.glEnd
    }
    
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice,
    		     c1: Color, c2: Color, c3: Color)
    {
    	val color = new Array[Float](4)
    	gl.glGetFloatv(GL_CURRENT_COLOR, color, 0)
        gl.glBegin(GL_TRIANGLES)
        gl.glColor4f(c1.r, c1.g, c1.b, c1.a)
        gl.glVertex3f(v1.x, v1.y, v1.z)
        gl.glColor4f(c2.r, c2.g, c2.b, c2.a)
        gl.glVertex3f(v2.x, v2.y, v2.z)
        gl.glColor4f(c3.r, c3.g, c3.b, c3.a)
        gl.glVertex3f(v3.x, v3.y, v3.z)
        gl.glEnd
        gl.glColor4f(color(0), color(1), color(2), color(3))
    }
    
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
