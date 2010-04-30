package scalascenegraph.opengl

import java.awt.Frame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.media.opengl.awt.GLCanvas
import javax.media.opengl.awt.GLCanvas
import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GL

import scalascenegraph.ui.browser.Browser
import scalascenegraph.ui.browser.BrowserMouseListener

class OpenglBrowser extends Browser {

    val frame = new Frame
    frame.addWindowListener(new WindowAdapter {
        override def windowClosing(e: WindowEvent) {
            System.exit(0)
        }
    })
    val canvas = new GLCanvas
    val eventListener = new DefaultEventListener
    val mouseListener = new BrowserMouseListener
    canvas.addGLEventListener(eventListener);
    canvas.addMouseListener(mouseListener);
    canvas.addMouseWheelListener(mouseListener);
    canvas.addMouseMotionListener(mouseListener);
    frame.add(canvas);
    frame.setSize(300, 300);
    
    def show = {
        frame.setVisible(true)
    }
    
}
