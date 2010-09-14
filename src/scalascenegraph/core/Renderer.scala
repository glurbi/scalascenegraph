package scalascenegraph.core

import java.nio._
import java.awt.image._

import scalascenegraph.core.Predefs._

trait Renderer {

	def pushColorState
	def popColorState
	def setColor(color: Color)
    def clearColor(color: Color)
	def enableBlending
	def disableBlending
    def clear
    def flush
    
    def pushDepthTestState
    def enableDepthTest
    def disableDepthTest
    def popDepthTestState
    
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
    def setLightState(state: OnOffState)
    def setLightState(instance: LightInstance, state: OnOffState)
	def setAmbientLight(intensity: Intensity)
	def setMaterial(face: Face, lightType: LightType, color: Color)
	def lightColor(instance: LightInstance, lightType: LightType, color: Color)
	def lightPosition(instance: LightInstance, position: Position)
	def setShadeModel(shadeModel: ShadeModel)
	def setShininess(face: Face, shininess: Int)
    def popLightState
    
    def pushFogState
    def setFogState(color: Color, mode: FogMode)
    def popFogState
    
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
    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture)
    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture, normals: Normals)
	def quads(vertices: Vertices, normals: Normals)
    
    def translate(x: Float, y: Float, z: Float)
	def rotate(angle: Float, x: Float, y: Float, z: Float)

	def newTexture(image: BufferedImage): TextureId
	def freeTexture(textureId: TextureId)
	
	def drawImage(x: Int, y: Int, width: Int, height: Int, imageType: ImageType, rawImage: ByteBuffer)
	def drawText(x: Int, y: Int, font: Font, text: String)
	
	def newShader(shaderType: ShaderType): ShaderId
	def freeShader(shaderId: ShaderId)
	def shaderSource(shaderId: ShaderId, source: String)
	def compileShader(shaderId: ShaderId): String
	def newProgram: ProgramId
	def freeProgram(programId: ProgramId)
	def attachShader(programId: ProgramId, shaderId: ShaderId)
	def detachShader(programId: ProgramId, shaderId: ShaderId)
	def linkProgram(programId: ProgramId): String
	def validateProgram(programId: ProgramId): String
	def useProgram(programId: ProgramId)
	def useNoProgram
	def currentProgram: ProgramId
	def getUniformId(program: Program, name: String): UniformId
	def setUniformValue(uniform: Uniform, a: Float, b: Float, c: Float, d: Float)
	
}
