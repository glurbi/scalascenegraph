package scalascenegraph.builders

import java.nio._
import javax.media.opengl._
import javax.media.opengl.GL._

import scalascenegraph.core._

class GeometryBuilder {

	private class VertexAttributeData {
		var buffer: Buffer
		var attributeIndex: Int
		var dataType: Int
		var components: Int
	}
	
    private var vertexCount = 0
    private var primitiveType = GL_TRIANGLES // TODO: do not use opengl value here
    private var attributesData: List[VertexAttributeData] = Nil
    
    def addAtribute(attributeIndex: Int, components: Int, dataType: Int, data: Object): GeometryBuilder = {
    	val attribute = new VertexAttributeData
    	attribute.attributeIndex = attributeIndex
    	attribute.components = components
    	attribute.dataType = dataType
    	attribute.buffer = makeBuffer(dataType, data)
    	attributesData = attribute +: attributesData
        this
    }
    
    def setVertexCount(vertexCount: Int): GeometryBuilder = {
        this.vertexCount = vertexCount
        this
    }

    def setPrimitiveType(primitiveType: Int): GeometryBuilder = {
        this.primitiveType = primitiveType
        this
    }
    
    def build(gl: GL4): Geometry = {
        val bufferNames = new Array[Int](attributesData.size)
        gl.glGenBuffers(attributesData.size, bufferNames, 0)
        var attributes: List[VertexAttribute] = Nil
        for (i <- 0 until attributesData.size) {
        	val attribute = attributesData(i)
        	val size = componentSize(attribute.dataType) * attribute.buffer.limit
        	gl.glBindBuffer(GL_ARRAY_BUFFER, bufferNames(i))
        	gl.glBufferData(GL_ARRAY_BUFFER, size, attribute.buffer, GL_STATIC_DRAW)
        	attributes = new VertexAttribute(attribute.attributeIndex, bufferNames(i), attribute.components, attribute.dataType) +: attributes
        }
        new SimpleGeometry(attributes, vertexCount, primitiveType)
    }

    private def componentSize(dataType: Int): Int = {
        dataType match {
            case GL_FLOAT => 4
            case GL_UNSIGNED_BYTE => 1
            case _ => throw new UnsupportedOperationException("Data type not supported")
        }
    }
    
    private def makeBuffer(dataType: Int, data: Object): Buffer = {
        dataType match {
            case GL_FLOAT => makeFloatBuffer(data.asInstanceOf[Array[Float]])
            case GL_UNSIGNED_BYTE => makeUbyteBuffer(data.asInstanceOf[Array[Byte]])
            case default => throw new UnsupportedOperationException("Data type not supported")
        }
    }
    
    private def makeFloatBuffer(data: Array[Float]): Buffer = {
        ByteBuffer.allocateDirect(4*data.length)
                  .order(ByteOrder.nativeOrder)
                  .asFloatBuffer
                  .put(data)
                  .flip
    }
    
    private def makeUbyteBuffer(data: Array[Byte]): Buffer = {
        ByteBuffer.allocateDirect(data.length)
                  .put(data)
                  .flip
    }
    
}

