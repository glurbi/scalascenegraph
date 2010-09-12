package scalascenegraph.core

import java.io._
import java.nio._
import java.awt.image._
import javax.imageio._
import javax.media.opengl._

import scalascenegraph.core.Predefs._

class Shader(shaderType: ShaderType, source: String) extends Node {

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

class Program(shaderIds: List[Shader]) extends Node {
	
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
