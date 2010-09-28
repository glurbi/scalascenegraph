package scalascenegraph.builders

import java.nio._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

trait RenderableBuilder {

	def createRenderable[T <: Buffer](vertices: Vertices[T]): Renderable = {
		new Renderable {
			def render(context: Context) {
				import context.gl
				gl.glEnableClientState(GL_VERTEX_ARRAY);
				gl.glVertexPointer(vertices.vertexDimension, GL_FLOAT, 0, vertices.buf);
				gl.glDrawArrays(vertices.primitiveType, 0, vertices.count);
				gl.glDisableClientState(GL_VERTEX_ARRAY);
			}
		}
	}

	def createRenderable[T <: Buffer](vertices: Vertices[T], color: Color): Renderable = {
		new Renderable {
			def render(context: Context) {
				import context.gl
		    	val save = new Array[Float](4)
		    	gl.glGetFloatv(GL_CURRENT_COLOR, save, 0)
		        gl.glColor4f(color.r, color.g, color.b, color.a)
		        gl.glEnableClientState(GL_VERTEX_ARRAY);
		        gl.glVertexPointer(vertices.vertexDimension, GL_FLOAT, 0, vertices.buf);
		        gl.glDrawArrays(vertices.primitiveType, 0, vertices.count);
		        gl.glDisableClientState(GL_VERTEX_ARRAY);
		        gl.glColor4f(save(0), save(1), save(2), save(3))
			}
		}
	}
	
	def createRenderable[T <: Buffer](primitiveType: PrimitiveType, vertices: Vertices[T], colors: Colors): Renderable = {
		new Renderable {
			def render(context: Context) {
				import context.gl
		    	val color = new Array[Float](4)
		    	gl.glGetFloatv(GL_CURRENT_COLOR, color, 0)
		        gl.glEnableClientState(GL_VERTEX_ARRAY)
		        gl.glEnableClientState(GL_COLOR_ARRAY)
		        gl.glVertexPointer(vertices.vertexDimension, GL_FLOAT, 0, vertices.buf)
		        gl.glColorPointer(colors.colorType , GL_FLOAT, 0, colors.floatBuffer)
		        gl.glDrawArrays(primitiveType, 0, vertices.count)
		        gl.glDisableClientState(GL_VERTEX_ARRAY)
		        gl.glDisableClientState(GL_COLOR_ARRAY)
		        gl.glColor4f(color(0), color(1), color(2), color(3))
			}
		}
	}

	def createRenderable[T <: Buffer](vertices: Vertices[T], normals: Normals): Renderable = {
		new Renderable {
			def render(context: Context) {
				import context.gl
		        gl.glEnableClientState(GL_VERTEX_ARRAY);
		        gl.glEnableClientState(GL_NORMAL_ARRAY);
		        gl.glNormalPointer(GL_FLOAT, 0, normals.floatBuffer)
		        gl.glVertexPointer(vertices.vertexDimension, GL_FLOAT, 0, vertices.buf);
		        gl.glDrawArrays(vertices.primitiveType , 0, vertices.count);
		        gl.glDisableClientState(GL_NORMAL_ARRAY);
		        gl.glDisableClientState(GL_VERTEX_ARRAY);
			}
		}
	}

	def createRenderable[T <: Buffer](vertices: Vertices[T],
			textureCoordinates: TextureCoordinates, texture: Texture): Renderable =
	{
		new Renderable {
			def render(context: Context) {
				import context.gl
				gl.glBindTexture(GL_TEXTURE_2D, texture.id)
		        gl.glEnableClientState(GL_VERTEX_ARRAY)
		        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
		        gl.glVertexPointer(vertices.vertexDimension, GL_FLOAT, 0, vertices.buf)
		        gl.glTexCoordPointer(2, GL_FLOAT, 0, textureCoordinates.floatBuffer)
		        gl.glDrawArrays(GL_QUADS, 0, vertices.count)
		        gl.glDisableClientState(GL_VERTEX_ARRAY)
		        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
			}
		}
	}

	def createRenderable[T <: Buffer](
			vertices: Vertices[T], textureCoordinates: TextureCoordinates,
			texture: Texture, normals: Normals): Renderable =
	{
		new Renderable {
			def render(context: Context) {
				import context.gl
				gl.glBindTexture(GL_TEXTURE_2D, texture.id)
		        gl.glEnableClientState(GL_VERTEX_ARRAY)
		        gl.glEnableClientState(GL_TEXTURE_COORD_ARRAY)
		        gl.glVertexPointer(vertices.vertexDimension, GL_FLOAT, 0, vertices.buf)
		        gl.glTexCoordPointer(2, GL_FLOAT, 0, textureCoordinates.floatBuffer)
		        gl.glDrawArrays(GL_QUADS, 0, vertices.count)
		        gl.glDisableClientState(GL_VERTEX_ARRAY)
		        gl.glDisableClientState(GL_TEXTURE_COORD_ARRAY)
			}
		}
	}
	
	def createRenderableVBO[T <: Buffer](vbo: VertexBufferObject[T],
			firsts: IntBuffer, counts: IntBuffer): Renderable =
	{
		new Renderable {
			def render(context: Context) {
				import context.gl
				gl.glEnableClientState(GL_VERTEX_ARRAY)
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.id)
				gl.glVertexPointer(vbo.dim, GL_FLOAT, 0, 0);
				gl.glMultiDrawArrays(vbo.primitiveType, firsts, counts, firsts.capacity)
				gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
				gl.glDisableClientState(GL_VERTEX_ARRAY)
			}
		}
	}
	
	def createRenderableVBO[T <: Buffer](vbo: VertexBufferObject[T]): Renderable = {
		new Renderable {
			def render(context: Context) {
				import context.gl
				gl.glEnableClientState(GL_VERTEX_ARRAY)
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo.id)
				gl.glVertexPointer(vbo.dim, GL_FLOAT, 0, 0);
		        gl.glDrawArrays(vbo.primitiveType, 0, vbo.count)
				gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
				gl.glDisableClientState(GL_VERTEX_ARRAY)
			}
		}
	}

}