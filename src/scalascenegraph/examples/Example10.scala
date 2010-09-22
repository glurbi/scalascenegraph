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
	
	val nofSamples = 10000
	val nofSignals = 2000
	val amplitude = 1.0f
	val speed = 10.0f
	
	def f(x: Float): Float = { sin(x * speed).asInstanceOf[Float] * amplitude }

	val firsts = Buffers.newDirectIntBuffer(nofSignals)
	val counts = Buffers.newDirectIntBuffer(nofSignals)
	
	val vertices = {
		val buf = Buffers.newDirectFloatBuffer(nofSignals * nofSamples * 3)
		for (signal <- 0 until nofSignals) {
			val shift = signal * 0.1f
			for (sample <- 0 until nofSamples) {
				val x = sample / 1000.0f;
				buf.put(x)
				buf.put(f(x) + shift)
				buf.put(0.0f)
			}
			firsts.put(signal * nofSamples)
			counts.put(nofSamples)
		}
		buf.flip
		firsts.flip
		counts.flip
		Vertices(buf)
	}
	
	def example =
		world {
			vbo("signals", vertices)
			rotation(90.0f, 0.0f, 0.0f, 1.0f)
			translation(-5.0f, 0.0f, -8.0f)
			lineStrips("signals", firsts, counts)
			showFramesPerSecond
		}
	
}
