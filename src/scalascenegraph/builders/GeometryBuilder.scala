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

trait GeometryBuilder extends GraphBuilder with StateBuilder {

	def point(v: Vertice3D, color: Color) {
		val vertices = Vertices[FloatBuffer](
			Buffers.newDirectFloatBuffer(Array(v.x, v.y, v.z)),
			GL_FLOAT,
			dim_3D,
			GL_POINTS)
		stack.top.attach(new SimpleGeometry(vertices, color))
	}
	
	def line(v1: Vertice3D, v2: Vertice3D)
	{
		val vertices = Vertices[FloatBuffer](
			Buffers.newDirectFloatBuffer(
				Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z)),
			GL_FLOAT,
			dim_3D,
			GL_LINES)
		stack.top.attach(new SimpleGeometry(vertices))
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
		stack.top.attach(new SimpleGeometry(vertices))
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
		stack.top.attach(new SimpleGeometry(vertices, color))
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
		stack.top.attach(new SimpleGeometry(vertices, colors))
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
		stack.top.attach(new SimpleGeometry(vertices, color))
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
		stack.top.attach(new SimpleGeometry(vertices))
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
		stack.top.attach(new SimpleGeometry(vertices, colors))
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
		stack.top.attach(new SimpleGeometry(vertices, textureCoordinates, texture))
	}

	def cube(normals: Boolean) {
		cube(1.0f, normals)
	}
	
	def cube(size: Float, normals: Boolean) {
		box(size, size, size, 1, 1, 1, normals)
	}
	
	def cube(texture: Texture, normals: Boolean) {
		box(1.0f, 1.0f, 1.0f, 1, 1, 1, texture, normals)
	}
	
	def cube(color: Color) {
		val builder = new CubeBuilder
		val vertices = builder.createVertices
		val geometry = new SimpleGeometry(vertices, color)
		stack.top.attach(geometry)
	}
	
	def cube[ColorBuffer <: Buffer](colors: Colors[ColorBuffer]) {
		val builder = new CubeBuilder
		val vertices = builder.createVertices
		val geometry = new SimpleGeometry(vertices, colors)
		stack.top.attach(geometry)
	}
	
	def sphere(n: Int, r: Float, normals: Boolean): Geometry = {
		val b = new SphereBuilder(n, r)
		normals match {
			case false => new SimpleGeometry(b.createVertices)
			case true => new SimpleGeometry(b.createVertices, b.createNormals)
		}
	}
	
	def sphere(n: Int, r: Float, color: Color, normals: Boolean) {
		val b = new SphereBuilder(n, r)
		val geometry = normals match {
			case false => new SimpleGeometry(b.createVertices, color)
			case true => new SimpleGeometry(b.createVertices, color, b.createNormals)
		}
		stack.top.attach(geometry)
	}
	
	def sphere(n: Int, r: Float, texture: Texture, normals: Boolean) {
		val b = new SphereBuilder(n, r)
		val geometry = normals match {
			case false => new SimpleGeometry(b.createVertices, b.createTextureCoordinates, texture)
			case true => new SimpleGeometry(b.createVertices, b.createTextureCoordinates, texture, b.createNormals)
		}
		stack.top.attach(geometry)
	}

	def ellipsoid(n: Int, m: Int, a: Float, b: Float, c: Float, normals: Boolean): Geometry = {
		val bld = new EllipsoidBuilder(n, m, a, b, c)
		normals match {
			case false => new SimpleGeometry(bld.createVertices)
			case true => new SimpleGeometry(bld.createVertices, bld.createNormals)
		}
	}
	
	def box(width: Float, height: Float, depth: Float, l: Int, m: Int, n: Int, normals: Boolean) {
		val b = new BoxBuilder(width, height, depth, l, m, n)
		val geometry = normals match {
			case false => new SimpleGeometry(b.createVertices)
			case true => new SimpleGeometry(b.createVertices, b.createNormals)
		}
		stack.top.attach(geometry)
	}

	def box(width: Float, height: Float, depth: Float, l: Int, m: Int, n: Int, texture: Texture, normals: Boolean) {
		val b = new BoxBuilder(width, height, depth, l, m, n)
		val geometry = normals match {
			case false => new SimpleGeometry(b.createVertices, b.createTextureCoordinates, texture)
			case true => new SimpleGeometry(b.createVertices, b.createTextureCoordinates, texture, b.createNormals)
		}
		stack.top.attach(geometry)
	}
	
	def torus(n: Int, R: Float, r: Float, normals: Boolean): Geometry = {
		val b = new TorusBuilder(n, R, r)
		normals match {
			case false => new SimpleGeometry(b.createVertices)
			case true => new SimpleGeometry(b.createVertices, b.createNormals)
		}
	}

	def checkerBoard(n: Int, m: Int, c1: Color, c2: Color) {
		val builder = new CheckerBoardBuilder(n, m, c1, c2)
		stack.top.attach(builder.createCheckerBoard)
	}
	
	def lineStrip[T <: Buffer](vbo: VertexBufferObject[T]) {
		stack.top.attach(new SimpleGeometryVBO(RenderableBuilder.createRenderableVBO(vbo)))
	}

	def lineStrips[T <: Buffer](vbo: VertexBufferObject[T], firsts: IntBuffer, counts: IntBuffer) {
		stack.top.attach(new SimpleGeometryVBO(RenderableBuilder.createRenderableVBO(vbo, firsts, counts)))
	}
	
	def grid(width: Float, height: Float, m: Int, n: Int) {
		val builder = new GridBuilder(width, height, m, n)
		stack.top.attach(builder.createGrid)
	}

	def grid(width: Float, height: Float, m: Int, n: Int, texture: Texture) {
		val builder = new GridBuilder(width, height, m, n)
		stack.top.attach(builder.createGrid(texture))
	}

	def cone(n: Int, m: Int, r: Float, h: Float, normals: Boolean): Geometry = {
		val b = new ConeBuilder(n, m, r, h)
		b.createCone(normals)
	}
	
	def normals(geometry: Geometry): Group = {
		detached {
			depthTest(On)
			light(Off)
			color(JColor.white)
			val b = new NormalFieldBuilder(geometry)
			attach(new SimpleGeometry(b.createNormalLines))
		}
	}

	def bufferedPoint(v: Vertice3D, c: Color): BufferedGeometry = {
        val position = Buffers.newDirectFloatBuffer(Array(v.x, v.y, v.z))
        val color = Buffers.newDirectFloatBuffer(Array(c.r, c.g, c.b, c.a))
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, position),
                              new VertexAttributeObject(COLOR_ATTRIBUTE_INDEX, 4, GL_FLOAT, color)),
            indicesCount = 1,
            primitiveType = GL_POINTS)
	}

	def bufferedLine(v1: Vertice3D, v2: Vertice3D): BufferedGeometry = {
        val positions = Buffers.newDirectFloatBuffer(Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z))
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)),
            indicesCount = 2,
            primitiveType = GL_LINES)
	}
    
	def bufferedTriangle(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D): BufferedGeometry = {
        val positions = Buffers.newDirectFloatBuffer(Array(
                            v1.x, v1.y, v1.z,
                            v2.x, v2.y, v2.z,
                            v3.x, v3.y, v3.z))
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)),
            indicesCount = 3,
            primitiveType = GL_TRIANGLES)
	}
    
	def bufferedTriangle(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D,
                         c1: Color, c2: Color, c3: Color): BufferedGeometry = {
        val positions = Buffers.newDirectFloatBuffer(Array(
                            v1.x, v1.y, v1.z,
                            v2.x, v2.y, v2.z,
                            v3.x, v3.y, v3.z))
        val colors = Buffers.newDirectFloatBuffer(Array(
                            c1.r, c1.g, c1.b, c1.a,
                            c2.r, c2.g, c2.b, c2.a,
                            c3.r, c3.g, c3.b, c3.a))
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions),
                              new VertexAttributeObject(COLOR_ATTRIBUTE_INDEX, 4, GL_FLOAT, colors)),
            indicesCount = 3,
            primitiveType = GL_TRIANGLES)
	}
    
	def bufferedQuad(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D): BufferedGeometry = {
        val positions = Buffers.newDirectFloatBuffer(Array(
                            v1.x, v1.y, v1.z,
                            v2.x, v2.y, v2.z,
                            v3.x, v3.y, v3.z,
                            
                            v1.x, v1.y, v1.z,
                            v3.x, v3.y, v3.z,
                            v4.x, v4.y, v4.z))
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)),
            indicesCount = 6,
            primitiveType = GL_TRIANGLES)
	}
    
	def bufferedQuad(v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D,
                     c1: Color, c2: Color, c3: Color, c4: Color): BufferedGeometry = {
        val positions = Buffers.newDirectFloatBuffer(Array(
                            v1.x, v1.y, v1.z,
                            v2.x, v2.y, v2.z,
                            v3.x, v3.y, v3.z,
                            
                            v1.x, v1.y, v1.z,
                            v3.x, v3.y, v3.z,
                            v4.x, v4.y, v4.z))
        val colors = Buffers.newDirectFloatBuffer(Array(
                            c1.r, c1.g, c1.b, c1.a,
                            c2.r, c2.g, c2.b, c2.a,
                            c3.r, c3.g, c3.b, c3.a,
                            
                            c1.r, c1.g, c1.b, c1.a,
                            c3.r, c3.g, c3.b, c3.a,
                            c4.r, c4.g, c4.b, c4.a))
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions),
                              new VertexAttributeObject(COLOR_ATTRIBUTE_INDEX, 4, GL_FLOAT, colors)),
            indicesCount = 6,
            primitiveType = GL_TRIANGLES)
	}
    
	def bufferedSphere(n: Int, r: Float): BufferedGeometry = {
		val b = new SphereBuilder(n, r)
        val positions = b.createPositions
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)),
            indicesCount = positions.limit / 3,
            primitiveType = GL_TRIANGLES)
	}
    
	def bufferedSphereWithNormals(n: Int, r: Float): BufferedGeometry = {
		val b = new SphereBuilder(n, r)
        val positions = b.createPositions
        val normals = b.createNormals2
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions),
                              new VertexAttributeObject(NORMAL_ATTRIBUTE_INDEX, 3, GL_FLOAT, normals)),
            indicesCount = positions.limit / 3,
            primitiveType = GL_TRIANGLES)
	}
    
	def bufferedTorus(n: Int, R: Float, r: Float): BufferedGeometry = {
		val b = new TorusBuilder(n, R, r)
        val positions = b.createPositions
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)),
            indicesCount = positions.limit / 3,
            primitiveType = GL_TRIANGLES)
	}

	def bufferedTorusWithNormals(n: Int, R: Float, r: Float): BufferedGeometry = {
		val b = new TorusBuilder(n, R, r)
        val positions = b.createPositions
        val normals = b.createNormals2
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions),
                              new VertexAttributeObject(NORMAL_ATTRIBUTE_INDEX, 3, GL_FLOAT, normals)),
            indicesCount = positions.limit / 3,
            primitiveType = GL_TRIANGLES)
	}
    
    def bufferedEllipsoid(n: Int, m: Int, a: Float, b: Float, c: Float): BufferedGeometry = {
        val builder = new EllipsoidBuilder(n, m, a, b, c)
        val positions = builder.createPositions
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)),
            indicesCount = positions.limit / 3,
            primitiveType = GL_TRIANGLES)
    }

    def bufferedEllipsoidWithNormals(n: Int, m: Int, a: Float, b: Float, c: Float): BufferedGeometry = {
        val builder = new EllipsoidBuilder(n, m, a, b, c)
        val positions = builder.createPositions
        val normals = builder.createNormals2
        new BufferedGeometry(
            attributes = List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions),
                              new VertexAttributeObject(NORMAL_ATTRIBUTE_INDEX, 3, GL_FLOAT, normals)),
            indicesCount = positions.limit / 3,
            primitiveType = GL_TRIANGLES)
    }
    
	def bufferedBox(width: Float, height: Float, depth: Float, l: Int, m: Int, n: Int, normals: Boolean): BufferedGeometry = {
		val b = new BoxBuilder(width, height, depth, l, m, n)
        val positions = b.createPositions
		val attributes = normals match {
			case false => List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions))
			case true => List(new VertexAttributeObject(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions),
                              new VertexAttributeObject(NORMAL_ATTRIBUTE_INDEX, 3, GL_FLOAT, b.createNormals2))
		}
        new BufferedGeometry(attributes, positions.limit / 3, GL_TRIANGLES)
	}
    
}
