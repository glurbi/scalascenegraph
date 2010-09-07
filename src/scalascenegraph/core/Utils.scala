package scalascenegraph.core

import java.awt._
import java.awt.geom._
import java.awt.image._
import java.awt.color._
import java.nio._
import scala.math._

import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

object Utils {

	/**
	 * @return a vector created from two points in space.
	 */
	def vector(v1: Vertice, v2: Vertice): Vector =
		Vector(v2.x - v1.x, v2.y - v1.y, v2.z -v1.z)
	
	/**
	 * @return the cross product of two vectors.
	 */
	def crossProduct(u: Vector, v: Vector): Vector = {
		Vector(u.y*v.z - u.z*v.y, u.z*v.x - u.x*v.z, u.x*v.y - u.y*v.x)
	}
	
	/**
	 * @return a vector that is the normalized instance of the one given in parameter.
	 */
	def normalize(v: Vector): Vector = {
		implicit def doubleToFloat(d: Double): Float = d.asInstanceOf[Float]
		val norm = sqrt(v.x*v.x + v.y*v.y + v.z*v.z)
		Vector(v.x / norm, v.y / norm, v.z / norm)
	}
	
	/**
	 * Convert any BufferedImage object into a BufferedImage that complies with
	 * scalascenegraph supported color models (RGB and RGBA) and transform the
	 * image for an opengl like coordinate system (invert the y axis direction).
	 */
	def convertImage(source: BufferedImage): BufferedImage = {
		val raster = Raster.createInterleavedRaster(
				DataBuffer.TYPE_BYTE,
				source.getWidth,
				source.getHeight,
				4,    // bands
				null) // location
		val colorModel = new ComponentColorModel(
				ColorSpace.getInstance(ColorSpace.CS_sRGB),
				Array(8, 8, 8, 8),
				true,
				false,
				Transparency.TRANSLUCENT,
				DataBuffer.TYPE_BYTE)
		val target = new BufferedImage(colorModel, raster, false, null)
		val g2d = target.createGraphics
		val transform = new AffineTransform
		transform.translate(0, source.getHeight)
		transform.scale(1.0, -1.0)
		g2d.transform(transform)
		g2d.drawImage(source, null, 0, 0)
		g2d.dispose
		target
	}

	/**
	 * Creates a BufferedImage that is the inverse (y axis direction) of the
	 * one given in parameter. 
	 */
	def inverseImage(source: BufferedImage): BufferedImage = {
		val target = new BufferedImage(source.getWidth, source.getHeight, source.getType)
		val g2d = target.createGraphics
		val transform = new AffineTransform
		transform.translate(0, target.getHeight)
		transform.scale(1.0, -1.0)
		g2d.transform(transform)
		g2d.drawImage(source, null, 0, 0)
		g2d.dispose
		target
	}
	
	/**
	 * Creates a ByteBuffer object that contains the raw data of the original
	 * image in an RGB or RGBA format, as appropriate.
	 */
	def makeDirectByteBuffer(image: BufferedImage): ByteBuffer = {
		val converted = convertImage(image)
		val data = image.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
		val buffer = ByteBuffer.allocateDirect(data.length)
		buffer.put(data, 0, data.length)
		buffer.rewind
		buffer
	}
	
	/**
	 * @return the bit value at the bit position in the ByteBuffer.
	 */
	def getBit(bitPos: Int, buf: ByteBuffer): Int = {
		val bytePos = bitPos / 8
		val byte = buf.get(bytePos)
		val shift = 7 - bitPos % 8
		val mask = 1 << shift
		val bit = (byte & mask) >> shift
		bit
	}
	
	/**
	 * Set the bit value at the bit position in the ByteBuffer.
	 */
	def setBit(value: Int, bitPos: Int, buf: ByteBuffer) {
		val bytePos = bitPos / 8
		val mask = value << (7 - bitPos % 8)
		val oldb = buf.get(bytePos)
		val newb = (oldb & ~mask) | mask
		buf.put(bytePos, newb.asInstanceOf[Byte])
	}
	

	/**
	 * Print on the console the content of the bitmap, as zeroes and ones.
	 */
	def dumpBitmap(width: Int, height: Int, bitmap: ByteBuffer) {
		val byteWidth = FontBuilder.calculateByteWidth(width)
		for (y <- height-1 to 0 by -1) {
			for (x <- 0 to width-1) {
				scala.Console.print(Utils.getBit(y * byteWidth * 8 + x, bitmap))
			}
			println
		}
		println
	}

	/**
	 * Makes sure a buffer only contains zeroes (0)
	 */
	def clearBuffer(buffer: ByteBuffer) {
		for (i <- 0 until buffer.capacity) { buffer.put(0.toByte) }
		buffer.rewind
	}
	
}
