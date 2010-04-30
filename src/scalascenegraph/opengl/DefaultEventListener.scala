package scalascenegraph.opengl

import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLEventListener

class DefaultEventListener extends GLEventListener {
  
    def init(drawable: GLAutoDrawable) {}

    def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {}

    def display(drawable: GLAutoDrawable) {}

    def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {}

    def dispose(drawable: GLAutoDrawable) {}

}
