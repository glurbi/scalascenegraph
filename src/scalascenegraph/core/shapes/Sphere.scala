package scalascenegraph.core

import java.nio._
import scala.math._
import scala.collection.mutable._
import com.jogamp.common.nio._

import scalascenegraph.core.Predefs._

class Sphere(steps: Int) extends Node {

	protected val vertices = {
		val ab = new ArrayBuffer[Float]
		val stepAngle = Pi / steps
		
		// ideally we should use a for loop with a NumericRange (instead of the while loop)
		// however it uses BigDecimal under the hood, which can lead to
		// java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
		// Maybe there is a nice way to express the range, but I don't know it...
		
		var phi = 0.0
		while (phi < 2.0 * Pi) {
			
			var teta = 0.0
			while (teta < Pi - stepAngle) {
				
				ab += (sin(teta) * cos(phi)).asInstanceOf[Float]
				ab += (sin(teta) * sin(phi)).asInstanceOf[Float]
				ab += (cos(teta)).asInstanceOf[Float]
				
				ab += (sin(teta+stepAngle) * cos(phi)).asInstanceOf[Float]
				ab += (sin(teta+stepAngle) * sin(phi)).asInstanceOf[Float]
				ab += (cos(teta+stepAngle)).asInstanceOf[Float]
				
				ab += (sin(teta+stepAngle) * cos(phi+stepAngle)).asInstanceOf[Float]
				ab += (sin(teta+stepAngle) * sin(phi+stepAngle)).asInstanceOf[Float]
				ab += (cos(teta+stepAngle)).asInstanceOf[Float]
				
				ab += (sin(teta) * cos(phi+stepAngle)).asInstanceOf[Float]
				ab += (sin(teta) * sin(phi+stepAngle)).asInstanceOf[Float]
				ab += (cos(teta)).asInstanceOf[Float]
				
				teta += stepAngle
			}
			phi += stepAngle
		}
		Vertices(Buffers.newDirectFloatBuffer(ab.toArray))
	}
	
	protected val normals = Normals(vertices.floatBuffer)
	
    def doRender(context: Context) {
        context.renderer.quads(vertices, normals)
    }

}

class UnicoloredSphere(steps: Int, color: Color) extends Sphere(steps) {

    override def doRender(context: Context) {
        context.renderer.quads(vertices, color)
    }
    
}


class ColoredSphere(steps: Int, colors: Colors) extends Sphere(steps) {

    override def doRender(context: Context) {
        context.renderer.quads(vertices, colors)
    }
    
}
