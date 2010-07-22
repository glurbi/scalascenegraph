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
 
    def triangle(v1: Vertex, v2: Vertex, v3: Vertex) {
        gl2.glBegin(GL.GL_TRIANGLES)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glEnd
    }
    
    def triangle(v1: Vertex, c1: Color, v2: Vertex, c2: Color, v3: Vertex, c3: Color) {
    	val color = new Array[Float](4)
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, color, 0)
        gl2.glBegin(GL.GL_TRIANGLES)
        gl2.glColor3f(c1.red, c1.green , c1.blue)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glColor3f(c2.red, c2.green , c2.blue)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glColor3f(c3.red, c3.green , c3.blue)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glEnd
        gl2.glColor4f(color(0), color(1), color(2), color(3))
    }
    
    def quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex) {
        gl2.glBegin(GL2.GL_QUADS)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glVertex3f(v4.x, v4.y, v4.z)
        gl2.glEnd
    }
    
    def quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex, color: Color) {
    	val save = new Array[Float](4)
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, save, 0)
        gl2.glColor3f(color.red, color.green , color.blue)
    	quad(v1, v2, v3, v4)
        gl2.glColor4f(save(0), save(1), save(2), save(3))
    }
    
    def translate(x: Float, y: Float, z: Float) {
        gl2.glTranslatef(x, y, z)
    }
    
}
