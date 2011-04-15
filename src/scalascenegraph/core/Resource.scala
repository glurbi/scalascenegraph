package scalascenegraph.core

import java.io._
import java.nio._
import javax.imageio._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core.Predefs._

/**
 * A class implementing the resource trait has the responsibility to load and
 * free renderer or system resources, that will be used for the lifetime of their
 * attachment to the scene graph.
 */
trait Resource {

    /**
     * Called once, after being attached to the scene graph.
     * Can be used to load resources (textures, bitmaps, geometries...)
     */
    def prepare(context: Context) {}
    
    /**
     * Called once, after being detached from the scene graph.
     * Resources should be freed here.
     */
    def dispose(context: Context) {}
    
}

class Shader(shaderType: ShaderType, source: String) extends Resource {

    var id: ShaderId = _
    
    override def prepare(context: Context) {
        id = context.gl.glCreateShader(shaderType)
        context.gl.glShaderSource(id, 1, Array(source), Array(source.size), 0)
        val log = new Array[Byte](8192)
        val length = Array(0)
        context.gl.glCompileShader(id)
        context.gl.glGetShaderInfoLog(id, log.size, length, 0, log, 0)
        println(new String(log, 0, length(0)))
    }
    
    override def dispose(context: Context) {
        context.gl.glDeleteShader(id)
    }
    
}

// FIXME: constructors are not so scala-ish...
class Program(private val attributes: Map[Int, String]) extends Resource {

    var id: ProgramId = _
    
    private var shaders: List[Shader] = Nil
    
    private var vertexShaderSource: String = _
    private var fragmentShaderSource: String = _
    
    def this(shaders: List[Shader], attributes: Map[Int, String]) = {
        this(attributes)
        this.shaders = shaders
        this
    }

    def this(vertexShaderSource: String, fragmentShaderSource: String, attributes: Map[Int, String]) = {
        this(attributes)
        this.vertexShaderSource = vertexShaderSource
        this.fragmentShaderSource = fragmentShaderSource
        this
    }
    
    private def makeShadersFromSource(context: Context) {
        if (vertexShaderSource != null) {
            val shader = new Shader(GL_VERTEX_SHADER, vertexShaderSource)
            shader.prepare(context)
            shaders = shader +: shaders
        }
        if (fragmentShaderSource != null) {
            val shader = new Shader(GL_FRAGMENT_SHADER, fragmentShaderSource)
            shader.prepare(context)
            shaders = shader +: shaders
        }
    }
    
    override def prepare(context: Context) {
        if (shaders == Nil) {
            makeShadersFromSource(context)
        }
        id = context.gl.glCreateProgram
        shaders.foreach { shader => context.gl.glAttachShader(id, shader.id) }
        attributes.foreach { attr => context.gl.glBindAttribLocation(id, attr._1, attr._2) }
        val log = new Array[Byte](8192)
        val length = Array(0)
        context.gl.glLinkProgram(id)
        context.gl.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
        println(new String(log, 0, length(0)))
        context.gl.glValidateProgram(id)
        context.gl.glGetProgramInfoLog(id, log.size, length, 0, log, 0)
        println(new String(log, 0, length(0)))
        prepareUniforms(context)
    }
    
    override def dispose(context: Context) {
        context.gl.glDeleteProgram(id)
    }
    
    /**
     * Will be called each time the program will be set for use.
     */
    def setUniforms(context: Context) {}
    
    /**
     * Will be called when this program will be prepared.
     */
    def prepareUniforms(context: Context) {}
}

class Uniform(program: Program, name: String) extends Resource {
    
    var id: UniformId = _
    var value: Any = _

    override def prepare(context: Context) {
        id = context.gl.glGetUniformLocation(program.id, name)
    }
    
}

class Texture(in: InputStream) extends Resource {

    var id: TextureId = _
    
    override def prepare(context: Context) {
        import context.gl
        val image = ImageIO.read(in)
        gl.glEnable(GL_TEXTURE_2D)
        val buffer = Utils.makeDirectByteBuffer(image)
        val ids = new Array[Int](1)
        gl.glGenTextures(1, ids, 0)
        id = ids(0)
        gl.glBindTexture(GL_TEXTURE_2D, id)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        image.getColorModel.hasAlpha match {
            case false => gl.glTexImage2D(GL_TEXTURE_2D, 0, 3, image.getWidth, image.getHeight, 0, GL_RGB, GL_UNSIGNED_BYTE, buffer)
            case true => gl.glTexImage2D(GL_TEXTURE_2D, 0, 4, image.getWidth, image.getHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)
        }
    }
    
    override def dispose(context: Context) {
        val ids = Array(id)
        context.gl.glDeleteTextures(1, ids, 0)
    }
    
}

class Character(val char: Char, val width: Int, val height: Int, val bitmap: ByteBuffer)

// TODO: use immutable collection?
class Font(val characters: scala.collection.mutable.Map[Char, Character]) extends Resource

class VertexBufferObject[T <: Buffer](vertices: Vertices[T]) extends Resource {
    
    var id: VBOId = _
    var count: Int = vertices.count
    val vertexDimension: VertexDimension = vertices.vertexDimension
    val dataType: DataType = vertices.dataType
    val primitiveType: PrimitiveType = vertices.primitiveType
    
    override def prepare(context: Context) {
        val ids = Array[Int](1)
        context.gl.glGenBuffers(1, ids, 0)
        id = ids(0)
        context.gl.glBindBuffer(GL_ARRAY_BUFFER, id)
        context.gl.glBufferData(GL_ARRAY_BUFFER, vertices.buffer.limit * 4, vertices.buffer, GL_STATIC_DRAW)
        context.gl.glBindBuffer(GL_ARRAY_BUFFER, 0)
    }
    
    override def dispose(context: Context) {
        val ids = Array(id)
        context.gl.glDeleteBuffers(1, ids, 0)
    }
    
}

class VertexAttributeObject(val attributeIndex: Int, val componentCount: Int, val dataType: Int, val buffer: Buffer) extends Resource {

    var bufferName: Int = 0

    override def prepare(context: Context) {
        val tmp = Array[Int](1)
        context.gl.glGenBuffers(1, tmp, 0)
        bufferName = tmp(0)
        val size = componentSize(dataType) * buffer.limit
        context.gl.glBindBuffer(GL_ARRAY_BUFFER, bufferName)
        context.gl.glBufferData(GL_ARRAY_BUFFER, size, buffer, GL_STATIC_DRAW)
    }
    
    override def dispose(context: Context) {
    }
    
    private def componentSize(dataType: Int): Int = {
        dataType match {
            case GL_FLOAT => 4
            case GL_UNSIGNED_BYTE => 1
            case _ => throw new UnsupportedOperationException("Data type not supported")
        }
    }
    
}
