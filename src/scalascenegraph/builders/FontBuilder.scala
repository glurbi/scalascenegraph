package scalascenegraph.builders

import java.io._
import java.util.concurrent._
import java.awt.geom._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
import java.nio._
import javax.imageio._
import scala.collection.mutable._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._

object FontBuilder {
	
	/**
	 * Creates a Font object based on the AWT Font given in parameter.
	 */
	def create(parent: Node, jfont: JFont): Font = {
		val characters = Map.empty[Char, Character]
		val img = new BufferedImage(50, 50, BufferedImage.TYPE_3BYTE_BGR)
		val g2d = img.createGraphics
		g2d.setFont(jfont)
		val metrics = g2d.getFontMetrics
		for (char <- 32 to 255) {
			val s = new String(Array(char.toChar))
			val ascent = metrics.getAscent
			val descent = metrics.getDescent
			val height = ascent + descent
			val width = metrics.charWidth(char.toChar)
			val xmin = 0
			val xmax = width-1
			val ymin = img.getHeight-1-height
			val ymax = img.getHeight-1
			g2d.setColor(JColor.black)
			g2d.fillRect(0, 0, img.getWidth, img.getHeight)
			g2d.setColor(JColor.white)
			g2d.drawString(s, 0, img.getHeight-1-descent)
			val characterImage = img.getSubimage(xmin, ymin, width, height)
			val invertedImage = inverseImage(characterImage)
			val character = makeCharacter(char.toChar, invertedImage)
			characters += char.toChar -> character
		}
		g2d.dispose
		new Font(parent, characters)
	}
	
	private def makeCharacter(char: Char, image: BufferedImage): Character = {
		new Character(char, image.getWidth, image.getHeight, makeBitmap(image))
	}
	
	// TODO: should use 4 byte alignment (opengl default)
	private def makeBitmap(image: BufferedImage): ByteBuffer = {
		val byteWidth = calculateByteWidth(image.getWidth)
		val buffer = ByteBuffer.allocateDirect(byteWidth * image.getHeight)
		clearBuffer(buffer)
		for (y <- 0 to image.getHeight-1) {
			for (x <- 0 to image.getWidth-1) {
				val index = byteWidth * 8 * y + x
				// alpha value never at 0 so we need to apply a mask
				setBit(if ((image.getRGB(x, y) & 0xFFFFFF) != 0) 1 else 0, index, buffer)
			}
		}
		buffer
	}

	// the width in number of bytes
	// each bitmap row start on a byte alignment
	def calculateByteWidth(bitWidth: Int): Int = {
		if (bitWidth % 8 == 0) bitWidth / 8 else bitWidth / 8 + 1
	}
	
}