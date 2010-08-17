package scalascenegraph.dsl

import java.nio._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core._
import scalascenegraph.core.shapes._
import scalascenegraph.core.Predefs._

trait WorldBuilder {

	private val stack = new Stack[Node]
	
	def preRenderHook(hook: NodeHook) {
		stack.top.addPreRenderHook(hook)
	}
	
	def postRenderHook(hook: NodeHook) {
		stack.top.addPostRenderHook(hook)
	}
	
	def world(body: => Unit): World = {
		stack.push(new World)
		body
		stack.pop.asInstanceOf[World]
	}
	
	def group(body: => Unit): Group = {
		val g = new Group
		stack.top.asInstanceOf[Group].add(g)
		stack.push(g)
		body
		stack.pop.asInstanceOf[Group]
	}

	def color(c: Color) {
		stack.top.addState(new ColorState(c))
	}
	
	def translation(x: Float, y: Float, z: Float) {
		stack.top.addState(new Translation(x, y, z))
	}

	def translation(x: Float, y: Float, z: Float, hook: StateHook[Translation]) {
		stack.top.addState(new DynamicState(hook, new Translation(x, y, z)))
	}
	
	def translation(hook: StateHook[Translation]) {
		stack.top.addState(new DynamicState(hook, new Translation(0.0f, 0.0f, 0.0f)))
	}
	
	def rotation(angle: Float, x: Float, y: Float, z: Float) {
		stack.top.addState(new Rotation(angle, x, y, z))
	}

	def rotation(angle: Float, x: Float, y: Float, z: Float, hook: StateHook[Rotation]) {
		stack.top.addState(new DynamicState(hook, new Rotation(angle, x, y, z)))
	}

	def rotation(hook: StateHook[Rotation]) {
		stack.top.addState(new DynamicState(hook, new Rotation))
	}
	
	def polygonMode(face: Face, mode: DrawingMode) {
		stack.top.addState(new PolygonState(face, mode))
	}
	
	def frontFace(frontFace: FrontFace) {
		stack.top.addState(new FrontFaceState(frontFace))
	}
	
	def cullFace(b: Boolean) {
		stack.top.addState(new CullFaceState(b))
	}

	def triangle(v1: Vertice, v2: Vertice, v3: Vertice)	{
		stack.top.asInstanceOf[Group].add(new Triangle(v1, v2, v3))
	}
	
	def triangle(v1: Vertice, v2: Vertice, v3: Vertice,
    		     c1: Color, c2: Color, c3: Color)
	{
		stack.top.asInstanceOf[Group].add(new ColoredTriangle(v1, v2, v3, c1, c2, c3))
	}
	
	def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice, color: Color) {
		val c = color
		stack.top.asInstanceOf[Group].add(new ColoredQuad(v1, v2, v3, v4, c, c, c, c))
	}
	
	def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice) {
		stack.top.asInstanceOf[Group].add(new Quad(v1, v2, v3, v4))
	}
	
	def cube {
		stack.top.asInstanceOf[Group].add(new Cube)
	}
	
	def cube(color: Color) {
		stack.top.asInstanceOf[Group].add(new UnicoloredCube(color))
	}
	
	def cube(colors: Array[Float]) {
		stack.top.asInstanceOf[Group].add(new ColoredCube(Colors(Buffers.newDirectFloatBuffer(colors))))
	}
	
	def sphere(steps: Int) {
		stack.top.asInstanceOf[Group].add(new Sphere(steps))
	}
	
	def sphere(steps: Int, color: Color) {
		stack.top.asInstanceOf[Group].add(new UnicoloredSphere(steps, color))
	}
	
	def light(mode: OnOffState) {
		stack.top.addState(new LightState(mode))
	}
	
	def light(lightType: LightType, position: Position, color: Color) {
		stack.top.addState(new Light(lightType, position, color))
	}

	def ambient(intensity: Intensity) {
		stack.top.addState(new AmbientLightState(intensity))
	}
	
	def material(face: Face, lightType: LightType, color: Color) {
		stack.top.addState(new MaterialState(face, lightType, color))
	}
	
	def lineWidth(width: Float) {
		stack.top.addState(new LineWidthState(width))
	}
	
	def torus(n: Int, R: Float, r: Float) {
		stack.top.asInstanceOf[Group].add(new Torus(n, R, r))
	}

	def shadeModel(shadeModel: ShadeModel) {
		stack.top.addState(new ShadeModelState(shadeModel))
	}

	def checkerBoard(n: Int, m: Int, c1: Color, c2: Color) {
		stack.top.asInstanceOf[Group].add(new CheckerBoard(n, m, c1, c2))
	}
	
}
