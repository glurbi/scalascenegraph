package scalascenegraph.examples.opengl

import scalascenegraph.core._
import scalascenegraph.opengl._
import scalascenegraph.examples._

object RunExample {
	
    def main(args: Array[String]) {
    	val clazz = Class.forName(args(0))
    	val example = clazz.newInstance.asInstanceOf[Example]
        OpenglBrowser.getDefault(example.example, true).show
    }

}