package scalascenegraph.core

import java.awt.{Color => JColor}
import javax.media.opengl._
import scala.collection.mutable._

import scalascenegraph.core.Predefs._

/**
 * The base class for any scene graph node.
 */
trait Node {

    private val states = new ArrayBuffer[State]
    
    // TODO: provide a add(child: Node) method to avoid asInstanceOf[Group]
    
    //
    // TODO: should be resources...
    //
   	private val textures = Map.empty[String, Texture]
    private val fonts = Map.empty[String, Font]
    private val shaders = Map.empty[String, Shader]
    private val programs = Map.empty[String, Program]

    /**
     * The parent node of this node in the graph.
     */
    var parent: Node = null
    
    /**
     * This method should not be overriden in subclasses.
     */
	def render(context: Context) {
		states.foreach { state => state.preRender(context) }
		preRender(context)
		doRender(context)
		postRender(context)
		states.reverse.foreach { state => state.postRender(context) }
	}
    
    def addState(state: State) {
    	states += state
    }
    
    def addTexture(name: String, texture: Texture) {
    	textures += name -> texture
    }
    
    def addFont(name: String, font: Font) {
    	fonts += name -> font
    }
    
    def addShader(name: String, shader: Shader) {
    	shaders += name -> shader
    }
    
    def addProgram(name: String, program: Program) {
    	programs += name -> program
    }
    
    def getTexture(name: String): Texture = {
    	textures.get(name) match {
    		case Some(texture) => texture
    		case None => parent.getTexture(name)
    	}
    }
    
    def getFont(name: String): Font = {
    	fonts.get(name) match {
    		case Some(font) => font
    		case None => parent.getFont(name)
    	}
    }

    def getShader(name: String): Shader = {
    	shaders.get(name) match {
    		case Some(shader) => shader
    		case None => parent.getShader(name)
    	}
    }
    
    def getProgram(name: String): Program = {
    	programs.get(name) match {
    		case Some(program) => program
    		case None => parent.getProgram(name)
    	}
    }
    
    /**
     * The actual rendering of a node should be implemented here.
     */
	def doRender(context: Context) {}
	
	/**
	 * Called once only, during scene graph initialisation.
	 * Can be used to load resources (textures, bitmaps, ...)
	 */
	def prepare(context: Context) {}
	
	/**
	 * Called once when the scene graph is not used any longer.
	 * Resources should be freed here.
	 */
	def dispose(context: Context) {}

	/**
	 * Called before a node is actually rendered (doRender() method)
	 */
	def preRender(context: Context) {}
	
	/**
	 * Called after a node is actually rendered (doRender() method)
	 */
	def postRender(context: Context) {}
	
}

/**
 * Wraps a node so that the specified hook can be called before the node is
 * rendered, for each frame. The hook code allows the user to change some node
 * attributes before rendering, thus making the wrapped node dynamic. 
 */
class DynamicNode[T <: Node](val hook: NodeHook[T], val node: T) extends Node {
	
	parent = node.parent
	
	override def preRender(context: Context) {
		hook(node, context)
		node.preRender(context)
	}
	override def doRender(context: Context) {
		node.doRender(context)
	}
	override def postRender(context: Context) {
		node.postRender(context)
	}
}

/**
 * A node that is a simple container for other nodes.
 */
class Group extends Node {
  
    private val children = new ArrayBuffer[Node]
    
    def add(child: Node) {
        children += child
    }
  
    override def doRender(context: Context) {
        children.foreach { child => child.render(context) }
    }
    
    override def prepare(context: Context) {
    	children.foreach { child => child.prepare(context) }
    }

    override def dispose(context: Context) {
    	children.foreach { child => child.dispose(context) }
    }
    
}

/**
 * The top node in a scene graph.
 */
class World extends Group {

    override def doRender(context: Context) {
        context.renderer.clear
        super.doRender(context)
        context.renderer.flush
    }
  
}
