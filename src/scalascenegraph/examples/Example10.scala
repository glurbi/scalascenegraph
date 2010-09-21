package scalascenegraph.examples

import java.nio._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
import com.jogamp.common.nio._
import scala.math._
import scala.collection.mutable._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example10 extends Example with WorldBuilder {

	val vertices = {
		val amplitude = 10.0f
		val ab = new ArrayBuffer[Float]
		def f(x: Float): Float = { sin(x).asInstanceOf[Float] * amplitude }
		for (sample <- 0 to 10000) {
			val x = sample / 1000.0f;
			ab ++= Vertice(x, f(x), 0.0f).asFloatArray
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray))
	}
	
	
	
	def example =
		world {
			vbo("signal", vertices)
			translation(-5.0f, 0.0f, -5.0f)
			lineStrip("signal")
			showFramesPerSecond
		}
	
}
