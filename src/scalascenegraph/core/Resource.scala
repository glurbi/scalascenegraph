package scalascenegraph.core

import java.io._
import java.nio._
import javax.imageio._
import scala.collection.mutable._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core.Predefs._

/**
 * A class implementing the resource trait has the responsibility to load and
 * free renderer or system resources, that will be used for the lifetime of the
 * scene graph they are attached to.
 */
trait Resource {

	/**
	 * Called once only, during scene graph initialisation.
	 * Can be used to load resources (textures, bitmaps, ...)
	 */
	def prepare(context: Context) {}
	
	/**
	 * Called once when the scene graph is not used any longer.
	 * Resources should be freed here.
	 */
	def dispose(context: Context) {}
	
}

class Shader(shaderType: ShaderType, source: String) extends Resource {

	var id: ShaderId = _
	
	override def prepare(context: Context) {
		id = context.gl.glCreateShader(shaderType)
		context.gl.glShaderSource(id, 1, Array(source), Array(source.size), 0)
		val log = new Array[Byte](8192)
		val length = Array(0)
		context.gl.glCompileShader(id)
		context.gl.glGetShaderInfoLog(id, log.size, length, 0, log, 0)
		println(new String(log, 0, length(0)))
	}
	
	override def dispose(context: Context) {
		context.gl.glDeleteShader(id)
	}
	
}

class Program(shaderIds: List[Shader]) extends Resource {
	
	var id: ProgramId = _
	
	override def prepare(context: Context) {
		val renderer = context.renderer
		id = context.gl.glCreateProgram
		shaderIds.foreach { shader => renderer.gl.glAttachShader(id, shader.id) }
		
		val log = new Array[Byte](8192)
		val length = Array(0)
		
		context.gl.glLinkProgram(id)
		context.gl.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
		println(new String(log, 0, length(0)))
		
		context.gl.glValidateProgram(id)
		context.gl.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
		println(new String(log, 0, length(0)))
	}
	
	override def dispose(context: Context) {
		context.gl.glDeleteProgram(id)
	}
	
}

class Uniform(program: Program, name: String) extends Resource {
	
	var id: UniformId = _
	var value: Any = _

	override def prepare(context: Context) {
		id = context.gl.glGetUniformLocation(program.id, name)
	}
	
}

class Texture(in: InputStream) extends Resource {

	var id: TextureId = _
	
	override def prepare(context: Context) {
		import context.gl
		val image = ImageIO.read(in)
		gl.glEnable(GL_TEXTURE_2D)
		val buffer = Utils.makeDirectByteBuffer(image)
		val textureIds = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder).asIntBuffer // TODO: hardcoded value...
		gl.glGenTextures(1, textureIds)
		id = textureIds.get(0)
		gl.glBindTexture(GL_TEXTURE_2D, id)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
		image.getColorModel.hasAlpha match {
			case false => gl.glTexImage2D(GL_TEXTURE_2D, 0, 3, image.getWidth, image.getHeight, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer)
			case true => gl.glTexImage2D(GL_TEXTURE_2D, 0, 4, image.getWidth, image.getHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
		}
	}
	
	override def dispose(context: Context) {
		val textureIds = ByteBuffer.allocateDirect(4).asIntBuffer // TODO: hardcoded value...
		textureIds.put(0, id)
		context.gl.glDeleteTextures(1, textureIds)
	}
	
}

class Character(val char: Char, val width: Int, val height: Int, val bitmap: ByteBuffer)

class Font(val characters: Map[Char, Character]) extends Resource

class VertexBufferObject(vertices: Vertices) extends Resource {
	
	var vertexBufferObjectId: VertexBufferObjectId = _
	var count: Int = _
	
	override def prepare(context: Context) {
		val ids = Array[Int](1)
		context.gl.glGenBuffers(1, ids, 0)
		vertexBufferObjectId = VertexBufferObjectId(ids(0))
		context.gl.glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObjectId.id.asInstanceOf[Int])
		context.gl.glBufferData(GL_ARRAY_BUFFER, vertices.count * 3 * 4, vertices.floatBuffer, GL_STATIC_DRAW)
		context.gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
		count = vertices.count
	}
	
	override def dispose(context: Context) {
		val ids = Array(vertexBufferObjectId.id .asInstanceOf[Int])
		context.gl.glDeleteBuffers(1, ids, 0)
	}
	
}
