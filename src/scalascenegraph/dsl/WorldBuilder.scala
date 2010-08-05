package scalascenegraph.dsl

import java.nio._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

trait WorldBuilder {

	private val stack = new Stack[Node]
	
	def preRenderHook(hook: Hook) {
		stack.top.addPreRenderHook(hook)
	}
	
	def postRenderHook(hook: Hook) {
		stack.top.addPostRenderHook(hook)
	}
	
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

	def rotation(angle: Float, x: Float, y: Float, z: Float)(body: => Unit): Rotation = {
		val rot = new Rotation(angle, x, y, z)
		stack.top.asInstanceOf[Group].add(rot)
		stack.push(rot)
		body
		stack.pop.asInstanceOf[Rotation]
	}

	def polygonMode(face: Face, mode: DrawingMode)(body: => Unit): PolygonMode = {
		val polygonMode = new PolygonMode(face, mode)
		stack.top.asInstanceOf[Group].add(polygonMode)
		stack.push(polygonMode)
		body
		stack.pop.asInstanceOf[PolygonMode]
	}
	
	def frontFace(frontFace: FrontFace)(body: => Unit): FrontFaceMode = {
		val frontFaceMode = new FrontFaceMode(frontFace)
		stack.top.asInstanceOf[Group].add(frontFaceMode)
		stack.push(frontFaceMode)
		body
		stack.pop.asInstanceOf[FrontFaceMode]
	}
	
	def cullFace(b: Boolean)(body: => Unit): CullFaceMode = {
		val cullFaceMode = new CullFaceMode(b)
		stack.top.asInstanceOf[Group].add(cullFaceMode)
		stack.push(cullFaceMode)
		body
		stack.pop.asInstanceOf[CullFaceMode]
	}
	
	def triangle(vertices: Array[Float]) {
		stack.top.asInstanceOf[Group].add(new Triangle(vertices))
	}
	
	def triangle(vertices: Array[Float], colors: Array[Float]) {
		stack.top.asInstanceOf[Group].add(new ColoredTriangle(vertices, colors))
	}
	
	def quad(vertices: Array[Float], color: Color) {
		val c = color
		val colors = Array(c.red, c.green, c.blue, c.red, c.green, c.blue,
				c.red, c.green, c.blue, c.red, c.green, c.blue)
		stack.top.asInstanceOf[Group].add(new ColoredQuad(vertices, colors))
	}
	
	def cube {
		stack.top.asInstanceOf[Group].add(new Cube)
	}
	
	def cube(color: Color) {
		stack.top.asInstanceOf[Group].add(new UnicoloredCube(color))
	}
	
	def cube(colors: Array[Float]) {
		stack.top.asInstanceOf[Group].add(new ColoredCube(Buffers.newDirectFloatBuffer(colors)))
	}
	
	def sphere(steps: Int) {
		stack.top.asInstanceOf[Group].add(new Sphere(steps))
	}
	
	def sphere(steps: Int, color: Color) {
		stack.top.asInstanceOf[Group].add(new UnicoloredSphere(steps, color))
	}
	
	def light(mode: OnOffMode)(body: => Unit): LightMode = {
		val l = new LightMode(mode)
		stack.top.asInstanceOf[Group].add(l)
		stack.push(l)
		body
		stack.pop.asInstanceOf[LightMode]
	}

	def ambient(intensity: Intensity)(body: => Unit): AmbientLightMode = {
		val l = new AmbientLightMode(intensity)
		stack.top.asInstanceOf[Group].add(l)
		stack.push(l)
		body
		stack.pop.asInstanceOf[AmbientLightMode]
	}
	
	def material(face: Face, lightType: LightType, color: Color)(body: => Unit): MaterialMode = {
		val m = new MaterialMode(face, lightType, color)
		stack.top.asInstanceOf[Group].add(m)
		stack.push(m)
		body
		stack.pop.asInstanceOf[MaterialMode]
	}
	
}
