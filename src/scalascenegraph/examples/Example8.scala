package scalascenegraph.examples

import java.awt._
import java.awt.{Color => JColor}
import java.awt.image._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example8 extends Example with WorldBuilder {

	val centerHook = (o: Overlay, c: Context) => {
		o.x = c.width / 2 - o.width / 2
		o.y = c.height / 2 - o.height / 2
	}
	
	val angleHook = (r: Rotation, c: Context) => {
		r.angle = (c.elapsed / 20.0f) % 360.0f
	}
	
	val image = {
		val img = new BufferedImage(400, 200, BufferedImage.TYPE_4BYTE_ABGR)
		val g2d = img.createGraphics
		val font = new Font(g2d.getFont.getFontName, Font.BOLD, 25)
		val metrics = g2d.getFontMetrics
		val text = "...Overlay..."
		g2d.setFont(font)
		g2d.setColor(new JColor(0.0f, 0.0f, 0.0f, 0.0f))
		g2d.fillRect(0, 0, img.getWidth, img.getHeight)
		g2d.setColor(JColor.red)
		g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
		g2d.drawRoundRect(0, 0, img.getWidth-1, img.getHeight-1, 40, 40)
		// FIXME: not really centered?
		val x = img.getWidth / 2 - metrics.stringWidth(text) / 2
		val y = img.getHeight / 2 + metrics.getHeight / 2
		g2d.drawString(text, x, y)
		g2d.dispose
		img
	}
	
	def example =
		world {
			group {
				cullFace(false)
				translation(0.0f, 0.0f, -1.8f)
				polygonMode(FrontAndBack, Line)
				rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
				cube
			}
			overlay(0, 0, image, centerHook)
		}
	
}
