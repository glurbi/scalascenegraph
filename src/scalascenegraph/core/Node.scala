package scalascenegraph.core

import java.awt.{Color => JColor}
import scala.collection.mutable._

import scalascenegraph.core.Predefs._

abstract class Node {

    private val preRenderHooks = new ArrayBuffer[NodeHook]
    private val postRenderHooks = new ArrayBuffer[NodeHook]
    private val states = new ArrayBuffer[State]

	def render(context: Context) {
		callPreRenderHooks(context)
		states.foreach { state => state.preRender(context) }
		doRender(context)
		states.reverse.foreach { state => state.postRender(context) }
		callPostRenderHooks(context)
	}

    def addPreRenderHook(hook: NodeHook) {
    	preRenderHooks += hook
    }
    
    def addPostRenderHook(hook: NodeHook) {
    	postRenderHooks += hook
    }
    
    def addState(state: State) {
    	states += state
    }
    
	def callPreRenderHooks(context: Context) {
		preRenderHooks.foreach { hook => hook(this, context) }
	}
	
	def callPostRenderHooks(context: Context) {
		postRenderHooks.foreach { hook => hook(this, context) }
	}
    
	def doRender(context: Context)
}

class Group extends Node {
  
    private val children = new ArrayBuffer[Node]
  
    def add(child: Node) {
        children += child
    }
  
    def doRender(context: Context) {
        children.foreach { child => child.render(context) }
    }
    
}

class World extends Group {

	var foreground: Color = JColor.white
	var background: Color = JColor.lightGray
	
    override def doRender(context: Context) {
    	val renderer = context.renderer
        renderer.clear
        renderer.clearColor(background);
        renderer.setColor(foreground)
        renderer.enableDepthTest
        renderer.enableCullFace
        super.doRender(context)
        renderer.flush
    }
  
}

abstract class Shape extends Node {
	
	def vertices: Vertices = null
	def colors: Colors = null
	def normals: Normals = null
	def color: Color = null
	def normal: Normal = null
	
	val geometryType: GeometryType
	val colorType: ColorType
	val normalType: NormalType
	
	def doRender(context: Context) {
		(geometryType, colorType, normalType) match {
			case (QuadArray, UnspecifiedColor, MultiNormal) => context.renderer.quads(vertices, normals)
		}
	}
	
}
