package scalascenegraph.core

import java.nio._

import scalascenegraph.core.Predefs._

trait Renderer {

	def color(color: Color)
    def clearColor(color: Color)
	def enableDepthTest
	def disableDepthTest
    def clear
    def flush
    
    def pushCullFace
    def enableCullFace
    def disableCullFace
    def popCullFace

    def pushFrontFace
    def setFrontFace(frontFace: FrontFace)
	def popFrontFace
	
    def pushPolygonMode
    def setPolygonMode(face: Face, mode: DrawingMode)
    def popPolygonMode
    
    def pushLineState
    def setLineWidth(width: Float)
    def popLineState
    
    def pushLightState
    def setLightState(mode: OnOffState)
	def setAmbientLight(intensity: Intensity)
	def setMaterial(face: Face, lightType: LightType, color: Color)
	def enableLight(lightType: LightType, position: Position, color: Color)
    def popLightState
    
    def pushMatrix
    def popMatrix
    
    def ortho(left: Double, right: Double,
    		  bottom: Double, top: Double,
    		  near: Double, far: Double)
	
    def perspective(left: Double, right: Double,
    		        bottom: Double, top: Double,
    		        near: Double, far: Double)
    
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice)
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice,
    		     c1: Color, c2: Color, c3: Color)

    def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice)
    def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice,
    		 c1: Color, c2: Color, c3: Color, c4: Color)
	
    def quads(vertices: Vertices)
    def quads(vertices: Vertices, color: Color)
    def quads(vertices: Vertices, colors: Colors)
	def quads(vertices: Vertices, normals: Normals)
    
    def translate(x: Float, y: Float, z: Float)
	def rotate(angle: Float, x: Float, y: Float, z: Float)
	
}
