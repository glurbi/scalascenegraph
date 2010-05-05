package scalascenegraph.core

trait Renderer {

    def clearColor(color: Color)
    def clear()
    
    def flush
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    
}
