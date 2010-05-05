package scalascenegraph.core

abstract class Camera extends Node {

}

class PerspectiveCamera(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
extends Camera {
  
    def render(renderer: Renderer) {
        renderer.perspective(left, right, bottom, top, near, far);
    }
    
}
