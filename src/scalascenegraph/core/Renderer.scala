package scalascenegraph.core

trait Renderer {

	def color(color: Color)
    def clearColor(color: Color)
    def clear
    
    def flush
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    
    def triangle(v1: Vertex, v2: Vertex, v3: Vertex)
    def triangle(v1: Vertex, c1: Color, v2: Vertex, c2: Color, v3: Vertex, c3: Color)

    def quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex)
    def quad(v1: Vertex, v2: Vertex, v3: Vertex, v4: Vertex, color: Color)
    
    def translate(x: Float, y: Float, z: Float)
}
