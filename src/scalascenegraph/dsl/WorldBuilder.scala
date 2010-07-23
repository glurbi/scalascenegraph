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
	
	def coloredTriangle(v1: Vertex, c1: Color, v2: Vertex, c2: Color, v3: Vertex, c3: Color) {
		stack.top.asInstanceOf[Group].add(new ColoredTriangle(v1, c1, v2, c2, v3, c3))
	}
	
	def triangle(v1: Vertex, v2: Vertex, v3: Vertex) {
		stack.top.asInstanceOf[Group].add(new Triangle(v1, v2, v3))
	}
	
	def unicoloredQuad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex, color: Color) {
		stack.top.asInstanceOf[Group].add(new UnicoloredQuad(v1, v2, v3, v4, color))
	}
}
