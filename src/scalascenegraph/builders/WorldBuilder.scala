package scalascenegraph.builders

import java.io._
import java.nio._
import java.awt.image._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import scala.collection.mutable.Stack
import com.jogamp.common.nio._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core._
import scalascenegraph.builders._
import scalascenegraph.core.Predefs._

// TODO: should be some consistency with what has to be attached or not
trait WorldBuilder extends GraphBuilder with StateBuilder with GeometryBuilder {

	def overlay(x: Int, y: Int, image: BufferedImage) {
		val format = image.getColorModel.hasAlpha match {
		    case true => GL_RGBA
		    case false => GL_RGB
		}
		val data = Utils.makeDirectByteBuffer(image)
		stack.top.attach(new ImageOverlay(x, y, image.getWidth, image.getHeight, format, data))
	}

	def overlay(x: Int, y: Int, image: BufferedImage, hook: NodeHook[ImageOverlay]) {
		val format = image.getColorModel.hasAlpha match {
		    case true => GL_RGBA
		    case false => GL_RGB
		}
		val data = Utils.makeDirectByteBuffer(image)
		stack.top.attach(new DynamicNode(hook, new ImageOverlay(x, y, image.getWidth, image.getHeight, format, data)))
	}

	def overlay(x: Int, y: Int, font: Font, text: String) {
		stack.top.attach(new TextOverlay(x, y, font, text))
	}

	def overlay(x: Int, y: Int, font: Font, text: String, hook: NodeHook[TextOverlay]) {
		stack.top.attach(new DynamicNode(hook, new TextOverlay(x, y, font, text)))
	}
	
	def showFramesPerSecond = {
		val defaultFont = FontBuilder.create(new JFont("Default", JFont.PLAIN, 20))
		attach(defaultFont)
		val fpsHook = (o: TextOverlay, c: Context) => {
			o.x = 10
			o.y = c.height - 30
			o.text = "FPS: " + c.frameRate
		}
		overlay(0, 0, defaultFont, "", fpsHook)
	}
	
}
