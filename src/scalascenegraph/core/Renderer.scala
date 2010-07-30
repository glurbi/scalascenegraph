package scalascenegraph.core

import scalascenegraph.core.Predefs._

trait Renderer {

	def color(color: Color)
    def clearColor(color: Color)
	def enableDepthTest
	def enableCullFace
    def clear
    def flush

    def pushPolygonMode
    def setPolygonMode(face: Face, mode: DrawingMode)
    def popPolygonMode
    
    def pushMatrix
    def popMatrix
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double)
    
    def triangle(vertices: Array[Float])
    def triangle(vertices: Array[Float], colors: Array[Float])

    def quad(vertices: Array[Float])
    def quad(vertices: Array[Float], colors: Array[Float])
    def quads(vertices: Array[Float])
    def quads(vertices: Array[Float], colors: Array[Float])
    
    def translate(x: Float, y: Float, z: Float)
	def rotate(angle: Float, x: Float, y: Float, z: Float)
	
	def context: Context
}
