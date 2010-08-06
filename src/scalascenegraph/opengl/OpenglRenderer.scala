package scalascenegraph.opengl

import java.nio._
import javax.media.opengl._
import javax.media.opengl.fixedfunc._
import com.jogamp.opengl.util._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class OpenglRenderer(val gl2: GL2) extends Renderer { 
	
    def color(color: Color) {
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
        gl2.glMatrixMode(GLMatrixFunc.GL_MODELVIEW)
        gl2.glLoadIdentity
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
    }
    
    def perspective(left: Double, right: Double, bottom: Double, top: Double, near: Double, far: Double) {
        gl2.glMatrixMode(GLMatrixFunc.GL_PROJECTION)
        gl2.glLoadIdentity
        gl2.glFrustum(left, right, bottom, top, near, far)
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
        gl2.glEnableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl2.glEnableClientState(GLPointerFunc.GL_COLOR_ARRAY);
        gl2.glVertexPointer(3, GL.GL_FLOAT, 0, vertices.floatBuffer);
        gl2.glColorPointer(4, GL.GL_FLOAT, 0, colors.floatBuffer);
        gl2.glDrawArrays(GL2.GL_QUADS, 0, vertices.count);
        gl2.glDisableClientState(GLPointerFunc.GL_VERTEX_ARRAY);
        gl2.glDisableClientState(GLPointerFunc.GL_COLOR_ARRAY);
        gl2.glColor4f(color(0), color(1), color(2), color(3))
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

    def pushLightMode {
    	gl2.glPushAttrib(GL2.GL_LIGHTING_BIT)
    }
    
    def setLightMode(mode: OnOffMode) {
    	mode match {
    		case On => gl2.glEnable(GLLightingFunc.GL_LIGHTING)
    		case Off => gl2.glDisable(GLLightingFunc.GL_LIGHTING)
    	}
    }
    
	def setAmbientLight(intensity: Array[Float]) {
		gl2.glLightModelfv(GL2ES1.GL_LIGHT_MODEL_AMBIENT, intensity, 0)
	}

	def setMaterial(face: Face, lightType: LightType, color: Color) {
		gl2.glMaterialfv(glFace(face), glLightType(lightType), color.asFloatArray, 0)
	}

	def enableLight(lightType: LightType, position: Position, color: Color) {
		val p = position.asFloatArray
		gl2.glLightfv(0, glLightType(lightType), color.asFloatArray, 0)
		gl2.glLightfv(0, GLLightingFunc.GL_POSITION, Array(p(0), p(1), p(2), 1.0f), 0)
		gl2.glEnable(GLLightingFunc.GL_LIGHT0)
	}
	
	def disableLight() {
		gl2.glDisable(GLLightingFunc.GL_LIGHT0)
	}
	
    def popLightMode {
    	gl2.glPopAttrib
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
	}
	
}
