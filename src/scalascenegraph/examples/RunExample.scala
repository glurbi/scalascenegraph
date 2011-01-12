package scalascenegraph.examples

import javax.swing._

import scalascenegraph.core._
import scalascenegraph.examples._
import scalascenegraph.ui.browser._

object RunExample {
    
    def main(args: Array[String]) {
        val clazz = Class.forName(args(0))
        val example = clazz.newInstance.asInstanceOf[Example]
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(example.example, true).show
    }

}