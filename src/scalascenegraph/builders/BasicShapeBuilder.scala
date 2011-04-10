package scalascenegraph.builders

import java.io._
import java.nio._
import java.awt.image._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import scala.collection.mutable.Stack
import com.jogamp.common.nio._
import javax.media.opengl._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL4._
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

trait BasicShapeBuilder extends GraphBuilder with StateBuilder {

	def point(gl: GL4, v: Vertice3D, color: Color) {
        val builder = new GeometryBuilder
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, Array(v.x, v.y, v.z))
                              .addAtribute(COLOR_ATTRIBUTE_INDEX, 3, GL_FLOAT, color.rgba)
                              .setPrimitiveType(GL_POINTS)
                              .setVertexCount(1)
                              .build(gl)
		stack.top.attach(geometry)
	}
	
	def line(gl: GL4, v1: Vertice3D, v2: Vertice3D)
	{
        val builder = new GeometryBuilder
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z))
                              .setPrimitiveType(GL_LINES)
                              .setVertexCount(2)
                              .build(gl)
		stack.top.attach(geometry)
	}

	def triangle(gl: GL4, v1: Vertice3D, v2: Vertice3D, v3: Vertice3D)	{
        val builder = new GeometryBuilder
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z))
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(3)
                              .build(gl)
		stack.top.attach(geometry)
	}

	def triangle(gl: GL4, v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, c: Color)
	{
        val builder = new GeometryBuilder
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z))
                              .addAtribute(COLOR_ATTRIBUTE_INDEX, 3, GL_FLOAT, Array(c.r, c.g, c.b, c.a, c.r, c.g, c.b, c.a, c.r, c.g, c.b, c.a))
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(3)
                              .build(gl)
		stack.top.attach(geometry)
	}
	
	def triangle(gl: GL4, v1: Vertice3D, v2: Vertice3D, v3: Vertice3D,
    		     c1: Color, c2: Color, c3: Color)
	{
        val builder = new GeometryBuilder
		val positions = Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z)
		val colors = Array(c1.r, c1.g, c1.b, c1.a, c2.r, c2.g, c2.b, c2.a, c3.r, c3.g, c3.b, c3.a)
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
                              .addAtribute(COLOR_ATTRIBUTE_INDEX, 3, GL_FLOAT, colors)
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(3)
                              .build(gl)
		stack.top.attach(geometry)
	}
	
	def quad(gl: GL4, v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D, c: Color) {
        val builder = new GeometryBuilder
        val positions = Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z,
                              v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v4.x, v4.y, v4.z)
        val colors = Array(c.r, c.g, c.b, c.a, c.r, c.g, c.b, c.a, c.r, c.g, c.b, c.a,
                           c.r, c.g, c.b, c.a, c.r, c.g, c.b, c.a, c.r, c.g, c.b, c.a)
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
                              .addAtribute(COLOR_ATTRIBUTE_INDEX, 3, GL_FLOAT, colors)
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(6)
                              .build(gl)
		stack.top.attach(geometry)
	}
	
	def quad(gl: GL4, v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D) {
        val builder = new GeometryBuilder
        val positions = Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z,
                              v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v4.x, v4.y, v4.z)
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(6)
                              .build(gl)
		stack.top.attach(geometry)
	}

	def quad(gl: GL4, v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D,
    		     c1: Color, c2: Color, c3: Color, c4: Color)
	{
        val builder = new GeometryBuilder
        val positions = Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z,
                              v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v4.x, v4.y, v4.z)
        val colors = Array(c1.r, c1.g, c1.b, c1.a, c2.r, c2.g, c2.b, c2.a, c3.r, c3.g, c3.b, c3.a,
                           c1.r, c1.g, c1.b, c1.a, c2.r, c2.g, c2.b, c2.a, c4.r, c4.g, c4.b, c4.a)
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
                              .addAtribute(COLOR_ATTRIBUTE_INDEX, 3, GL_FLOAT, colors)
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(6)
                              .build(gl)
		stack.top.attach(geometry)
	}
	
	def quad(gl: GL4, v1: Vertice3D, v2: Vertice3D, v3: Vertice3D, v4: Vertice3D, texture: Texture) {
        val builder = new GeometryBuilder
        val positions = Array(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z,
                              v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v4.x, v4.y, v4.z)
        val texCoords = Array(0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f,
        					  0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f)
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
                              .addAtribute(TEXTURE_COORDINATE_ATTRIBUTE_INDEX, 3, GL_FLOAT, texCoords)
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(6)
                              .build(gl)
		stack.top.attach(geometry)
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
		val cubeBuilder = new CubeBuilder
		val positions = cubeBuilder.createPositions
        val builder = new GeometryBuilder
        val geometry = builder.addAtribute(POSITION_ATTRIBUTE_INDEX, 3, GL_FLOAT, positions)
                              .addAtribute(TEXTURE_COORDINATE_ATTRIBUTE_INDEX, 3, GL_FLOAT, texCoords)
                              .setPrimitiveType(GL_TRIANGLES)
                              .setVertexCount(36)
                              .build(gl)
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

}
