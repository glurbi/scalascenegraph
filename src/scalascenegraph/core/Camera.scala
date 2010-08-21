package scalascenegraph.core

import java.io._
import java.util._

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
	
}

abstract class Camera(val clippingVolume: ClippingVolume) extends Node {
	
	private var xT: Int = 0 // translation along x axis
	private var yT: Int = 0 // translation along y axis
	private var zT: Int = 0 // translation along z axis
	private var xR: Int = 0 // rotation around x axis
	private var yR: Int = 0 // rotation around y axis
	private var zR: Int = 0 // rotation around z axis
	
	protected def positionCamera(context: Context) {
		val tFac = 0.1f
		val rFac = 1.0f
		if (!context.controlKeyPressed && !context.shiftKeyPressed) {
			if (context.upKeyPressed) { zT += 1 }
			if (context.downKeyPressed) { zT -= 1 }
			if (context.leftKeyPressed) { xT += 1 }
			if (context.rightKeyPressed) { xT -= 1 }
		} else if (context.controlKeyPressed && !context.shiftKeyPressed) {
			if (context.upKeyPressed) { xR += 1 }
			if (context.downKeyPressed) { xR -= 1 }
			if (context.leftKeyPressed) { yR += 1 }
			if (context.rightKeyPressed) { yR -= 1 }
		} else if (!context.controlKeyPressed && context.shiftKeyPressed) {
			if (context.upKeyPressed) { yT -= 1 }
			if (context.downKeyPressed) { yT += 1 }
			if (context.leftKeyPressed) { zR += 1 }
			if (context.rightKeyPressed) { zR -= 1 }
		}
		if (context.spaceKeyPressed) {
			xT = 0; yT = 0; zT = 0; xR = 0; yR = 0; zR = 0
		}
		context.renderer.translate(xT * tFac, yT * tFac, zT * tFac)
		context.renderer.rotate(xR * rFac, 1.0f, 0.0f, 0.0f)
		context.renderer.rotate(yR * rFac, 0.0f, 1.0f, 0.0f)
		context.renderer.rotate(zR * rFac, 0.0f, 0.0f, 1.0f)
	}
	
}

class ClippingVolume(
    val left: Double,
    val right: Double,
    val bottom: Double,
    val top: Double,
    val near: Double,
    val far: Double)

class PerspectiveCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
    def doRender(context: Context) {
        import clippingVolume._
        context.renderer.perspective(left, right, bottom, top, near, far);
        positionCamera(context)
    }
}

class ParallelCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
    def doRender(context: Context) {
        import clippingVolume._
        context.renderer.ortho(left, right, bottom, top, near, far);
        positionCamera(context)
    }
}
