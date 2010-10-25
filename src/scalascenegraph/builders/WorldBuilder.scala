package scalascenegraph.builders

import java.io._
import java.nio._
import java.awt.image._
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

trait WorldBuilder extends GeometryBuilder {

	private val stack = new Stack[Node]
	
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

	def node(fun: (Context) => Unit): Node = new Node {
		def render(context: Context) {
			fun(context)
		}
	}
	
	def color(c: Color) {
		stack.top.attach(new ColorState(c))
	}

	def smooth(smooth: SmoothType) {
		stack.top.attach(new SmoothState(smooth))
	}
	
	def pointSize(size: Float) {
		stack.top.attach(new PointSizeState(size))
	}
	
	def lineWidth(width: Float) {
		stack.top.attach(new LineWidthState(width))
	}

	def lineStipple(factor: Int, pattern: Short) {
		stack.top.attach(new LineStippleState(factor, pattern))
	}
	
	def translation(x: Float, y: Float, z: Float) {
		stack.top.attach(new Translation(x, y, z))
	}

	def translation(x: Float, y: Float, z: Float, hook: StateHook[Translation]) {
		stack.top.attach(new DynamicState(hook, new Translation(x, y, z)))
	}
	
	def translation(hook: StateHook[Translation]) {
		stack.top.attach(new DynamicState(hook, new Translation(0.0f, 0.0f, 0.0f)))
	}
	
	def rotation(angle: Float, x: Float, y: Float, z: Float) {
		stack.top.attach(new Rotation(angle, x, y, z))
	}

	def rotation(angle: Float, x: Float, y: Float, z: Float, hook: StateHook[Rotation]) {
		stack.top.attach(new DynamicState(hook, new Rotation(angle, x, y, z)))
	}

	def rotation(hook: StateHook[Rotation]) {
		stack.top.attach(new DynamicState(hook, new Rotation))
	}
	
	def polygonMode(face: Face, mode: DrawingMode) {
		stack.top.attach(new PolygonModeState(face, mode))
	}

	def polygonOffset(factor: Float, units: Float) {
		stack.top.attach(new PolygonOffsetState(factor, units))
	}
	
	def frontFace(frontFace: FrontFace) {
		stack.top.attach(new FrontFaceState(frontFace))
	}
	
	def cullFace(cullFace: OnOffState) {
		stack.top.attach(new CullFaceState(cullFace))
	}

	def depthTest(depthTest: OnOffState) {
		stack.top.attach(new DepthTestState(depthTest))
	}
	
	def point(v: Vertice3D, color: Color)
	{
		val vertices = Vertices[FloatBuffer](
			Buffers.newDirectFloatBuffer(Array(v.x, v.y, v.z)),
			GL_FLOAT,
			dim_3D,
			GL_POINTS)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, color))
		stack.top.attach(geometry)
	}
	
	def line(v1: Vertice3D, v2: Vertice3D)
	{
		val vertices = Vertices[FloatBuffer](
			Buffers.newDirectFloatBuffer(
				Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z)),
			GL_FLOAT,
			dim_3D,
			GL_LINES)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices))
		stack.top.attach(geometry)
	}

	def triangle(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D)	{
		val vertices = Vertices(
			Buffers.newDirectFloatBuffer(
				Array(v1.x, v1.y, v1.z,
					  v2.x, v2.y, v2.z,
					  v3.x, v3.y, v3.z)),
			GL_FLOAT,
			dim_3D,
			GL_TRIANGLES)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices))
		stack.top.attach(geometry)
	}

	def triangle(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, color: Color)
	{
		val vertices = Vertices[FloatBuffer](
			Buffers.newDirectFloatBuffer(
				Array(v1.x, v1.y, v1.z,
					  v2.x, v2.y, v2.z,
					  v3.x, v3.y, v3.z)),
			GL_FLOAT,
			dim_3D,
			GL_TRIANGLES)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, color))
		stack.top.attach(geometry)
	}
	
	def triangle(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D,
    		     c1: Color, c2: Color, c3: Color)
	{
		val vertices = Vertices[FloatBuffer](Buffers.newDirectFloatBuffer(
			Array(v1.x, v1.y, v1.z,
				  v2.x, v2.y, v2.z,
				  v3.x, v3.y, v3.z)),
											 GL_FLOAT,
											 dim_3D,
											 GL_TRIANGLES)
		val colors = Colors(Buffers.newDirectFloatBuffer(
			Array(c1.r, c1.g, c1.b, c1.a,
				  c2.r, c2.g, c2.b, c2.a,
				  c3.r, c3.g, c3.b, c3.a)), GL_FLOAT, RGBA)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, colors))
		stack.top.attach(geometry)
	}
	
	def quad(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D, color: Color) {
		val vertices =
			Vertices(
				Buffers.newDirectFloatBuffer(
					Array(v1.x, v1.y, v1.z,
						  v2.x, v2.y, v2.z,
						  v3.x, v3.y, v3.z,
						  v4.x, v4.y, v4.z)),
				GL_FLOAT,
				dim_3D,
				GL_QUADS)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, color))
		stack.top.attach(geometry)
	}
	
	def quad(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D) {
		val vertices =
			Vertices(
				Buffers.newDirectFloatBuffer(
					Array(v1.x, v1.y, v1.z,
						  v2.x, v2.y, v2.z,
						  v3.x, v3.y, v3.z,
						  v4.x, v4.y, v4.z)),
				GL_FLOAT,
				dim_3D,
				GL_QUADS)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices))
		stack.top.attach(geometry)
	}

	def quad(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D,
    		     c1: Color, c2: Color, c3: Color, c4: Color)
	{
		val vertices = Vertices[FloatBuffer](
			Buffers.newDirectFloatBuffer(
				Array(v1.x, v1.y, v1.z,
					  v2.x, v2.y, v2.z,
					  v3.x, v3.y, v3.z,
					  v4.x, v4.y, v4.z)),
			GL_FLOAT,
			dim_3D,
			GL_QUADS)
		val colors = Colors(Buffers.newDirectFloatBuffer(
			Array(c1.r, c1.g, c1.b, c1.a,
				  c2.r, c2.g, c2.b, c2.a,
				  c3.r, c3.g, c3.b, c3.a,
				  c4.r, c4.g, c4.b, c4.a)), GL_FLOAT, RGBA)
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, colors))
		stack.top.attach(geometry)
	}
	
	def quad(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D, texture: Texture) {
		val vertices =
			Vertices(
				Buffers.newDirectFloatBuffer(
					Array(v1.x, v1.y, v1.z,
						  v2.x, v2.y, v2.z,
						  v3.x, v3.y, v3.z,
						  v4.x, v4.y, v4.z)),
				GL_FLOAT,
				dim_3D,
				GL_QUADS)
		val textureCoordinates = TextureCoordinates(
			Buffers.newDirectFloatBuffer(
				Array(0.0f, 0.0f,
					  1.0f, 0.0f,
					  1.0f, 1.0f,
					  0.0f, 1.0f)))
		val geometry = new Geometry
		geometry.addRenderable(createRenderable(vertices, textureCoordinates, texture))
		stack.top.attach(geometry)
	}
	
	def cube(normals: Boolean) {
		val b = new CubeBuilder
		val geometry = normals match {
			case false => createGeometry(b.createVertices)
			case true => createGeometry(b.createVertices, b.createNormals)
		}
		stack.top.attach(geometry)
	}
	
	def cube(texture: Texture, normals: Boolean) {
		box(1.0f, 1.0f, 1.0f, 1, 1, 1, texture, normals)
	}
	
	def cube(color: Color) {
		val builder = new CubeBuilder
		val vertices = builder.createVertices
		val geometry = createGeometry(vertices, color)
		stack.top.attach(geometry)
	}
	
	def cube[ColorBuffer <: Buffer](colors: Colors[ColorBuffer]) {
		val builder = new CubeBuilder
		val vertices = builder.createVertices
		val geometry = createGeometry(vertices, colors)
		stack.top.attach(geometry)
	}
	
	def sphere(n: Int, r: Float, normals: Boolean) {
		val b = new SphereBuilder(n, r)
		val geometry = normals match {
			case false => createGeometry(b.createVertices)
			case true => createGeometry(b.createVertices, b.createNormals)
		}
		stack.top.attach(geometry)
	}
	
	def sphere(n: Int, r: Float, color: Color, normals: Boolean) {
		val b = new SphereBuilder(n, r)
		val geometry = normals match {
			case false => createGeometry(b.createVertices, color)
			case true => createGeometry(b.createVertices, color, b.createNormals)
		}
		stack.top.attach(geometry)
	}
	
	def sphere(n: Int, r: Float, texture: Texture, normals: Boolean) {
		val b = new SphereBuilder(n, r)
		val geometry = normals match {
			case false => createGeometry(b.createVertices, b.createTextureCoordinates, texture)
			case true => createGeometry(b.createVertices, b.createTextureCoordinates, texture, b.createNormals)
		}
		stack.top.attach(geometry)
	}

	def ellipsoid(n: Int, m: Int, a: Float, b: Float, c: Float) {
		val builder = new EllipsoidBuilder(n, m, a, b, c)
		stack.top.attach(builder.createEllipsoid)
	}
	
	def box(width: Float, height: Float, depth: Float, l: Int, m: Int, n: Int, normals: Boolean) {
		val b = new BoxBuilder(width, height, depth, l, m, n)
		val geometry = normals match {
			case false => createGeometry(b.createVertices)
			case true => createGeometry(b.createVertices, b.createNormals)
		}
		stack.top.attach(geometry)
	}

	def box(width: Float, height: Float, depth: Float, l: Int, m: Int, n: Int, texture: Texture, normals: Boolean) {
		val b = new BoxBuilder(width, height, depth, l, m, n)
		val geometry = normals match {
			case false => createGeometry(b.createVertices, b.createTextureCoordinates, texture)
			case true => createGeometry(b.createVertices, b.createTextureCoordinates, texture, b.createNormals)
		}
		stack.top.attach(geometry)
	}
	
	def light(mode: OnOffState) {
		stack.top.attach(new GlobalLightState(mode))
	}

	def light(instance: LightInstance, mode: OnOffState) {
		stack.top.attach(new LightState(instance, mode))
	}

	// TODO: remove (replace with light parameter)
	def light(instance: LightInstance, lightType: LightType, color: Color) {
		stack.top.attach(new LightColorState(instance, lightType, color))
	}

	def light(instance: LightInstance, position: Position) {
		stack.top.attach(new LightPositionState(instance, position))
	}
	
	def light(instance: LightInstance, parameter: LightParameter, value: Array[Float]) {
		stack.top.attach(new LightParameterState(instance, parameter, value))
	}

	def shininess(face: Face, shininess: Int) {
		stack.top.attach(new MaterialShininessState(face, shininess))
	}
	
	def ambient(intensity: Intensity) {
		stack.top.attach(new AmbientLightState(intensity))
	}

	def colorMaterial(face: Face, lightType: LightType) {
		stack.top.attach(new ColorMaterialState(face, lightType))
	}
	
	def material(face: Face, lightType: LightType, color: Color) {
		stack.top.attach(new MaterialState(face, lightType, color))
	}
	
	def torus(n: Int, R: Float, r: Float) {
		val builder = new TorusBuilder(n, R, r)
		stack.top.attach(builder.createTorus)
	}

	def shadeModel(shadeModel: ShadeModel) {
		stack.top.attach(new ShadeModelState(shadeModel))
	}

	def checkerBoard(n: Int, m: Int, c1: Color, c2: Color) {
		val builder = new CheckerBoardBuilder(n, m, c1, c2)
		stack.top.attach(builder.createCheckerBoard)
	}
	
	def fog(color: Color, mode: FogMode) {
		stack.top.attach(new FogState(color, mode))
	}
	
	def overlay(x: Int, y: Int, image: BufferedImage) {
		val format = image.getColorModel.hasAlpha match {
		    case true => GL_RGBA
		    case false => GL_RGB
		}
		val data = Utils.makeDirectByteBuffer(image)
		stack.top.attach(new ImageOverlay(x, y, image.getWidth, image.getHeight, format, data))
	}

	def overlay(x: Int, y: Int, image: BufferedImage, hook: NodeHook[ImageOverlay]) {
		val format = image.getColorModel.hasAlpha match {
		    case true => GL_RGBA
		    case false => GL_RGB
		}
		val data = Utils.makeDirectByteBuffer(image)
		stack.top.attach(new DynamicNode(hook, new ImageOverlay(x, y, image.getWidth, image.getHeight, format, data)))
	}

	def overlay(x: Int, y: Int, font: Font, text: String) {
		stack.top.attach(new TextOverlay(x, y, font, text))
	}

	def overlay(x: Int, y: Int, font: Font, text: String, hook: NodeHook[TextOverlay]) {
		stack.top.attach(new DynamicNode(hook, new TextOverlay(x, y, font, text)))
	}
	
	def showFramesPerSecond = {
		val defaultFont = FontBuilder.create(new JFont("Default", JFont.PLAIN, 20))
		attach(defaultFont)
		val fpsHook = (o: TextOverlay, c: Context) => {
			o.x = 10
			o.y = c.height - 30
			o.text = "FPS: " + c.frameRate
		}
		overlay(0, 0, defaultFont, "", fpsHook)
	}
	
	def blending(mode: OnOffState) {
		stack.top.attach(new BlendingState(mode))
	}

	def useProgram(program: Program) {
		stack.top.attach(new ProgramState(program))
	}
	
	def setUniform(uniform: Uniform, color: Color) {
		stack.top.attach(new UniformState(uniform, color.rgba))
	}
	
	def setUniform(uniform: Uniform, value: Float) {
		stack.top.attach(new UniformState(uniform, value))
	}

	def setUniform(uniform: Uniform, value: Float, hook: StateHook[UniformState]) {
		stack.top.attach(new DynamicState(hook, new UniformState(uniform, value)))
	}
	
	def lineStrip[T <: Buffer](vbo: VertexBufferObject[T]) {
		val geometry = new Geometry
		geometry.addRenderable(createRenderableVBO(vbo))
		stack.top.attach(geometry)
	}

	def lineStrips[T <: Buffer](vbo: VertexBufferObject[T], firsts: IntBuffer, counts: IntBuffer) {
		val geometry = new Geometry
		geometry.addRenderable(createRenderableVBO(vbo, firsts, counts))
		stack.top.attach(geometry)
	}
	
	def grid(width: Float, height: Float, m: Int, n: Int) {
		val builder = new GridBuilder(width, height, m, n)
		stack.top.attach(builder.createGrid)
	}

	def grid(width: Float, height: Float, m: Int, n: Int, texture: Texture) {
		val builder = new GridBuilder(width, height, m, n)
		stack.top.attach(builder.createGrid(texture))
	}

	def cone(n: Int, m: Int, r: Float, h: Float, normals: Boolean) {
		val b = new ConeBuilder(n, m, r, h)
		stack.top.attach(b.createCone(normals))
	}

}
