package scalascenegraph.core

import java.io._
import java.nio._
import javax.imageio._
import scala.collection.mutable._

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

	var shaderId: ShaderId = _
	
	override def prepare(context: Context) {
		shaderId = context.renderer.newShader(shaderType)
		context.renderer.shaderSource(shaderId, source)
		println(context.renderer.compileShader(shaderId))
	}
	
	override def dispose(context: Context) {
		context.renderer.freeShader(shaderId)
	}
	
}

class Program(shaderIds: List[Shader]) extends Resource {
	
	var programId: ProgramId = _
	
	override def prepare(context: Context) {
		val renderer = context.renderer
		programId = renderer.newProgram
		shaderIds.foreach { shader => renderer.attachShader(programId, shader.shaderId) }
		println(renderer.linkProgram(programId))
		println(renderer.validateProgram(programId))
	}
	
	override def dispose(context: Context) {
		context.renderer.freeProgram(programId)
	}
	
}

class Texture(in: InputStream) extends Resource {

	var textureId: TextureId = _
	
	override def prepare(context: Context) {
		val image = ImageIO.read(in)
		textureId = context.renderer.newTexture(image)
	}
	
	override def dispose(context: Context) {
		context.renderer.freeTexture(textureId)
	}
	
}

class Character(val char: Char, val width: Int, val height: Int, val bitmap: ByteBuffer)

class Font(val characters: Map[Char, Character]) extends Resource
