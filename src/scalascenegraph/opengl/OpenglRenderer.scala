package scalascenegraph.opengl

import java.nio._
import java.awt.image._
import javax.media.opengl._
import javax.media.opengl.fixedfunc._
import com.jogamp.opengl.util._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class OpenglRenderer(val gl2: GL2) extends Renderer { 
	
	def pushColorState {
		gl2.glPushAttrib(GL2.GL_CURRENT_BIT)
	}
	
	def popColorState {
		gl2.glPopAttrib
	}
	
    def setColor(color: Color) {
        gl2.glColor3f(color.r, color.g , color.b)
    }	
	
    def clearColor(color: Color) {
        gl2.glClearColor(color.r, color.g, color.b, color.a)
    }
 
	def enableDepthTest {
		gl2.glEnable(GL.GL_DEPTH_TEST)
	}
	
	def disableDepthTest {
		gl2.glDisable(GL.GL_DEPTH_TEST)
	}

	def enableCullFace {
		gl2.glEnable(GL.GL_CULL_FACE)
	}
	
    def pushCullFace {
    	gl2.glPushAttrib(GL2.GL_ENABLE_BIT)
    }
    
    def disableCullFace {
		gl2.glDisable(GL.GL_CULL_FACE)
    }
    
    def popCullFace {
    	gl2.glPopAttrib
    }

    def pushFrontFace {
    	gl2.glPushAttrib(GL2.GL_POLYGON_BIT)
    }
    
    def setFrontFace(frontFace: FrontFace) {
    	def glFrontFace(frontFace: FrontFace): Int = frontFace match {
			case ClockWise => GL.GL_CW
			case CounterClockWise => GL.GL_CCW
		}
    	gl2.glFrontFace(glFrontFace(frontFace))
    }
    
	def popFrontFace {
    	gl2.glPopAttrib
	}
    
    def pushPolygonMode {
    	gl2.glPushAttrib(GL2.GL_POLYGON_BIT)
    }
    
    def popPolygonMode {
    	gl2.glPopAttrib
    }
	
	def setPolygonMode(face: Face, mode: DrawingMode) {
		gl2.glPolygonMode(glFace(face), glMode(mode))
	}
	
    def clear {
        gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT)
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
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glOrtho(left, right, bottom, top, near, far)
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW)
        gl2.glLoadIdentity
    }
    
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glFrustum(left, right, bottom, top, near, far)
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW)
        gl2.glLoadIdentity
    }
 
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice) {
        gl2.glBegin(GL.GL_TRIANGLES)
        gl2.glVertex3f(v1.x, v1.y, v1.z)
        gl2.glVertex3f(v2.x, v2.y, v2.z)
        gl2.glVertex3f(v3.x, v3.y, v3.z)
        gl2.glEnd
    }
    
    def triangle(v1: Vertice, v2: Vertice, v3: Vertice,
    		     c1: Color, c2: Color, c3: Color)
    {
    	val color = new Array[Float](4)
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, color, 0)
        gl2.glBegin(GL.GL_TRIANGLES)
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
        gl2.glBegin(GL2.GL_QUADS)
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
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, color, 0)
        gl2.glBegin(GL2.GL_QUADS)
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
        gl2.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl2.glVertexPointer(3, GL.GL_FLOAT, 0, vertices.floatBuffer);
        gl2.glDrawArrays(GL2.GL_QUADS, 0, vertices.count);
        gl2.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
    }
    
    def quads(vertices: Vertices, colors: Colors) {
    	val color = new Array[Float](4)
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, color, 0)
        gl2.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
        gl2.glEnableClientState(GLPointerFunc.GL_COLOR_ARRAY)
        gl2.glVertexPointer(3, GL.GL_FLOAT, 0, vertices.floatBuffer)
        gl2.glColorPointer(4, GL.GL_FLOAT, 0, colors.floatBuffer)
        gl2.glDrawArrays(GL2.GL_QUADS, 0, vertices.count)
        gl2.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
        gl2.glDisableClientState(GLPointerFunc.GL_COLOR_ARRAY)
        gl2.glColor4f(color(0), color(1), color(2), color(3))
    }

    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture) {
		gl2.glBindTexture(GL.GL_TEXTURE_2D, texture.textureId.id.asInstanceOf[Int])
        gl2.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
        gl2.glEnableClientState(GLPointerFunc.GL_TEXTURE_COORD_ARRAY)
        gl2.glVertexPointer(3, GL.GL_FLOAT, 0, vertices.floatBuffer)
        gl2.glTexCoordPointer(2, GL.GL_FLOAT, 0, textureCoordinates.floatBuffer)
        gl2.glDrawArrays(GL2.GL_QUADS, 0, vertices.count)
        gl2.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
        gl2.glDisableClientState(GLPointerFunc.GL_TEXTURE_COORD_ARRAY)
    }
    
    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture, normals: Normals) {
		gl2.glBindTexture(GL.GL_TEXTURE_2D, texture.textureId.id.asInstanceOf[Int])
        gl2.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
        gl2.glEnableClientState(GLPointerFunc.GL_TEXTURE_COORD_ARRAY)
        gl2.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
        gl2.glNormalPointer(GL.GL_FLOAT, 0, normals.floatBuffer)
        gl2.glVertexPointer(3, GL.GL_FLOAT, 0, vertices.floatBuffer)
        gl2.glTexCoordPointer(2, GL.GL_FLOAT, 0, textureCoordinates.floatBuffer)
        gl2.glDrawArrays(GL2.GL_QUADS, 0, vertices.count)
        gl2.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY)
        gl2.glDisableClientState(GLPointerFunc.GL_TEXTURE_COORD_ARRAY)
        gl2.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
    }
    
    def quads(vertices: Vertices, color: Color) {
    	val save = new Array[Float](4)
    	gl2.glGetFloatv(GL2ES1.GL_CURRENT_COLOR, save, 0)
        gl2.glColor4f(color.r, color.g, color.b, color.a)
        gl2.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl2.glVertexPointer(3, GL.GL_FLOAT, 0, vertices.floatBuffer);
        gl2.glDrawArrays(GL2.GL_QUADS, 0, vertices.count);
        gl2.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl2.glColor4f(save(0), save(1), save(2), save(3))
    }
    
	def quads(vertices: Vertices, normals: Normals) {
        gl2.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl2.glEnableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
        gl2.glNormalPointer(GL.GL_FLOAT, 0, normals.floatBuffer)
        gl2.glVertexPointer(3, GL.GL_FLOAT, 0, vertices.floatBuffer);
        gl2.glDrawArrays(GL2.GL_QUADS, 0, vertices.count);
        gl2.glDisableClientState(GLPointerFunc.GL_NORMAL_ARRAY);
        gl2.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
	}
    
    def translate(x: Float, y: Float, z: Float) {
        gl2.glTranslatef(x, y, z)
    }

	def rotate(angle: Float, x: Float, y: Float, z: Float) {
		gl2.glRotated(angle, x, y, z)
	}

    def pushLightState {
    	gl2.glPushAttrib(GL2.GL_LIGHTING_BIT)
    }
    
    def setLightState(state: OnOffState) {
    	state match {
    		case On => gl2.glEnable(GLLightingFunc.GL_LIGHTING)
    		case Off => gl2.glDisable(GLLightingFunc.GL_LIGHTING)
    	}
    }
    
	def setAmbientLight(intensity: Intensity) {
		gl2.glLightModelfv(GL2ES1.GL_LIGHT_MODEL_AMBIENT, intensity.asFloatArray, 0)
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
		gl2.glLightfv(glLightInstance(instance), GLLightingFunc.GL_POSITION, Array(p(0), p(1), p(2), 1.0f), 0)
	}
	
	def setShininess(face: Face, shininess: Int) {
		gl2.glMateriali(glFace(face), GLLightingFunc.GL_SHININESS, shininess)
	}
	
    def popLightState {
    	gl2.glPopAttrib
    }
	
    def pushLineState {
    	gl2.glPushAttrib(GL2.GL_LINE_BIT)
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
    	gl2.glPushAttrib(GL2.GL_FOG_BIT)
    }
    
    def setFogState(color: Color, mode: FogMode) {
    	gl2.glEnable(GL2ES1.GL_FOG)
    	gl2.glFogfv(GL2ES1.GL_FOG_COLOR, color.asFloatArray, 0)
    	mode match {
    		case Linear(start, end) => {
    			gl2.glFogf(GL2ES1.GL_FOG_START, start)
    			gl2.glFogf(GL2ES1.GL_FOG_END, end)
    			gl2.glFogf(GL2ES1.GL_FOG_MODE, GL.GL_LINEAR)
    		}
    		case Exp(density) => {
    			gl2.glFogf(GL2ES1.GL_FOG_DENSITY, density)
    			gl2.glFogf(GL2ES1.GL_FOG_MODE, GL2ES1.GL_EXP)
    		}
    		case Exp2(density) => {
    			gl2.glFogf(GL2ES1.GL_FOG_DENSITY, density)
    			gl2.glFogf(GL2ES1.GL_FOG_MODE, GL2ES1.GL_EXP2)
    		}
    	}
    }
    
    def popFogState {
    	gl2.glPopAttrib
    }
    
	def newTexture(image: BufferedImage): TextureId = {
		gl2.glEnable(GL.GL_TEXTURE_2D)
		val buffer = image.getType match {
			case BufferedImage.TYPE_3BYTE_BGR => makeBufferForTYPE_3BYTE_BGR(image)
			case BufferedImage.TYPE_CUSTOM => makeBufferForTYPE_3BYTE_BGR(image) // Windows?
			case BufferedImage.TYPE_4BYTE_ABGR => makeBufferForTYPE_4BYTE_ABGR(image)
		}
		val textureIds = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder).asIntBuffer // TODO: hardcoded value...
		gl2.glGenTextures(1, textureIds)
		val textureId = textureIds.get(0)
		gl2.glBindTexture(GL.GL_TEXTURE_2D, textureId)
		gl2.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT)
		gl2.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT)
		gl2.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR)
		gl2.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR)
		image.getType match {
			case BufferedImage.TYPE_3BYTE_BGR => gl2.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, image.getWidth, image.getHeight, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, buffer)
			case BufferedImage.TYPE_CUSTOM => gl2.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, image.getWidth, image.getHeight, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, buffer) // Windows?
			case BufferedImage.TYPE_4BYTE_ABGR => gl2.glTexImage2D(GL.GL_TEXTURE_2D, 0, 4, image.getWidth, image.getHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, buffer)
		}
		TextureId(textureId)
	}

	private def makeBufferForTYPE_3BYTE_BGR(image: BufferedImage): ByteBuffer = {
			val data = image.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
			val buffer = ByteBuffer.allocateDirect(data.length)
			buffer.order(ByteOrder.nativeOrder)
			buffer.put(data, 0, data.length)
			buffer.rewind
			buffer
	}

	private def makeBufferForTYPE_INT_ARGB(image: BufferedImage): ByteBuffer = {
			val data = image.getRaster.getDataBuffer.asInstanceOf[DataBufferInt].getData
			val buffer = ByteBuffer.allocateDirect(data.length * 4)
			buffer.order(ByteOrder.nativeOrder)
			buffer.asIntBuffer.put(data, 0, data.length)
			buffer.rewind
			buffer
	}

	private def makeBufferForTYPE_4BYTE_ABGR(image: BufferedImage): ByteBuffer = {
			val data = image.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
			val buffer = ByteBuffer.allocateDirect(data.length)
			buffer.order(ByteOrder.nativeOrder)
			buffer.put(data, 0, data.length)
			buffer.rewind
			// ABGR -> RGBA
			for (val pos <- 0 to buffer.limit-1; pos % 4 == 0) {
				val tmp1 = buffer.get(pos)
				buffer.put(pos, buffer.get(pos+3))
				buffer.put(pos+3, tmp1)
				val tmp2 = buffer.get(pos+1)
				buffer.put(pos+1, buffer.get(pos+2))
				buffer.put(pos+2, tmp2)
			}
			buffer
	}
	
	def freeTexture(textureId: TextureId) {
		val textureIds = ByteBuffer.allocateDirect(4).asIntBuffer // TODO: hardcoded value...
		textureIds.put(0, textureId.id.asInstanceOf[Int])
		gl2.glDeleteTextures(1, textureIds)
	}

	def drawImage(x: Int, y: Int, image: BufferedImage) {
		//gl2.glRasterPos2i(x, y)
		gl2.glWindowPos2i(x,y)
		gl2.glDrawPixels(image.getWidth, image.getHeight, GL2GL3.GL_BGRA, GL.GL_UNSIGNED_BYTE, makeBufferForTYPE_INT_ARGB(image))
		//gl2.glDrawPixels(image.getWidth, image.getHeight, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, makeBufferForTYPE_4BYTE_ABGR(image))
	}
	
	private def glFace(face: Face): Int = face match {
		case Front => GL.GL_FRONT
		case Back => GL.GL_BACK
		case FrontAndBack => GL.GL_FRONT_AND_BACK
	}
	
	private def glMode(mode: DrawingMode): Int = mode match {
		case Point => GL2GL3.GL_POINT
		case Line => GL2GL3.GL_LINE
		case Fill => GL2GL3.GL_FILL
	}
	
	private def glLightType(lightType: LightType): Int = lightType match {
		case AmbientLight => GLLightingFunc.GL_AMBIENT
		case DiffuseLight => GLLightingFunc.GL_DIFFUSE
		case SpecularLight => GLLightingFunc.GL_SPECULAR
		case EmissionLight => GLLightingFunc.GL_EMISSION
		case AmbientAndDiffuseLight => GLLightingFunc.GL_AMBIENT_AND_DIFFUSE
	}
	
	private def glShadeModel(shadeModel: ShadeModel): Int = shadeModel match {
		case Flat => GLLightingFunc.GL_FLAT
		case Smooth => GLLightingFunc.GL_SMOOTH
	}

	private def glLightInstance(instance: LightInstance): Int = instance match {
		case LightInstance(0) => GLLightingFunc.GL_LIGHT0
		case LightInstance(1) => GLLightingFunc.GL_LIGHT1
		case LightInstance(2) => GLLightingFunc.GL_LIGHT2
		case LightInstance(3) => GLLightingFunc.GL_LIGHT3
		case LightInstance(4) => GLLightingFunc.GL_LIGHT4
		case LightInstance(5) => GLLightingFunc.GL_LIGHT5
		case LightInstance(6) => GLLightingFunc.GL_LIGHT6
		case LightInstance(7) => GLLightingFunc.GL_LIGHT7
	}
	
}
