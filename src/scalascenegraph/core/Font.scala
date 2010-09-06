package scalascenegraph.core

import java.io._
import java.util.concurrent._
import java.awt._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
import java.nio._
import javax.imageio._
import scala.collection.mutable._

import scalascenegraph.core.Predefs._

object Font {
	
	/**
	 * Creates a Font object based on the AWT Font given in parameter.
	 */
	def apply(parent: Node, jfont: JFont): Font = {
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
			val character = makeCharacter(char.toChar, img.getSubimage(xmin, ymin, width, height))
			characters += char.toChar -> character
		}
		g2d.dispose
		new Font(parent, characters)
	}
	
	private def makeCharacter(char: Char, image: BufferedImage): Character = {
		new Character(char, image.getWidth, image.getHeight, makeBitmap(image))
	}
	
	private def makeBitmap(image: BufferedImage): ByteBuffer = {
		val buffer = ByteBuffer.allocateDirect(image.getWidth * image.getHeight / 8 + 1)
		buffer.order(ByteOrder.nativeOrder)
		var idx = 0
		for (y <- image.getHeight-1 to 0 by -1) {
			for (x <- 0 to image.getWidth-1) {
				// alpha value never at 0 so we need to apply a mask
				setBit(if ((image.getRGB(x, y) & 0xFFFFFF) != 0) 1 else 0, idx, buffer)
				idx += 1
			}
		}
		buffer		
	}

	private def setBit(value: Int, bitPos: Int, buf: ByteBuffer) {
		val bytePos = bitPos / 8
		val mask = value << (7 - bitPos % 8)
		val oldb = buf.get(bytePos)
		val newb = (oldb & ~mask) | mask
		buf.put(bytePos, newb.asInstanceOf[Byte])
	}
	
	def main(args: Array[String]) {
		val font = Font(null, new JFont("Serif", JFont.PLAIN, 20))
		font.characters.get('Y') match {
    		case Some(character) => character.dump
    		case None => println("Damn it!")
    	}
	}
	
}

class Character(val char: Char, val width: Int, val height: Int, val bitmap: ByteBuffer) {
	
	// testing purpose
	def dump {
		for (y <- height-1 to 0 by -1) {
			for (x <- 0 to width-1) {
				System.out.print(getBit(y * width + x, bitmap))
			}
			println
		}
	}
	
	private def getBit(bitPos: Int, buf: ByteBuffer): Int = {
		val bytePos = bitPos / 8
		val byte = buf.get(bytePos)
		val shift = 7 - bitPos % 8
		val mask = 1 << shift
		val bit = (byte & mask) >> shift
		bit
	}

	
}

class Font(parent: Node, val characters: Map[Char, Character]) extends Node(parent)