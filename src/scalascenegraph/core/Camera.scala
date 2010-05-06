package scalascenegraph.core

abstract class Camera extends Node {

}

class PerspectiveCamera(
  
    var left: Double,
    var right: Double,
    var bottom: Double,
    var top: Double,
    var near: Double,
    var far: Double)

extends Camera {
  
    def render(renderer: Renderer) {
        renderer.perspective(left, right, bottom, top, near, far);
    }
    
}
