package scalascenegraph.examples

import java.awt._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example9 extends Example with WorldBuilder {

	val myshadersource =
	"""
	this is not a shader (yet!)
	"""
	
	def example =
		world {
			shader("myshader", FragmentShader, myshadersource)
		}
	
}
