package scalascenegraph.core

import java.io._
import java.util._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core.Predefs._

object Camera {
	
	def get(config: InputStream): Camera = {
		try {
			val props = new Properties
			props.load(config)
			val left = props.getProperty("camera.clipping.volume.left").toDouble
			val right = props.getProperty("camera.clipping.volume.right").toDouble
			val bottom = props.getProperty("camera.clipping.volume.bottom").toDouble
			val top = props.getProperty("camera.clipping.volume.top").toDouble
			val near = props.getProperty("camera.clipping.volume.near").toDouble
			val far = props.getProperty("camera.clipping.volume.far").toDouble
			val clippingVolume = new ClippingVolume(left, right, bottom, top, near, far)
			props.get("camera.projection.type") match {
			    case "perspective" => new PerspectiveCamera(clippingVolume)
			    case "parallel" => new ParallelCamera(clippingVolume)
			    case _ => throw new RuntimeException("Missing or invalid property camera.projection.type")
			}
		} finally {
			config.close
		}
	}
	
	def get(projectionType: ProjectionType, clippingVolume: ClippingVolume): Camera = {
		projectionType match {
			case Perspective => new PerspectiveCamera(clippingVolume)
			case Parallel => new ParallelCamera(clippingVolume)
		}
	}
}

abstract class Camera(val clippingVolume: ClippingVolume) extends Node {
	val projectionType: ProjectionType
}

class ClippingVolume(
    var left: Double,
    var right: Double,
    var bottom: Double,
    var top: Double,
    var near: Double,
    var far: Double)

class PerspectiveCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
	val projectionType = Perspective
	
    def render(context: Context) {
        import clippingVolume._
        import context.gl
        gl.glMatrixMode(GL_PROJECTION)
        gl.glLoadIdentity
        gl.glFrustum(left, right, bottom, top, near, far)
        gl.glMatrixMode(GL_MODELVIEW)
        gl.glLoadIdentity
    }
}

class ParallelCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
	val projectionType = Parallel
	
    def render(context: Context) {
        import clippingVolume._
        import context.gl
        gl.glMatrixMode(GL_PROJECTION)
        gl.glLoadIdentity
        gl.glOrtho(left, right, bottom, top, near, far)
        gl.glMatrixMode(GL_MODELVIEW)
        gl.glLoadIdentity
    }
}
