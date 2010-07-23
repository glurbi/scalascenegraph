package scalascenegraph.dsl

import scala.collection.mutable._

import scalascenegraph.core._

class WorldBuilder {

	private val stack = new Stack[Node]
	
	def world(body: => Unit): World = {
		stack.push(new World)
		body
		stack.pop.asInstanceOf[World]
	}

	def translation(x: Float, y: Float, z: Float)(body: => Unit): Translation = {
		val tr = new Translation(x, y, z)
		stack.top.asInstanceOf[Group].add(tr)
		stack.push(tr)
		body
		stack.pop.asInstanceOf[Translation]
	}
	
	def coloredTriangle(vertices: Array[Float], colors: Array[Float]) {
		stack.top.asInstanceOf[Group].add(new ColoredTriangle(vertices, colors))
	}
	
	def triangle(vertices: Array[Float]) {
		stack.top.asInstanceOf[Group].add(new Triangle(vertices))
	}
	
	def quad(vertices: Array[Float], color: Color) {
		val c = color
		val colors = Array(c.red, c.green, c.blue, c.red, c.green, c.blue,
				c.red, c.green, c.blue, c.red, c.green, c.blue)
		stack.top.asInstanceOf[Group].add(new ColoredQuad(vertices, colors))
	}
	
}
