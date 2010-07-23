package scalascenegraph.opengl

import javax.media.opengl._
import javax.media.opengl.fixedfunc._

import scalascenegraph.core._

class OpenglRenderer(gl2: GL2) extends Renderer {

    def color(color: Color) {
        gl2.glColor3f(color.red, color.green , color.blue)
    }	
	
    def clearColor(color: Color) {
        gl2.glClearColor(color.red, color.green, color.blue, color.alpha)
    }
 
    def clear() {
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW)
        gl2.glLoadIdentity
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT)
    }
    
    def flush {
        gl2.glFlush
    }
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glOrtho(left, right, bottom, top, near, far)
    }
    
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glFrustum(left, right, bottom, top, near, far)
    }
 
    def triangle(v: Array[Float]) {
        gl2.glBegin(GL.GL_TRIANGLES)
        gl2.glVertex3f(v(0), v(1), v(2))
        gl2.glVertex3f(v(3), v(4), v(5))
        gl2.glVertex3f(v(6), v(7), v(8))
        gl2.glEnd
    }
    
    def triangle(v: Array[Float], c: Array[Float]) {
    	val color = new Array[Float](4)
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, color, 0)
        gl2.glBegin(GL.GL_TRIANGLES)
        gl2.glColor3f(c(0), c(1), c(2))
        gl2.glVertex3f(v(0), v(1), v(2))
        gl2.glColor3f(c(3), c(4), c(5))
        gl2.glVertex3f(v(3), v(4), v(5))
        gl2.glColor3f(c(6), c(7), c(8))
        gl2.glVertex3f(v(6), v(7), v(8))
        gl2.glEnd
        gl2.glColor4f(color(0), color(1), color(2), color(3))
    }
    
    def quad(vertices: Array[Float]) {
    	val v = vertices
        gl2.glBegin(GL2.GL_QUADS)
        gl2.glVertex3f(v(0), v(1), v(2))
        gl2.glVertex3f(v(3), v(4), v(5))
        gl2.glVertex3f(v(6), v(7), v(8))
        gl2.glVertex3f(v(9), v(10), v(11))
        gl2.glEnd
    }
    
    def quad(vertices: Array[Float], colors: Array[Float]) {
    	val v = vertices
    	val c = colors
    	val save = new Array[Float](4)
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, save, 0)
        gl2.glBegin(GL2.GL_QUADS)
        gl2.glColor3f(c(0), c(1), c(2))
        gl2.glVertex3f(v(0), v(1), v(2))
        gl2.glColor3f(c(3), c(4), c(5))
        gl2.glVertex3f(v(3), v(4), v(5))
        gl2.glColor3f(c(6), c(7), c(8))
        gl2.glVertex3f(v(6), v(7), v(8))
        gl2.glColor3f(c(9), c(10), c(11))
        gl2.glVertex3f(v(9), v(10), v(11))
        gl2.glEnd
        gl2.glColor4f(save(0), save(1), save(2), save(3))
    }
    
    def translate(x: Float, y: Float, z: Float) {
        gl2.glTranslatef(x, y, z)
    }
    
}
