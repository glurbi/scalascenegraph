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
	
	private var zInc: Int = 0
	private var xInc: Int = 0
	
	protected def positionCamera(context: Context) {
		if (context.upKeyPressed) { zInc += 1 }
		if (context.downKeyPressed) { zInc -= 1 }
		if (context.leftKeyPressed) { xInc += 1 }
		if (context.rightKeyPressed) { xInc -= 1 }
		context.renderer.translate(0.1f * xInc, 0.0f, 0.1f * zInc)
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
    }
}
