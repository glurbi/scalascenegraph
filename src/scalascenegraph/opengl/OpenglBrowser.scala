package scalascenegraph.opengl

import java.awt.Frame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.media.opengl.awt.GLCanvas
import javax.media.opengl.awt.GLCanvas
import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GL2

import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLEventListener

import scalascenegraph.ui.browser.Browser
import scalascenegraph.ui.browser.BrowserMouseListener
import scalascenegraph.core.World
import scalascenegraph.core.Camera

class OpenglBrowser(val world: World, val camera: Camera) extends Browser with GLEventListener {

    val frame = new Frame
    frame.addWindowListener(new WindowAdapter {
        override def windowClosing(e: WindowEvent) {
            System.exit(0)
        }
    })
    val canvas = new GLCanvas
    val mouseListener = new BrowserMouseListener
    canvas.addGLEventListener(this);
    canvas.addMouseListener(mouseListener);
    canvas.addMouseWheelListener(mouseListener);
    canvas.addMouseMotionListener(mouseListener);
    frame.add(canvas);
    frame.setSize(300, 300);
    
    def show = {
        frame.setVisible(true)
    }
    
    def init(drawable: GLAutoDrawable) {
        val gl2 = drawable.getGL.asInstanceOf[GL2]
        val renderer = new OpenglRenderer(gl2)
        renderer.clearColor(world.background);
    }

    def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {}

    def display(drawable: GLAutoDrawable) {
        val gl2 = drawable.getGL.asInstanceOf[GL2]
        val renderer = new OpenglRenderer(gl2)
        camera.render(renderer)
        world.render(renderer)
    }

    def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {}

    def dispose(drawable: GLAutoDrawable) {}
    
}
