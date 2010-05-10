package scalascenegraph.opengl

import java.awt._
import java.awt.event._
import javax.media.opengl._
import javax.media.opengl.awt._
import scalascenegraph.core._
import scalascenegraph.ui.browser._

class OpenglBrowser(world: World)
extends Browser(world)
with GLEventListener
{
    val canvas = new GLCanvas
    
    val frame = {
        val f = new Frame
        f.addWindowListener(new WindowAdapter {
    	    override def windowClosing(e: WindowEvent) {
    	        System.exit(0)
    	    }
    	})
    	val mouseListener = new BrowserMouseListener
    	canvas.addGLEventListener(this);
    	canvas.addMouseListener(mouseListener);
    	canvas.addMouseWheelListener(mouseListener);
    	canvas.addMouseMotionListener(mouseListener);
    	f.add(canvas);
    	f.setSize(300, 300);
    	f
    }
    
    def show {
        frame.setVisible(true)
    }
    
    def repaint {
        canvas.display
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
