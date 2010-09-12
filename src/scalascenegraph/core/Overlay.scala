package scalascenegraph.core

import java.io._
import java.util.concurrent._
import java.awt._
import java.awt.image._
import java.nio._
import javax.imageio._

import scalascenegraph.core.Predefs._

class Overlay(val parent: Node) extends Node

class ImageOverlay(parent: Node,
			  	   var x: Int = 0,
		           var y: Int = 0,
 		           var width: Int,
		           var height: Int,
		           var imageType: ImageType,
		           var rawImage: ByteBuffer)
extends Overlay(parent) {

	override def doRender(context: Context) {
		if (rawImage != null) {
			context.renderer.drawImage(x, y, width, height, imageType, rawImage)
		}
	}
	
}

class TextOverlay(parent: Node,
			  	  var x: Int = 0,
		          var y: Int = 0,
		          var font: Font,
		          var text: String)
extends Overlay(parent) {

	override def doRender(context: Context) {
		context.renderer.drawText(x, y, font, text)
	}
	
}
