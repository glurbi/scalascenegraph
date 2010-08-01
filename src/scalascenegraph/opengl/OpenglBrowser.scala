package scalascenegraph.opengl

import java.util._
import java.io._
import java.awt._
import java.awt.event._
import javax.media.opengl._
import javax.media.opengl.awt._
import com.sun.opengl.util._ 

import scalascenegraph.core._
import scalascenegraph.ui.browser._


object OpenglBrowser {

	def getDefault(world: World, animated: Boolean = false): OpenglBrowser = {
        val browserConfig = this.getClass.getResourceAsStream("/browser.properties")
        val cameraConfig = this.getClass.getResourceAsStream("/camera.properties")
        val camera = Camera.get(cameraConfig)
        val browser = get(world, browserConfig, animated)
        browser.setCamera(camera)
        browser
	}
	
	def get(world: World, config: InputStream, animated: Boolean): OpenglBrowser = {
		try {
			val props = new Properties
			props.load(config)
			val width = props.getProperty("browser.width").toInt
			val height = props.getProperty("browser.width").toInt
			new OpenglBrowser(world, width, height, animated)
		} finally {
			config.close
		}
	}
	
}


class OpenglBrowser(world: World, width: Int, height: Int, animated: Boolean)
extends Browser(world)
with GLEventListener
{
	val context = new Context 
	
    val canvas = {
    	val profile = GLProfile.getDefault
        val capabilities = new GLCapabilities(profile)
        capabilities.setDoubleBuffered(true)
    	new GLCanvas(capabilities)
    }
    
    val frame = {
        val f = new Frame
        f.addWindowListener(new WindowAdapter {
    	    override def windowClosing(e: WindowEvent) {
    	        System.exit(0)
    	    }
    	})
    	val mouseListener = new BrowserMouseListener
    	canvas.addGLEventListener(this)
    	canvas.addMouseListener(mouseListener)
    	canvas.addMouseWheelListener(mouseListener)
    	canvas.addMouseMotionListener(mouseListener)
    	f.add(canvas);
    	f.setSize(width, height);
    	f
    }
    
    val animator = new Animator(canvas)
    
    def show {
        frame.setVisible(true)
        if (animated) {
        	animator.start
        }
    }
    
    def paint {
        canvas.display
    }
    
    def init(drawable: GLAutoDrawable) {
    	drawable.setGL(new DebugGL2(drawable.getGL.asInstanceOf[GL2]))
    	val gl2 = drawable.getGL.asInstanceOf[GL2]
    	gl2.setSwapInterval(1);
    }

    def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    }

    var c = 0
    
    def display(drawable: GLAutoDrawable) {
        val gl2 = drawable.getGL.asInstanceOf[GL2]
        val renderer = new OpenglRenderer(gl2)
        context.renderer = renderer
        camera.render(context)
        world.render(context)
        c += 1
        if (c%100 == 0) {
        	println(c)
        }
    }

    def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {
    }

    def dispose(drawable: GLAutoDrawable) {
    }
    
}
