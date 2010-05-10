package scalascenegraph.core

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
    def render(renderer: Renderer) {
        import clippingVolume._
        renderer.perspective(left, right, bottom, top, near, far);
    }
}

class ParallelCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
    def render(renderer: Renderer) {
        import clippingVolume._
        renderer.ortho(left, right, bottom, top, near, far);
    }
}
