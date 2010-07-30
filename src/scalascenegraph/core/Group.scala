package scalascenegraph.core

import scala.collection.mutable.ArrayBuffer

class Group extends Node {
  
    private val children = new ArrayBuffer[Node]
  
    def add(child: Node) {
        children += child
    }
  
    def doRender(renderer: Renderer, context: Context) {
        children.foreach { child => child.render(renderer, context) }
    }
    
}
