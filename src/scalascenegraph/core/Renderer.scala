package scalascenegraph.core

trait Renderer {

	def color(color: Color)
    def clearColor(color: Color)
    def clear
    
    def flush
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    
    def triangle(vertices: Array[Float])
    def triangle(vertices: Array[Float], colors: Array[Float])

    def quad(vertices: Array[Float])
    def quad(vertices: Array[Float], colors: Array[Float])
    
    def translate(x: Float, y: Float, z: Float)
}
