package scalascenegraph.examples

import java.nio._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
import com.jogamp.common.nio._
import scala.math._
import scala.collection.mutable._
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
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example11 extends Example with WorldBuilder {
	
	
	def example =
		world {
			translation(0.0f, 0.0f, -4.0f)
			polygonMode(GL_FRONT, GL_LINE)
			grid(3.0f, 5.0f, 30, 50)
		}
	
}
