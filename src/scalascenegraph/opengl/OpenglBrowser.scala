package scalascenegraph.opengl

import java.util._
import java.io._
import java.awt._
import java.awt.event._
import javax.media.opengl._
import javax.media.opengl.awt._
import com.jogamp.opengl.util._ 

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
	private val context = new Context 
	private val mouseListener = new BrowserMouseListener
	private val keyListener = new BrowserKeyboardListener
	
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
    	canvas.addGLEventListener(this)
    	canvas.addMouseListener(mouseListener)
    	canvas.addMouseWheelListener(mouseListener)
    	canvas.addMouseMotionListener(mouseListener)
    	canvas.addKeyListener(keyListener)
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
    	//drawable.setGL(new DebugGL2(drawable.getGL.asInstanceOf[GL2]))
    	val gl2 = drawable.getGL.asInstanceOf[GL2]
    	
    	// make sure we don't draw more often than the screen is refreshed
    	gl2.setSwapInterval(1);
    }

    def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    }

    def display(drawable: GLAutoDrawable) {
    	updateContext(drawable)
        camera.render(context)
        world.render(context)
    }

    def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {
    }

    def dispose(drawable: GLAutoDrawable) {
    }

    private def updateContext(drawable: GLAutoDrawable) {
        val gl2 = drawable.getGL.asInstanceOf[GL2]
        val renderer = new OpenglRenderer(gl2)
        context.renderer = renderer
        context.elapsed = System.currentTimeMillis - context.creationTime
        context.upKeyPressed = keyListener.isKeyPressed(KeyEvent.VK_UP)
        context.downKeyPressed = keyListener.isKeyPressed(KeyEvent.VK_DOWN)
        context.rightKeyPressed = keyListener.isKeyPressed(KeyEvent.VK_RIGHT)
        context.leftKeyPressed = keyListener.isKeyPressed(KeyEvent.VK_LEFT)
    }
    
}
