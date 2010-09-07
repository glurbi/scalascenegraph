package scalascenegraph.core

import java.awt.{Color => JColor}
import javax.media.opengl._
import scala.collection.mutable._

import scalascenegraph.core.Predefs._

abstract class Node(val parent: Node) {

    private val states = new ArrayBuffer[State]
   	private val textures = Map.empty[String, Texture]
    private val fonts = Map.empty[String, Font]

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
    
    def getTexture(name: String): Texture = {
    	textures.get(name) match {
    		case Some(texture) => texture
    		case None => parent.getTexture(name)
    	}
    }
    
    def addFont(name: String, font: Font) {
    	fonts += name -> font
    }
    
    def getFont(name: String): Font = {
    	fonts.get(name) match {
    		case Some(font) => font
    		case None => parent.getFont(name)
    	}
    }
    
	def doRender(context: Context) {}
	def prepare(context: Context) {}
	def dispose(context: Context) {}
	def preRender(context: Context) {}
	def postRender(context: Context) {}
	
}

class DynamicOverlay[T <: Overlay](val hook: OverlayHook[T], val overlay: T) extends Node(overlay.parent) {
	override def preRender(context: Context) {
		hook(overlay, context)
		overlay.preRender(context)
	}
	override def doRender(context: Context) {
		overlay.doRender(context)
	}
	override def postRender(context: Context) {
		overlay.postRender(context)
	}
}



class Group(parent: Node) extends Node(parent) {
  
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

class World extends Group(null) {

	var foreground: Color = JColor.white
	var background: Color = JColor.lightGray
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
        renderer.clear
        renderer.clearColor(background);
        renderer.setColor(foreground)
        renderer.enableDepthTest
        renderer.enableCullFace
        renderer.enableBlending
        super.doRender(context)
        renderer.flush
    }
  
}
