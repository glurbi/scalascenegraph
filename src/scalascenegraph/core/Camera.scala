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

abstract class Camera(val clippingVolume: ClippingVolume) extends Node

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
    def doRender(renderer: Renderer, context: Context) {
        import clippingVolume._
        renderer.perspective(left, right, bottom, top, near, far);
    }
}

class ParallelCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
    def doRender(renderer: Renderer, context: Context) {
        import clippingVolume._
        renderer.ortho(left, right, bottom, top, near, far);
    }
}
