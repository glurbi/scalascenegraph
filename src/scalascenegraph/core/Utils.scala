package scalascenegraph.core

import java.awt._
import java.awt.geom._
import java.awt.image._
import java.awt.color._
import scala.math._

import scalascenegraph.core.Predefs._

object Utils {

	def vector(v1: Vertice, v2: Vertice): Vector =
		Vector(v2.x - v1.x, v2.y - v1.y, v2.z -v1.z)
	
	def crossProduct(u: Vector, v: Vector): Vector = {
		Vector(u.y*v.z - u.z*v.y, u.z*v.x - u.x*v.z, u.x*v.y - u.y*v.x)
	}
	
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

}