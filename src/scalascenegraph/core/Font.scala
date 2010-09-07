package scalascenegraph.core

import java.io._
import java.util.concurrent._
import java.awt._
import java.awt.geom._
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
			val character = makeCharacter(char.toChar, revert(img.getSubimage(xmin, ymin, width, height)))
			characters += char.toChar -> character
		}
		g2d.dispose
		new Font(parent, characters)
	}
	
	// TODO: move to the Utils class...
	private def revert(source: BufferedImage): BufferedImage = {
		val target = new BufferedImage(source.getWidth, source.getHeight, BufferedImage.TYPE_3BYTE_BGR)
		val g2d = target.createGraphics
		val transform = new AffineTransform
		transform.translate(0, target.getHeight)
		transform.scale(1.0, -1.0)
		g2d.transform(transform)
		g2d.drawImage(source, null, 0, 0)
		g2d.dispose
		target
	}
	
	private def makeCharacter(char: Char, image: BufferedImage): Character = {
		new Character(char, image.getWidth, image.getHeight, makeBitmap(image))
	}
	
	private def clearBuffer(buffer: ByteBuffer) {
		for (i <- 0 until buffer.capacity) { buffer.put(0.toByte) }
		buffer.rewind
	}
	
	private def makeBitmap(image: BufferedImage): ByteBuffer = {
		val byteWidth = calculateByteWidth(image.getWidth)
		val buffer = ByteBuffer.allocateDirect(byteWidth * image.getHeight)
		clearBuffer(buffer)
		// TODO: do we care of the endianness when working with bytes?
		buffer.order(ByteOrder.nativeOrder)
		//for (y <- image.getHeight-1 to 0 by -1) {
		for (y <- 0 to image.getHeight-1) {
			for (x <- 0 to image.getWidth-1) {
				val index = byteWidth * 8 * y + x
				// alpha value never at 0 so we need to apply a mask
				setBit(if ((image.getRGB(x, y) & 0xFFFFFF) != 0) 1 else 0, index, buffer)
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
	
	// the width in number of bytes
	// each bitmap row start on a byte alignment
	def calculateByteWidth(bitWidth: Int): Int = {
		if (bitWidth % 8 == 0) bitWidth / 8 else bitWidth / 8 + 1
	}
	
	def main(args: Array[String]) {
		val font = Font(null, new JFont("Default", JFont.PLAIN, 20))
		font.characters.get('!') match {
    		case Some(character) => character.dump
    		case None => println("Damn it!")
    	}
	}
	
}

class Character(val char: Char, val width: Int, val height: Int, val bitmap: ByteBuffer) {
	
	// testing purpose
	def dump {
		val byteWidth = Font.calculateByteWidth(width)
		println(char + " " + width + "," + height)
		for (y <- 0 to height-1) {
			for (x <- 0 to width-1) {
				System.out.print(getBit(y * byteWidth * 8 + x, bitmap))
			}
			println
		}
		println
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
