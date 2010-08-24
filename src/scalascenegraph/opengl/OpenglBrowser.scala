package scalascenegraph.opengl

import java.util._
import java.io._
import java.awt._
import java.awt.event._
import javax.swing.SwingUtilities
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
	
	private val keyEventDispatcher = {
		val dispatcher = new BrowserKeyEventDispatcher
		KeyboardFocusManager.getCurrentKeyboardFocusManager.addKeyEventDispatcher(dispatcher)
		dispatcher
	}
	
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
    	    	exit
    	    }
    	})
    	canvas.addGLEventListener(this)
    	f.addMouseListener(mouseListener)
    	f.addMouseWheelListener(mouseListener)
    	f.addMouseMotionListener(mouseListener)
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
    	val renderer = new OpenglRenderer(gl2)
        context.renderer = renderer
        camera.prepare(context)
        world.prepare(context)
        
    	// make sure we don't draw more often than the screen is refreshed
    	gl2.setSwapInterval(1);
    }

    def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
    }

    def display(drawable: GLAutoDrawable) {
    	updateContext(drawable)
    	if (context.escapeKeyPressed) { exit }
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
        
        context.currentTime = System.currentTimeMillis
        context.elapsed = context.currentTime - context.creationTime
        context.totalFrameCount += 1
        context.frameCountLastSecond += 1
        val tmp = context.currentTime - context.frameCountLastSecondResetTime
        if (tmp >= 1000) {
        	context.frameRate = 1000 * context.frameCountLastSecond / tmp
        	context.frameCountLastSecond = 0
        	context.frameCountLastSecondResetTime = context.currentTime
        }
        
        context.upKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_UP)
        context.downKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_DOWN)
        context.rightKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_RIGHT)
        context.leftKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_LEFT)
        context.controlKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_CONTROL)
        context.shiftKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_SHIFT)
        context.spaceKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_SPACE)
        context.escapeKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_ESCAPE)
    }
    
    private def exit {
    	// if we invoke System.exit() on the opengl thread,
    	// the application freezes and does not stop.
    	SwingUtilities.invokeLater(new Runnable {
    		def run {
    			System.exit(0)
    		}
    	})
    }
    
}
