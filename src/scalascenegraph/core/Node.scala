package scalascenegraph.core

import scala.collection.mutable.ArrayBuffer

import scalascenegraph.core.Predefs._

abstract class Node {

    private val preRenderHooks = new ArrayBuffer[Hook]
    private val postRenderHooks = new ArrayBuffer[Hook]

    // TODO: put the renderer in the context
	def render(renderer: Renderer, context: Context) {
		preRender(context)
		doRender(renderer, context)
		postRender(context)
	}

    def addPreRenderHook(hook: Hook) {
    	preRenderHooks += hook
    }
    
    def addPostRenderHook(hook: Hook) {
    	postRenderHooks += hook
    }
    
	def preRender(context: Context) {
		preRenderHooks.foreach { hook => hook(this, context) }
	}
	def postRender(context: Context) {
		postRenderHooks.foreach { hook => hook(this, context) }
	}
    
	def doRender(renderer: Renderer, context: Context)
}
