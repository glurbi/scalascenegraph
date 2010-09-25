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

    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture) {
		gl.glBindTexture(GL_TEXTURE_2D, texture.id)
        gl.glEnableClientState(GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer)
        gl.glTexCoordPointer(2, GL_FLOAT, 0, textureCoordinates.floatBuffer)
        gl.glDrawArrays(GL_QUADS, 0, vertices.count)
        gl.glDisableClientState(GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
    }
    
    def quads(vertices: Vertices, textureCoordinates: TextureCoordinates, texture: Texture, normals: Normals) {
		gl.glBindTexture(GL_TEXTURE_2D, texture.id)
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
/*    
	def quads(vertices: Vertices, normals: Normals) {
        gl.glEnableClientState(GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL_NORMAL_ARRAY);
        gl.glNormalPointer(GL_FLOAT, 0, normals.floatBuffer)
        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.floatBuffer);
        gl.glDrawArrays(GL_QUADS, 0, vertices.count);
        gl.glDisableClientState(GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL_VERTEX_ARRAY);
	}
	*/
    
	def lineStrip(vbo: VertexBufferObject) {
		gl.glEnableClientState(GL_VERTEX_ARRAY)
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.id)
		gl.glVertexPointer(2, GL_FLOAT, 0, 0);
        gl.glDrawArrays(GL_LINE_STRIP, 0, vbo.count)
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
		gl.glDisableClientState(GL_VERTEX_ARRAY)
	}

	def lineStrips(vbo: VertexBufferObject, firsts: IntBuffer, counts: IntBuffer) {
		gl.glEnableClientState(GL_VERTEX_ARRAY)
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.id)
		gl.glVertexPointer(2, GL_FLOAT, 0, 0);
        gl.glMultiDrawArrays(GL_LINE_STRIP, firsts, counts, firsts.capacity)
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
		gl.glDisableClientState(GL_VERTEX_ARRAY)
	}
	
}
