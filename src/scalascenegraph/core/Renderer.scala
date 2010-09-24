package scalascenegraph.core

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

class Renderer(val gl: GL3bc) { 
	
    def clear {
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    }

    def clearColor(color: Color) {
        gl.glClearColor(color.r, color.g, color.b, color.a)
    }

    def enableBlending {
    	gl.glEnable(GL_BLEND)
    	gl.glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }
    
    def disableBlending {
    	gl.glDisable(GL_BLEND)
    }
    
    def pushDepthTestState {
    	gl.glPushAttrib(GL_DEPTH_BUFFER_BIT)
    }
    
	def enableDepthTest {
		gl.glEnable(GL_DEPTH_TEST)
	}
	
	def disableDepthTest {
		gl.glDisable(GL_DEPTH_TEST)
	}

    def popDepthTestState {
    	gl.glPopAttrib
    }
	
	def enableCullFace {
		gl.glEnable(GL_CULL_FACE)
	}
	
    def pushCullFace {
    	gl.glPushAttrib(GL_ENABLE_BIT)
    }
    
    def disableCullFace {
		gl.glDisable(GL_CULL_FACE)
    }
    
    def popCullFace {
    	gl.glPopAttrib
    }

    def pushFrontFace {
    	gl.glPushAttrib(GL_POLYGON_BIT)
    }
    
	def popFrontFace {
    	gl.glPopAttrib
	}
    
    def pushPolygonMode {
    	gl.glPushAttrib(GL_POLYGON_BIT)
    }
    
    def popPolygonMode {
    	gl.glPopAttrib
    }
	
	def setPolygonMode(face: Face, mode: DrawingMode) {
		gl.glPolygonMode(face, mode)
	}
	
    def flush {
        gl.glFlush
    }
    
    def finish {
        gl.glFinish
    }
    
    def pushMatrix {
    	gl.glPushMatrix
    }
    
    def popMatrix {
    	gl.glPopMatrix
    }
    
    def ortho(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl.glMatrixMode(GL_PROJECTION)
        gl.glLoadIdentity
        gl.glOrtho(left, right, bottom, top, near, far)
        gl.glMatrixMode(GL_MODELVIEW)
        gl.glLoadIdentity
    }
    
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl.glMatrixMode(GL_PROJECTION)
        gl.glLoadIdentity
        gl.glFrustum(left, right, bottom, top, near, far)
        gl.glMatrixMode(GL_MODELVIEW)
        gl.glLoadIdentity
    }
 
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice) {
        gl.glBegin(GL_TRIANGLES)
        gl.glVertex3f(v1.x, v1.y, v1.z)
        gl.glVertex3f(v2.x, v2.y, v2.z)
        gl.glVertex3f(v3.x, v3.y, v3.z)
        gl.glEnd
    }
    
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice,
    		     c1: Color, c2: Color, c3: Color)
    {
    	val color = new Array[Float](4)
    	gl.glGetFloatv(GL_CURRENT_COLOR, color, 0)
        gl.glBegin(GL_TRIANGLES)
        gl.glColor4f(c1.r, c1.g, c1.b, c1.a)
        gl.glVertex3f(v1.x, v1.y, v1.z)
        gl.glColor4f(c2.r, c2.g, c2.b, c2.a)
        gl.glVertex3f(v2.x, v2.y, v2.z)
        gl.glColor4f(c3.r, c3.g, c3.b, c3.a)
        gl.glVertex3f(v3.x, v3.y, v3.z)
        gl.glEnd
        gl.glColor4f(color(0), color(1), color(2), color(3))
    }
    
    def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice) {
        gl.glBegin(GL_QUADS)
        gl.glVertex3f(v1.x, v1.y, v1.z)
        gl.glVertex3f(v2.x, v2.y, v2.z)
        gl.glVertex3f(v3.x, v3.y, v3.z)
        gl.glVertex3f(v4.x, v4.y, v4.z)
        gl.glEnd
    }
    
    def quad(v1: Vertice, v2: Vertice, v3: Vertice, v4: Vertice,
    		 c1: Color, c2: Color, c3: Color, c4: Color)
    {
    	val color = new Array[Float](4)
    	gl.glGetFloatv(GL_CURRENT_COLOR, color, 0)
        gl.glBegin(GL_QUADS)
        gl.glColor4f(c1.r, c1.g, c1.b, c1.a)
        gl.glVertex3f(v1.x, v1.y, v1.z)
        gl.glColor4f(c2.r, c2.g, c2.b, c2.a)
        gl.glVertex3f(v2.x, v2.y, v2.z)
        gl.glColor4f(c3.r, c3.g, c3.b, c3.a)
        gl.glVertex3f(v3.x, v3.y, v3.z)
        gl.glColor4f(c4.r, c4.g, c4.b, c4.a)
        gl.glVertex3f(v4.x, v4.y, v4.z)
        gl.glEnd
        gl.glColor4f(color(0), color(1), color(2), color(3))
    }
    
    def quads(vertices: Vertices) {
        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
        gl.glDrawArrays(GL_QUADS, 0, vertices.count);
        gl.glDisableClientState(GL_VERTEX_ARRAY);
    }
    
    def quads(vertices: Vertices, colors: Colors) {
    	// FIXME: should not assume a color has alpha always
    	val color = new Array[Float](4)
    	gl.glGetFloatv(GL_CURRENT_COLOR, color, 0)
        gl.glEnableClientState(GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL_COLOR_ARRAY)
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
        gl.glColorPointer(4, GL_FLOAT, 0, colors.floatBuffer)
        gl.glDrawArrays(GL_QUADS, 0, vertices.count)
        gl.glDisableClientState(GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL_COLOR_ARRAY)
        gl.glColor4f(color(0), color(1), color(2), color(3))
    }

    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture) {
		gl.glBindTexture(GL_TEXTURE_2D, texture.textureId.id.asInstanceOf[Int])
        gl.glEnableClientState(GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
        gl.glTexCoordPointer(2, GL_FLOAT, 0, textureCoordinates.floatBuffer)
        gl.glDrawArrays(GL_QUADS, 0, vertices.count)
        gl.glDisableClientState(GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
    }
    
    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture, normals: Normals) {
		gl.glBindTexture(GL_TEXTURE_2D, texture.textureId.id.asInstanceOf[Int])
        gl.glEnableClientState(GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
        gl.glEnableClientState(GL_NORMAL_ARRAY);
        gl.glNormalPointer(GL_FLOAT, 0, normals.floatBuffer)
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
        gl.glTexCoordPointer(2, GL_FLOAT, 0, textureCoordinates.floatBuffer)
        gl.glDrawArrays(GL_QUADS, 0, vertices.count)
        gl.glDisableClientState(GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
        gl.glDisableClientState(GL_NORMAL_ARRAY);
    }
    
    def quads(vertices: Vertices, color: Color) {
    	val save = new Array[Float](4)
    	gl.glGetFloatv(GL_CURRENT_COLOR, save, 0)
        gl.glColor4f(color.r, color.g, color.b, color.a)
        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
        gl.glDrawArrays(GL_QUADS, 0, vertices.count);
        gl.glDisableClientState(GL_VERTEX_ARRAY);
        gl.glColor4f(save(0), save(1), save(2), save(3))
    }
    
	def quads(vertices: Vertices, normals: Normals) {
        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL_NORMAL_ARRAY);
        gl.glNormalPointer(GL_FLOAT, 0, normals.floatBuffer)
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
        gl.glDrawArrays(GL_QUADS, 0, vertices.count);
        gl.glDisableClientState(GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL_VERTEX_ARRAY);
	}
    
	def lineStrip(vbo: VertexBufferObject) {
		gl.glEnableClientState(GL_VERTEX_ARRAY)
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.vertexBufferObjectId.id.asInstanceOf[Int])
		gl.glVertexPointer(2, GL_FLOAT, 0, 0);
        gl.glDrawArrays(GL_LINE_STRIP, 0, vbo.count)
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
		gl.glDisableClientState(GL_VERTEX_ARRAY)
	}

	def lineStrips(vbo: VertexBufferObject, firsts: IntBuffer, counts: IntBuffer) {
		gl.glEnableClientState(GL_VERTEX_ARRAY)
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.vertexBufferObjectId.id.asInstanceOf[Int])
		gl.glVertexPointer(2, GL_FLOAT, 0, 0);
        gl.glMultiDrawArrays(GL_LINE_STRIP, firsts, counts, firsts.capacity)
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
		gl.glDisableClientState(GL_VERTEX_ARRAY)
	}
	
    def translate(x: Float, y: Float, z: Float) {
        gl.glTranslatef(x, y, z)
    }

	def rotate(angle: Float, x: Float, y: Float, z: Float) {
		gl.glRotated(angle, x, y, z)
	}

    def pushLightState {
    	gl.glPushAttrib(GL_LIGHTING_BIT)
    }
    
    def setLightState(state: OnOffState) {
    	state match {
    		case On => gl.glEnable(GL_LIGHTING)
    		case Off => gl.glDisable(GL_LIGHTING)
    	}
    }
    
	def setAmbientLight(intensity: Intensity) {
		gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, intensity.asFloatArray, 0)
	}

	def setMaterial(face: Face, lightType: LightType, color: Color) {
		gl.glMaterialfv(face, lightType, color.asFloatArray, 0)
	}

    def setLightState(instance: LightInstance, state: OnOffState) {
    	state match {
    		case On => gl.glEnable(instance)
    		case Off => gl.glDisable(instance)
    	}
    }
	
	def lightColor(instance: LightInstance, lightType: LightType, color: Color) {
		gl.glLightfv(instance, lightType, color.asFloatArray, 0)
	}
	
	def lightPosition(instance: LightInstance, position: Position) {
		val p = position.asFloatArray
		gl.glLightfv(instance, GL_POSITION, Array(p(0), p(1), p(2), 1.0f), 0)
	}
	
	def setShininess(face: Face, shininess: Int) {
		gl.glMateriali(face, GL_SHININESS, shininess)
	}
	
    def popLightState {
    	gl.glPopAttrib
    }
	
    def pushLineState {
    	gl.glPushAttrib(GL_LINE_BIT)
    }
    
    def setLineWidth(width: Float) {
    	gl.glLineWidth(width)
    }
    
    def popLineState {
    	gl.glPopAttrib
    }
    
	def setShadeModel(shadeModel: ShadeModel) {
		gl.glShadeModel(shadeModel)
	}
    
    def pushFogState {
    	gl.glPushAttrib(GL_FOG_BIT)
    }
    
    def setFogState(color: Color, mode: FogMode) {
    	gl.glEnable(GL_FOG)
    	gl.glFogfv(GL_FOG_COLOR, color.asFloatArray, 0)
    	mode match {
    		case Linear(start, end) => {
    			gl.glFogf(GL_FOG_START, start)
    			gl.glFogf(GL_FOG_END, end)
    			gl.glFogf(GL_FOG_MODE, GL_LINEAR)
    		}
    		case Exp(density) => {
    			gl.glFogf(GL_FOG_DENSITY, density)
    			gl.glFogf(GL_FOG_MODE, GL_EXP)
    		}
    		case Exp2(density) => {
    			gl.glFogf(GL_FOG_DENSITY, density)
    			gl.glFogf(GL_FOG_MODE, GL_EXP2)
    		}
    	}
    }
    
    def popFogState {
    	gl.glPopAttrib
    }
    
	def newTexture(image: BufferedImage): TextureId = {
		gl.glEnable(GL_TEXTURE_2D)
		val buffer = Utils.makeDirectByteBuffer(image)
		val textureIds = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder).asIntBuffer // TODO: hardcoded value...
		gl.glGenTextures(1, textureIds)
		val textureId = textureIds.get(0)
		gl.glBindTexture(GL_TEXTURE_2D, textureId)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
		gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
		image.getColorModel.hasAlpha match {
			case false => gl.glTexImage2D(GL_TEXTURE_2D, 0, 3, image.getWidth, image.getHeight, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer)
			case true => gl.glTexImage2D(GL_TEXTURE_2D, 0, 4, image.getWidth, image.getHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
		}
		TextureId(textureId)
	}
	
	def freeTexture(textureId: TextureId) {
		val textureIds = ByteBuffer.allocateDirect(4).asIntBuffer // TODO: hardcoded value...
		textureIds.put(0, textureId.id.asInstanceOf[Int])
		gl.glDeleteTextures(1, textureIds)
	}

	def drawImage(x: Int, y: Int, width: Int, height: Int, imageType: ImageType, rawImage: ByteBuffer) {
		gl.glWindowPos2i(x, y)
		gl.glDrawPixels(width, height, imageType, GL_UNSIGNED_BYTE, rawImage)
	}
	
	def drawText(x: Int, y: Int, font: Font, text: String) {
		
		// TODO: move to the 'init' part
		// TODO: should use opengl default
		gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
		
		gl.glWindowPos2i(x, y)
		text.foreach { c => {
			val character = font.characters(c)
			gl.glBitmap(character.width, character.height, 0, 0, character.width, 0, character.bitmap)
		}}
	}
	
	def newShader(shaderType: ShaderType): ShaderId = {
		ShaderId(gl.glCreateShader(shaderType))
	}
	
	def freeShader(shaderId: ShaderId) {
		gl.glDeleteShader(shaderId.id.asInstanceOf[Int])
	}
	
	def shaderSource(shaderId: ShaderId, source: String) {
		val id = shaderId.id.asInstanceOf[Int]
		gl.glShaderSource(id, 1, Array(source), Array(source.size), 0)
	}

	def compileShader(shaderId: ShaderId): String = {
		val log = new Array[Byte](8192)
		val length = Array(0)
		val id = shaderId.id.asInstanceOf[Int]
		gl.glCompileShader(id)
		gl.glGetShaderInfoLog(id, log.size, length, 0, log, 0)
		new String(log, 0, length(0))
	}

	def newProgram: ProgramId = {
		ProgramId(gl.glCreateProgram)
	}
	
	def freeProgram(programId: ProgramId) {
		gl.glDeleteProgram(programId.id.asInstanceOf[Int])
	}
	
	def attachShader(programId: ProgramId, shaderId: ShaderId) {
		gl.glAttachShader(programId.id.asInstanceOf[Int], shaderId.id.asInstanceOf[Int])
	}
	
	def detachShader(programId: ProgramId, shaderId: ShaderId) {
		gl.glDetachShader(programId.id.asInstanceOf[Int], shaderId.id.asInstanceOf[Int])
	}
	
	def linkProgram(programId: ProgramId): String = {
		val log = new Array[Byte](8192)
		val length = Array(0)
		val id = programId.id.asInstanceOf[Int]
		gl.glLinkProgram(id)
		gl.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
		new String(log, 0, length(0))
	}
	
	def validateProgram(programId: ProgramId): String = {
		val log = new Array[Byte](8192)
		val length = Array(0)
		val id = programId.id.asInstanceOf[Int]
		gl.glValidateProgram(id)
		gl.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
		new String(log, 0, length(0))
	}
	
	def useProgram(programId: ProgramId) {
		gl.glUseProgram(programId.id.asInstanceOf[Int])
	}

	def useNoProgram {
		gl.glUseProgram(0)
	}
	
	def currentProgram: ProgramId = {
		val id = Array[Int](1)
		gl.glGetIntegerv(GL_CURRENT_PROGRAM, id, 0)
		ProgramId(id(0))
	}

	def getUniformId(program: Program, name: String): UniformId = {
		UniformId(gl.glGetUniformLocation(program.programId.id.asInstanceOf[Int], name))
	}
	
	def setUniformValue(uniform: Uniform, a: Float, b: Float, c: Float, d: Float) {
		gl.glUniform4f(uniform.uniformId.id.asInstanceOf[Int], a, b, c, d)
		uniform.value = Array(a, b, c, d)
	}

	def setUniformValue(uniform: Uniform, a: Float) {
		gl.glUniform1f(uniform.uniformId.id.asInstanceOf[Int], a)
		uniform.value = a
	}

	def newVertexBufferObject: VertexBufferObjectId = {
		val ids = Array[Int](1)
		gl.glGenBuffers(1, ids, 0)
		VertexBufferObjectId(ids(0))
	}

	def freeVertexBufferObject(vertexBufferObjectId: VertexBufferObjectId) = {
		val ids = Array(vertexBufferObjectId.id .asInstanceOf[Int])
		gl.glDeleteBuffers(1, ids, 0)
	}
	
	def loadVertexBufferObjectData(vbo: VertexBufferObject, vertices: Vertices) = {
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.vertexBufferObjectId.id.asInstanceOf[Int])
		gl.glBufferData(GL_ARRAY_BUFFER, vertices.count * 3 * 4, vertices.floatBuffer, GL_STATIC_DRAW)
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
	}
	
}
