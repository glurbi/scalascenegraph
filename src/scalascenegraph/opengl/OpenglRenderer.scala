package scalascenegraph.opengl

import javax.media.opengl.GL
import javax.media.opengl.GL2
import javax.media.opengl.fixedfunc.GLMatrixFunc

import scalascenegraph.core.Color
import scalascenegraph.core.Renderer

class OpenglRenderer(gl2: GL2) extends Renderer {

    def clearColor(color: Color) {
        gl2.glClearColor(color.red, color.green, color.blue, color.alpha)
    }
 
    def clear() {
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT)
    }
    
    def flush {
        gl2.glFlush
    }
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
      
    }
    
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glFrustum(left, right, bottom, top, near, far)
    }
    
}
