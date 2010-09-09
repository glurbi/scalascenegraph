package scalascenegraph.opengl

import java.nio._
import java.awt.image._
import javax.media.opengl._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import com.jogamp.opengl.util._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class OpenglRenderer(val gl2: GL2) extends Renderer { 
	
	def pushColorState {
		gl2.glPushAttrib(GL_CURRENT_BIT)
	}
	
	def popColorState {
		gl2.glPopAttrib
	}
	
    def setColor(color: Color) {
        gl2.glColor3f(color.r, color.g , color.b)
    }	
	
    def clear {
        gl2.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    }
    
    def clearColor(color: Color) {
        gl2.glClearColor(color.r, color.g, color.b, color.a)
    }
 
    def enableBlending {
    	gl2.glEnable(GL_BLEND)
    	gl2.glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }
    
    def disableBlending {
    	gl2.glDisable(GL_BLEND)
    }
    
    def pushDepthTestState {
    	gl2.glPushAttrib(GL_DEPTH_BUFFER_BIT)
    }
    
	def enableDepthTest {
		gl2.glEnable(GL_DEPTH_TEST)
	}
	
	def disableDepthTest {
		gl2.glDisable(GL_DEPTH_TEST)
	}

    def popDepthTestState {
    	gl2.glPopAttrib
    }
	
	def enableCullFace {
		gl2.glEnable(GL_CULL_FACE)
	}
	
    def pushCullFace {
    	gl2.glPushAttrib(GL_ENABLE_BIT)
    }
    
    def disableCullFace {
		gl2.glDisable(GL_CULL_FACE)
    }
    
    def popCullFace {
    	gl2.glPopAttrib
    }

    def pushFrontFace {
    	gl2.glPushAttrib(GL_POLYGON_BIT)
    }
    
    def setFrontFace(frontFace: FrontFace) {
    	def glFrontFace(frontFace: FrontFace): Int = frontFace match {
			case ClockWise => GL_CW
			case CounterClockWise => GL_CCW
		}
    	gl2.glFrontFace(glFrontFace(frontFace))
    }
    
	def popFrontFace {
    	gl2.glPopAttrib
	}
    
    def pushPolygonMode {
    	gl2.glPushAttrib(GL_POLYGON_BIT)
    }
    
    def popPolygonMode {
    	gl2.glPopAttrib
    }
	
	def setPolygonMode(face: Face, mode: DrawingMode) {
		gl2.glPolygonMode(glFace(face), glMode(mode))
	}
	
    def flush {
        gl2.glFlush
    }
    
    def pushMatrix {
    	gl2.glPushMatrix
    }
    
    def popMatrix {
    	gl2.glPopMatrix
    }
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl2.glMatrixMode(GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glOrtho(left, right, bottom, top, near, far)
        gl2.glMatrixMode(GL_MODELVIEW)
        gl2.glLoadIdentity
    }
    
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl2.glMatrixMode(GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glFrustum(left, right, bottom, top, near, far)
        gl2.glMatrixMode(GL_MODELVIEW)
        gl2.glLoadIdentity
    }
 
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice) {
        gl2.glBegin(GL_TRIANGLES)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glEnd
    }
    
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice,
    		     c1: Color, c2: Color, c3: Color)
    {
    	val color = new Array[Float](4)
    	gl2.glGetFloatv(GL_CURRENT_COLOR, color, 0)
        gl2.glBegin(GL_TRIANGLES)
        gl2.glColor4f(c1.r, c1.g, c1.b, c1.a)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glColor4f(c2.r, c2.g, c2.b, c2.a)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glColor4f(c3.r, c3.g, c3.b, c3.a)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glEnd
        gl2.glColor4f(color(0), color(1), color(2), color(3))
    }
    
    def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice) {
        gl2.glBegin(GL_QUADS)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glVertex3f(v4.x, v4.y, v4.z)
        gl2.glEnd
    }
    
    def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice,
    		 c1: Color, c2: Color, c3: Color, c4: Color)
    {
    	val color = new Array[Float](4)
    	gl2.glGetFloatv(GL_CURRENT_COLOR, color, 0)
        gl2.glBegin(GL_QUADS)
        gl2.glColor4f(c1.r, c1.g, c1.b, c1.a)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glColor4f(c2.r, c2.g, c2.b, c2.a)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glColor4f(c3.r, c3.g, c3.b, c3.a)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glColor4f(c4.r, c4.g, c4.b, c4.a)
        gl2.glVertex3f(v4.x, v4.y, v4.z)
        gl2.glEnd
        gl2.glColor4f(color(0), color(1), color(2), color(3))
    }
    
    def quads(vertices: Vertices) {
        gl2.glEnableClientState(GL_VERTEX_ARRAY);
        gl2.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
        gl2.glDrawArrays(GL_QUADS, 0, vertices.count);
        gl2.glDisableClientState(GL_VERTEX_ARRAY);
    }
    
    def quads(vertices: Vertices, colors: Colors) {
    	// FIXME: should not assume a color has alpha always
    	val color = new Array[Float](4)
    	gl2.glGetFloatv(GL_CURRENT_COLOR, color, 0)
        gl2.glEnableClientState(GL_VERTEX_ARRAY)
        gl2.glEnableClientState(GL_COLOR_ARRAY)
        gl2.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
        gl2.glColorPointer(4, GL_FLOAT, 0, colors.floatBuffer)
        gl2.glDrawArrays(GL_QUADS, 0, vertices.count)
        gl2.glDisableClientState(GL_VERTEX_ARRAY)
        gl2.glDisableClientState(GL_COLOR_ARRAY)
        gl2.glColor4f(color(0), color(1), color(2), color(3))
    }

    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture) {
		gl2.glBindTexture(GL_TEXTURE_2D, texture.textureId.id.asInstanceOf[Int])
        gl2.glEnableClientState(GL_VERTEX_ARRAY)
        gl2.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
        gl2.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
        gl2.glTexCoordPointer(2, GL_FLOAT, 0, textureCoordinates.floatBuffer)
        gl2.glDrawArrays(GL_QUADS, 0, vertices.count)
        gl2.glDisableClientState(GL_VERTEX_ARRAY)
        gl2.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
    }
    
    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture, normals: Normals) {
		gl2.glBindTexture(GL_TEXTURE_2D, texture.textureId.id.asInstanceOf[Int])
        gl2.glEnableClientState(GL_VERTEX_ARRAY)
        gl2.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
        gl2.glEnableClientState(GL_NORMAL_ARRAY);
        gl2.glNormalPointer(GL_FLOAT, 0, normals.floatBuffer)
        gl2.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
        gl2.glTexCoordPointer(2, GL_FLOAT, 0, textureCoordinates.floatBuffer)
        gl2.glDrawArrays(GL_QUADS, 0, vertices.count)
        gl2.glDisableClientState(GL_VERTEX_ARRAY)
        gl2.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
        gl2.glDisableClientState(GL_NORMAL_ARRAY);
    }
    
    def quads(vertices: Vertices, color: Color) {
    	val save = new Array[Float](4)
    	gl2.glGetFloatv(GL_CURRENT_COLOR, save, 0)
        gl2.glColor4f(color.r, color.g, color.b, color.a)
        gl2.glEnableClientState(GL_VERTEX_ARRAY);
        gl2.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
        gl2.glDrawArrays(GL_QUADS, 0, vertices.count);
        gl2.glDisableClientState(GL_VERTEX_ARRAY);
        gl2.glColor4f(save(0), save(1), save(2), save(3))
    }
    
	def quads(vertices: Vertices, normals: Normals) {
        gl2.glEnableClientState(GL_VERTEX_ARRAY);
        gl2.glEnableClientState(GL_NORMAL_ARRAY);
        gl2.glNormalPointer(GL_FLOAT, 0, normals.floatBuffer)
        gl2.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
        gl2.glDrawArrays(GL_QUADS, 0, vertices.count);
        gl2.glDisableClientState(GL_NORMAL_ARRAY);
        gl2.glDisableClientState(GL_VERTEX_ARRAY);
	}
    
    def translate(x: Float, y: Float, z: Float) {
        gl2.glTranslatef(x, y, z)
    }

	def rotate(angle: Float, x: Float, y: Float, z: Float) {
		gl2.glRotated(angle, x, y, z)
	}

    def pushLightState {
    	gl2.glPushAttrib(GL_LIGHTING_BIT)
    }
    
    def setLightState(state: OnOffState) {
    	state match {
    		case On => gl2.glEnable(GL_LIGHTING)
    		case Off => gl2.glDisable(GL_LIGHTING)
    	}
    }
    
	def setAmbientLight(intensity: Intensity) {
		gl2.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, intensity.asFloatArray, 0)
	}

	def setMaterial(face: Face, lightType: LightType, color: Color) {
		gl2.glMaterialfv(glFace(face), glLightType(lightType), color.asFloatArray, 0)
	}

    def setLightState(instance: LightInstance, state: OnOffState) {
    	state match {
    		case On => gl2.glEnable(glLightInstance(instance))
    		case Off => gl2.glDisable(glLightInstance(instance))
    	}
    }
	
	def lightColor(instance: LightInstance, lightType: LightType, color: Color) {
		gl2.glLightfv(glLightInstance(instance), glLightType(lightType), color.asFloatArray, 0)
	}
	
	def lightPosition(instance: LightInstance, position: Position) {
		val p = position.asFloatArray
		gl2.glLightfv(glLightInstance(instance), GL_POSITION, Array(p(0), p(1), p(2), 1.0f), 0)
	}
	
	def setShininess(face: Face, shininess: Int) {
		gl2.glMateriali(glFace(face), GL_SHININESS, shininess)
	}
	
    def popLightState {
    	gl2.glPopAttrib
    }
	
    def pushLineState {
    	gl2.glPushAttrib(GL_LINE_BIT)
    }
    
    def setLineWidth(width: Float) {
    	gl2.glLineWidth(width)
    }
    
    def popLineState {
    	gl2.glPopAttrib
    }
    
	def setShadeModel(shadeModel: ShadeModel) {
		gl2.glShadeModel(glShadeModel(shadeModel))
	}
    
    def pushFogState {
    	gl2.glPushAttrib(GL_FOG_BIT)
    }
    
    def setFogState(color: Color, mode: FogMode) {
    	gl2.glEnable(GL_FOG)
    	gl2.glFogfv(GL_FOG_COLOR, color.asFloatArray, 0)
    	mode match {
    		case Linear(start, end) => {
    			gl2.glFogf(GL_FOG_START, start)
    			gl2.glFogf(GL_FOG_END, end)
    			gl2.glFogf(GL_FOG_MODE, GL_LINEAR)
    		}
    		case Exp(density) => {
    			gl2.glFogf(GL_FOG_DENSITY, density)
    			gl2.glFogf(GL_FOG_MODE, GL_EXP)
    		}
    		case Exp2(density) => {
    			gl2.glFogf(GL_FOG_DENSITY, density)
    			gl2.glFogf(GL_FOG_MODE, GL_EXP2)
    		}
    	}
    }
    
    def popFogState {
    	gl2.glPopAttrib
    }
    
	def newTexture(image: BufferedImage): TextureId = {
		gl2.glEnable(GL_TEXTURE_2D)
		val buffer = Utils.makeDirectByteBuffer(image)
		val textureIds = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder).asIntBuffer // TODO: hardcoded value...
		gl2.glGenTextures(1, textureIds)
		val textureId = textureIds.get(0)
		gl2.glBindTexture(GL_TEXTURE_2D, textureId)
		gl2.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
		gl2.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
		gl2.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
		gl2.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
		image.getColorModel.hasAlpha match {
			case false => gl2.glTexImage2D(GL_TEXTURE_2D, 0, 3, image.getWidth, image.getHeight, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer)
			case true => gl2.glTexImage2D(GL_TEXTURE_2D, 0, 4, image.getWidth, image.getHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
		}
		TextureId(textureId)
	}
	
	def freeTexture(textureId: TextureId) {
		val textureIds = ByteBuffer.allocateDirect(4).asIntBuffer // TODO: hardcoded value...
		textureIds.put(0, textureId.id.asInstanceOf[Int])
		gl2.glDeleteTextures(1, textureIds)
	}

	def drawImage(x: Int, y: Int, width: Int, height: Int, imageType: ImageType, rawImage: ByteBuffer) {
		gl2.glWindowPos2i(x, y)
		gl2.glDrawPixels(width, height, glImageType(imageType), GL_UNSIGNED_BYTE, rawImage)
	}
	
	def drawText(x: Int, y: Int, font: Font, text: String) {
		
		// TODO: move to the 'init' part
		// TODO: should use opengl default
		gl2.glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
		
		gl2.glWindowPos2i(x, y)
		text.foreach { c => {
			val character = font.characters(c)
			gl2.glBitmap(character.width, character.height, 0, 0, character.width, 0, character.bitmap)
		}}
	}
	
	def newShader(shaderType: ShaderType): ShaderId = {
		ShaderId(gl2.glCreateShader(glShaderType(shaderType)))
	}
	
	def freeShader(shaderId: ShaderId) {
		gl2.glDeleteShader(shaderId.id.asInstanceOf[Int])
	}
	
	def shaderSource(shaderId: ShaderId, source: String) {
		val id = shaderId.id.asInstanceOf[Int]
		gl2.glShaderSource(id, 1, Array(source), Array(source.size), 0)
	}

	def compileShader(shaderId: ShaderId): String = {
		val log = new Array[Byte](8192)
		val length = Array(0)
		val id = shaderId.id.asInstanceOf[Int]
		gl2.glCompileShader(id)
		gl2.glGetShaderInfoLog(id, log.size, length, 0, log, 0)
		new String(log, 0, length(0))
	}

	def newProgram: ProgramId = {
		ProgramId(gl2.glCreateProgram)
	}
	
	def freeProgram(programId: ProgramId) {
		gl2.glDeleteProgram(programId.id.asInstanceOf[Int])
	}
	
	def attachShader(programId: ProgramId, shaderId: ShaderId) {
		gl2.glAttachShader(programId.id.asInstanceOf[Int], shaderId.id.asInstanceOf[Int])
	}
	
	def detachShader(programId: ProgramId, shaderId: ShaderId) {
		gl2.glDetachShader(programId.id.asInstanceOf[Int], shaderId.id.asInstanceOf[Int])
	}
	
	def linkProgram(programId: ProgramId): String = {
		val log = new Array[Byte](8192)
		val length = Array(0)
		val id = programId.id.asInstanceOf[Int]
		gl2.glLinkProgram(id)
		gl2.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
		new String(log, 0, length(0))
	}
	
	def validateProgram(programId: ProgramId): String = {
		val log = new Array[Byte](8192)
		val length = Array(0)
		val id = programId.id.asInstanceOf[Int]
		gl2.glValidateProgram(id)
		gl2.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
		new String(log, 0, length(0))
	}
	
	def useProgram(programId: ProgramId) {
		gl2.glUseProgram(programId.id.asInstanceOf[Int])
	}

	def useNoProgram {
		gl2.glUseProgram(0)
	}
	
	def currentProgram: ProgramId = {
		val id = Array[Int](1)
		gl2.glGetIntegerv(GL_CURRENT_PROGRAM, id, 0)
		ProgramId(id(0))
	}
	
	private def glFace(face: Face): Int = face match {
		case Front => GL_FRONT
		case Back => GL_BACK
		case FrontAndBack => GL_FRONT_AND_BACK
	}
	
	private def glMode(mode: DrawingMode): Int = mode match {
		case Point => GL_POINT
		case Line => GL_LINE
		case Fill => GL_FILL
	}
	
	private def glLightType(lightType: LightType): Int = lightType match {
		case AmbientLight => GL_AMBIENT
		case DiffuseLight => GL_DIFFUSE
		case SpecularLight => GL_SPECULAR
		case EmissionLight => GL_EMISSION
		case AmbientAndDiffuseLight => GL_AMBIENT_AND_DIFFUSE
	}
	
	private def glShadeModel(shadeModel: ShadeModel): Int = shadeModel match {
		case Flat => GL_FLAT
		case Smooth => GL_SMOOTH
	}

	private def glLightInstance(instance: LightInstance): Int = instance match {
		case LightInstance(0) => GL_LIGHT0
		case LightInstance(1) => GL_LIGHT1
		case LightInstance(2) => GL_LIGHT2
		case LightInstance(3) => GL_LIGHT3
		case LightInstance(4) => GL_LIGHT4
		case LightInstance(5) => GL_LIGHT5
		case LightInstance(6) => GL_LIGHT6
		case LightInstance(7) => GL_LIGHT7
	}

	private def glImageType(imageType: ImageType): Int = imageType match {
		case RGB => GL_RGB
		case RGBA => GL_RGBA
	}
	
	private def glShaderType(shaderType: ShaderType): Int = shaderType match {
		case VertexShader => GL_VERTEX_SHADER
		case FragmentShader => GL_FRAGMENT_SHADER
	}
	
}
