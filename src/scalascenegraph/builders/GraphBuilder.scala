package scalascenegraph.builders

import java.io._
import java.nio._
import java.awt.image._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import scala.collection.mutable.Stack
import com.jogamp.common.nio._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core._
import scalascenegraph.builders._
import scalascenegraph.core.Predefs._

trait GraphBuilder {

	protected val stack = new Stack[Node]
	
	def world(body: => Unit): World = {
		stack.push(new World)
		body
		stack.pop.asInstanceOf[World]
	}

	def detached(body: => Unit): Group = {
		val group = new Group
		stack.push(group)
		body
		stack.pop.asInstanceOf[Group]
	}

	def attach(node: Node) {
		stack.top.attach(node)
	}
	
	def attach(group: Group) {
		stack.top.attach(group)
	}
	
	def attach(geometry: Geometry) {
		stack.top.attach(geometry)
	}

	def attach(texture: Texture) {
		stack.top.attach(texture)
	}
	
	def attach(font: Font) {
		stack.top.attach(font)
	}
	
	def attach(shader: Shader) {
		stack.top.attach(shader)
	}
	
	def attach(program: Program) {
		stack.top.attach(program)
	}

	def attach(uniform: Uniform) {
		stack.top.attach(uniform)
	}
	
	def attach[T <: Buffer](vbo: VertexBufferObject[T]) {
		stack.top.attach(vbo)
	}

	def group(body: => Unit): Group = {
		val group = new Group
		stack.top.attach(group)
		stack.push(group)
		body
		stack.pop.asInstanceOf[Group]
	}

	def conditional(condition: (Context) => Boolean)(body: => Unit): Node = {
		val child = detached { body }
		val optional = new Node {
			def render(context: Context) {
				if (condition(context)) {
					child.render(context)
				}
			}
		}
		attach(optional)
		optional
	}

	def node(fun: (Context) => Unit): Node = new Node {
		def render(context: Context) {
			fun(context)
		}
	}
	
}
