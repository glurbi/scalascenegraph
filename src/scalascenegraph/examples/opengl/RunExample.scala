package scalascenegraph.examples.opengl

import scalascenegraph.core._
import scalascenegraph.examples._
import scalascenegraph.ui.browser._

object RunExample {
	
    def main(args: Array[String]) {
    	val clazz = Class.forName(args(0))
    	val example = clazz.newInstance.asInstanceOf[Example]
        Browser.getDefault(example.example, true).show
    }

}