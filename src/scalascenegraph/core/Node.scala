package scalascenegraph.core

import java.awt.{Color => JColor}
import javax.media.opengl._
import java.nio._
import scala.collection.mutable._

import scalascenegraph.core.Predefs._
import scalascenegraph.core.Predefs._

/**
 * The base class for any scene graph node.
 */
trait Node {

    /**
     * The parent node of this node in the graph.
     */
    var parent: Node = null
    
    /**
     * The actual rendering of a node should be implemented here.
     * Must be overriden in concrete nodes.
     */
	def render(context: Context)
    
	/**
	 * Attaches the state given in parameter to this node,
	 * thus making it part of the scene graph.
	 */
	def attach(state: State) {
		throw new UnsupportedOperationException
	}

    /**
     * Attaches the node given in parameter to this node,
     * thus making is part of the scene graph.
     */
    def attach(child: Node) {
    	throw new UnsupportedOperationException
    }
    
    /**
     * Attaches the resource given in parameter to this node,
     * thus making is part of the scene graph.
     */
    def attach(name: String, resource: Resource) {
    	throw new UnsupportedOperationException
    }
    
    /**
     * Returns the Resource object corresponding to the name given in parameter,
     * or <code>null</code> if it doesn't exist.
     */
    def getResource[T <: Resource](name: String): T = {
    	parent.getResource(name)
    }
    
    /**
     * Visitor pattern support ...
     */
    def accept(visitor: NodeVisitor) {}
}

/**
 * Visitor pattern support ...
 */
abstract class NodeVisitor(context: Context) {
	def visit(group: Group)
}

/**
 * Wraps a node so that the specified hook can be called before the node is
 * rendered, for each frame. The hook code allows the user to change some node
 * attributes before rendering, thus making the wrapped node dynamic. 
 */
class DynamicNode[T <: Node](val hook: NodeHook[T], val node: T) extends Node {
	
	parent = node.parent
	
	def render(context: Context) {
		hook(node, context)
		node.render(context)
	}
}

/**
 * A node that is a simple container for other nodes.
 */
class Group extends Node {
  
    val children = new ArrayBuffer[Node]
    val states = new ArrayBuffer[State]
    val resources = LinkedHashMap.empty[String, Resource]
    
    override def attach(child: Node) {
    	if (child.parent != null) {
    		throw new RuntimeException(child + " is already attached to a node")
    	}
        children += child
        child.parent = this
    }
  
    override def attach(state: State) {
    	states += state
    }
    
    override def attach(name: String, resource: Resource) {
    	resources += name -> resource
    }
    
    override def getResource[T <: Resource](name: String): T = {
    	resources.get(name) match {
    		case Some(resource) => resource.asInstanceOf[T]
    		case None => parent.getResource(name)
    	}
    }
    
    override def render(context: Context) {
		states.foreach { state => state.preRender(context) }
        children.foreach { child => child.render(context) }
		states.reverse.foreach { state => state.postRender(context) }
    }
    
    override def accept(visitor: NodeVisitor) {
    	visitor.visit(this)
    }
    
}

/**
 * The top node in a scene graph.
 */
class World extends Group {

    override def render(context: Context) {
        context.renderer.clear
        super.render(context)
        context.renderer.finish
    }
  
}

/**
 * A marker class for nodes that behave as overlays.
 * An overlay is a graphic item that is drawn on top in the current frame
 * buffer, regardless of the depth buffer state.
 */
abstract class Overlay extends Node

/**
 * An overlay made of an image.
 */
class ImageOverlay(var x: Int = 0,
		           var y: Int = 0,
 		           var width: Int,
		           var height: Int,
		           var imageType: ImageType,
		           var rawImage: ByteBuffer)
extends Overlay {

	override def render(context: Context) {
		if (rawImage != null) {
			context.renderer.drawImage(x, y, width, height, imageType, rawImage)
		}
	}
	
}

/**
 * An overlay made of text.
 */
class TextOverlay(var x: Int = 0,
		          var y: Int = 0,
		          var font: Font,
		          var text: String)
extends Overlay {

	override def render(context: Context) {
		context.renderer.drawText(x, y, font, text)
	}
	
}
