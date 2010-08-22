package scalascenegraph.core

import java.io._
import java.nio._
import java.awt.image._
import javax.imageio._
import javax.media.opengl._

object Texture {
	
	def main(args: Array[String]) {
		val t = new Texture(Texture.getClass.getResourceAsStream("/scalascenegraph/examples/marble.png"))
		t.prepare(null)
	}
}

class Texture(in: InputStream) {

	def prepare(gl2: GL2) {
		val image = ImageIO.read(in);
		val buffer = image.getType match {
			case BufferedImage.TYPE_3BYTE_BGR => makeBufferForTYPE_3BYTE_BGR(image)
		}
		
		val textureIds = ByteBuffer.allocateDirect(4).asIntBuffer // TODO: hardcoded value...
		gl2.glGenTextures(1, textureIds)
		val textureId = textureIds.get(0)
		
		gl2.glBindTexture(GL.GL_TEXTURE_2D, textureId)

		gl2.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, image.getWidth, image.getHeight, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, buffer);
	}
	
	private def makeBufferForTYPE_3BYTE_BGR(image: BufferedImage): ByteBuffer = {
		val data = image.getRaster.getDataBuffer.asInstanceOf[DataBufferByte].getData
		val buffer = ByteBuffer.allocateDirect(data.length)
		buffer.order(ByteOrder.nativeOrder)
		buffer.put(data, 0, data.length)
	}
	
}