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
		
		for (latStep <- 0 to steps) {
			for (lonStep <- 0 to 2*steps) {
				
				val teta = latStep * stepAngle
				val phi = lonStep * stepAngle
				
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
				
			}
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
