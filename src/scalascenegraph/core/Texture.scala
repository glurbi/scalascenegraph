package scalascenegraph.core

import java.io._
import java.nio._
import java.awt.image._
import javax.imageio._
import javax.media.opengl._

import scalascenegraph.core.Predefs._

class Texture(in: InputStream) extends Node {

	var textureId: TextureId = _
	
	override def prepare(context: Context) {
		val image = ImageIO.read(in)
		textureId = context.renderer.newTexture(image)
	}
	
	override def dispose(context: Context) {
		context.renderer.freeTexture(textureId)
	}
	
}
