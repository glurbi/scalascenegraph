package scalascenegraph.ui.browser

import java.util._
import java.io._
import java.awt._
import java.awt.event._
import javax.swing._
import javax.media.opengl._
import javax.media.opengl.glu._
import javax.media.opengl.awt._
import com.jogamp.opengl.util._ 

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.ui.browser._

object Browser {

    def getDefault(world: World, animated: Boolean = false): Browser = {
        val browserConfig = this.getClass.getResourceAsStream("/browser.properties")
        val cameraConfig = this.getClass.getResourceAsStream("/camera.properties")
        val camera = Camera.get(cameraConfig)
        val browser = get(world, browserConfig, animated)
        browser.setCamera(camera)
        browser
    }
    
    def get(world: World, config: InputStream, animated: Boolean): Browser = {
        try {
            val props = new Properties
            props.load(config)
            val width = props.getProperty("browser.width").toInt
            val height = props.getProperty("browser.width").toInt
            new Browser(world, width, height, animated)
        } finally {
            config.close
        }
    }
    
}


class Browser(val world: World, width: Int, height: Int, animated: Boolean)
extends GLEventListener
{
    private var camera: Camera = _
    private var defaultCamera: Camera = _
    private var cameraHandler = new CameraHandler
    private var clippingVolumeHandler = new ClippingVolumeHandler

    def getCamera = camera
    def setCamera(camera: Camera) {
        this.camera = camera
        defaultCamera = camera
        import camera.clippingVolume._
        clippingVolumeHandler.defaultClippingVolume = new ClippingVolume(left, right, bottom, top, near, far)
        paint
    }

    private val context = new Context 
    private val mouseListener = new BrowserMouseListener
    private val keyEventDispatcher = {
        val dispatcher = new BrowserKeyEventDispatcher
        KeyboardFocusManager.getCurrentKeyboardFocusManager.addKeyEventDispatcher(dispatcher)
        dispatcher
    }
    
    val capabilities = {
        val profile = GLProfile.get(GLProfile.GL3bc)
        val c = new GLCapabilities(profile)
        c.setDoubleBuffered(true)
        c
    }
    
    val canvas = {
        val c = new GLJPanel(capabilities) // slower but no flicker
        //new GLCanvas(capabilities) // faster but can flicker
        c.addGLEventListener(this)
        c
    }

    val menuBar = {
        val mb = new JMenuBar
        val fileMenu = new JMenu("File")
        val exitMenuItem = new JMenuItem("Exit")
        exitMenuItem.addActionListener(new ActionListener() {
            def actionPerformed(e: ActionEvent) {
                exit
            }
        });        
        fileMenu.add(exitMenuItem)
        mb.add(fileMenu)
        val viewMenu = new JMenu("View")
        val fullScreenMenuItem = new JMenuItem("Full Screen")
        fullScreenMenuItem.addActionListener(new ActionListener() {
            def actionPerformed(e: ActionEvent) {
                fullScreenMode
            }
        })
        viewMenu.add(fullScreenMenuItem)
        mb.add(viewMenu)
        val helpMenu = new JMenu("Help")
        val aboutMenuItem = new JMenuItem("About")
        helpMenu.add(aboutMenuItem)
        mb.add(helpMenu)
        mb
    }
    
    val frame: Frame = {
        val f = new JFrame
        f.addWindowListener(new WindowAdapter {
            override def windowClosing(e: WindowEvent) {
                exit
            }
        })
        //f.addMouseListener(mouseListener)
        //f.addMouseWheelListener(mouseListener)
        //f.addMouseMotionListener(mouseListener)
        f.add(canvas)
        f.setSize(width, height)
        f.setJMenuBar(menuBar)
        f
    }

    val animator = new Animator(canvas)
    
    private var fullscreen = false
    
    private def fullScreenMode {
        if (!fullscreen) {
            val graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment
            val graphicsDevice = graphicsEnvironment.getDefaultScreenDevice
            graphicsDevice.setFullScreenWindow(frame)
            fullscreen = true
        }
    }

    private def windowedMode {
        if (fullscreen) {
            val graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment
            val graphicsDevice = graphicsEnvironment.getDefaultScreenDevice
            graphicsDevice.setFullScreenWindow(null)
            fullscreen = false
        }
    }

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
        //drawable.setGL(new DebugGL3bc(drawable.getGL.getGL3bc))
        val gl = drawable.getGL.getGL3bc
        context.gl = gl
        context.glu = GLU.createGLU
        val preparator = new NodeVisitor(context) {
            def visit(group: Group) {
                group.resources.foreach { resource => resource.prepare(context) }
                group.children.foreach { child => child.accept(this) }
            }
        }
        world.accept(preparator)
        
        // make sure we don't draw more often than the screen is refreshed
        //gl.setSwapInterval(1);
        
        // draw as fast as possible
        gl.setSwapInterval(0);
    }

    def reshape(drawable: GLAutoDrawable, x: Int, y: Int, width: Int, height: Int) {
        context.width = width
        context.height = height
        val gl = drawable.getGL.getGL3bc
        gl.glViewport(0,0, width, height)
    }

    def display(drawable: GLAutoDrawable) {
        updateContext(drawable)
        if (context.escapeKeyPressed) { exit }
        if (context.pressedKeys.contains(KeyEvent.VK_F11)) { fullScreenMode }
        if (context.pressedKeys.contains(KeyEvent.VK_F12)) { windowedMode }

        // TODO: create ProjectionHandler class
        if (context.pressedKeys.contains(KeyEvent.VK_P)) {
            camera = defaultCamera.projectionType match {
                case Perspective => Camera.get(Parallel, camera.clippingVolume)
                case Parallel => Camera.get(Perspective, camera.clippingVolume)
            }
        } else {
            camera = defaultCamera
        }

        clippingVolumeHandler.render(context)
        camera.render(context)
        cameraHandler.render(context)
        world.render(context)
    }


    def displayChanged(drawable: GLAutoDrawable, modeChanged: Boolean, deviceChanged: Boolean) {
        println("displayChanged(" + drawable + "," + modeChanged + ", " + deviceChanged + ")")
    }

    def dispose(drawable: GLAutoDrawable) {
        println("dispose(" + drawable + ")")
    }

    private def updateContext(drawable: GLAutoDrawable) {
        context.gl = drawable.getGL.getGL3bc
        
        context.currentTime = System.currentTimeMillis
        context.elapsed = context.currentTime - context.creationTime
        context.totalFrameCount += 1
        val currentTimeNano = System.nanoTime
        context.frameRate = 1000000000 / (currentTimeNano - context.lastFrameTimeNano)
        context.lastFrameTimeNano = currentTimeNano
        
        context.upKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_UP)
        context.downKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_DOWN)
        context.rightKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_RIGHT)
        context.leftKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_LEFT)
        context.controlKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_CONTROL)
        context.shiftKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_SHIFT)
        context.spaceKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_SPACE)
        context.escapeKeyPressed = keyEventDispatcher.isKeyPressed(KeyEvent.VK_ESCAPE)
        
        context.pressedKeys = keyEventDispatcher.pressedKeys

        context.camera = camera
    }
    
    private def exit {
        System.exit(0)
    }

}
