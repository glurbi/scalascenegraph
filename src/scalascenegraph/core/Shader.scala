package scalascenegraph.core

import java.io._
import java.nio._
import java.awt.image._
import javax.imageio._
import javax.media.opengl._

import scalascenegraph.core.Predefs._

class Shader(parent: Node, shaderType: ShaderType, source: String) extends Node(parent) {

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
