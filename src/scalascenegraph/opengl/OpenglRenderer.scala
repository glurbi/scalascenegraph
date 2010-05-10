package scalascenegraph.opengl

import javax.media.opengl._
import javax.media.opengl.fixedfunc._

import scalascenegraph.core._

class OpenglRenderer(gl2: GL2) extends Renderer {

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
    
    def quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex) {
        gl2.glBegin(GL2.GL_QUADS)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glVertex3f(v4.x, v4.y, v4.z)
        gl2.glEnd
    }
    
    def translate(x: Float, y: Float, z: Float) {
        gl2.glTranslatef(x, y, z)
    }
    
}
