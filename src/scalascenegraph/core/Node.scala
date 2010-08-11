package scalascenegraph.core

import scala.collection.mutable.ArrayBuffer

import scalascenegraph.core.Predefs._

abstract class Node {

    private val preRenderHooks = new ArrayBuffer[Hook]
    private val postRenderHooks = new ArrayBuffer[Hook]

	def render(context: Context) {
		callPreRenderHooks(context)
		doRender(context)
		callPostRenderHooks(context)
	}

    def addPreRenderHook(hook: Hook) {
    	preRenderHooks += hook
    }
    
    def addPostRenderHook(hook: Hook) {
    	postRenderHooks += hook
    }
    
	def callPreRenderHooks(context: Context) {
		preRenderHooks.foreach { hook => hook(this, context) }
	}
	
	def callPostRenderHooks(context: Context) {
		postRenderHooks.foreach { hook => hook(this, context) }
	}
    
	def doRender(context: Context)
	
	def preRender(context: Context) {}
	
	def postRender(context: Context) {}
}
