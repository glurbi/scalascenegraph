package scalascenegraph.core

trait Renderer {

    def clearColor(color: Color)
    def clear
    
    def flush
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    
    def triangle(v1: Vertex, v2: Vertex, v3: Vertex)
    def quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex)
    
    def translate(x: Float, y: Float, z: Float)
}
