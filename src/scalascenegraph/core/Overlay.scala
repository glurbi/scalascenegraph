package scalascenegraph.core

import java.io._
import java.util.concurrent._
import java.awt._
import java.awt.image._
import java.nio._
import javax.imageio._

import scalascenegraph.core.Predefs._

class Overlay extends Node

class ImageOverlay(var x: Int = 0,
		           var y: Int = 0,
 		           var width: Int,
		           var height: Int,
		           var imageType: ImageType,
		           var rawImage: ByteBuffer)
extends Overlay {

	override def doRender(context: Context) {
		if (rawImage != null) {
			context.renderer.drawImage(x, y, width, height, imageType, rawImage)
		}
	}
	
}

class TextOverlay(var x: Int = 0,
		          var y: Int = 0,
		          var font: Font,
		          var text: String)
extends Overlay {

	override def doRender(context: Context) {
		context.renderer.drawText(x, y, font, text)
	}
	
}
