package scalascenegraph.core

import java.awt.{Color => JColor}
import javax.media.opengl._
import java.nio._
import scala.collection.mutable._
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
import scalascenegraph.builders._

/**
 * A Renderable class is a class that will execute OpenGL commands.
 */
trait Renderable {

    def render(context: Context)

}

/**
 * The base class for any scene graph node.
 */
trait Node extends Renderable {

    /**
     * The parent node of this node in the graph.
     */
    //var parent: Node = null
    
    /**
     * Attaches the state given in parameter to this node,
     * thus making it part of the scene graph.
     */
    def attach(state: State) {
        throw new UnsupportedOperationException
    }

    /**
     * Attaches the node given in parameter to this node,
     * thus making is part of the scene graph.
     */
    def attach(child: Node) {
        throw new UnsupportedOperationException
    }
    
    /**
     * Attaches the resource given in parameter to this node,
     * thus making is part of the scene graph.
     */
    def attach(resource: Resource) {
        throw new UnsupportedOperationException
    }
    
    /**
     * Visitor pattern support ...
     */
    def accept(visitor: NodeVisitor) {}
}

/**
 * Visitor pattern support ...
 */
abstract class NodeVisitor(context: Context) {
    def visit(group: Group)
}

/**
 * Wraps a node so that the specified hook can be called before the node is
 * rendered, for each frame. The hook code allows the user to change some node
 * attributes before rendering, thus making the wrapped node dynamic. 
 */
class DynamicNode[T <: Node](val hook: NodeHook[T], val node: T) extends Node {
    
    def render(context: Context) {
        hook(node, context)
        node.render(context)
    }
}

/**
 * A node that is a simple container for other nodes.
 */
class Group extends Node {
  
    val children = new ArrayBuffer[Node]
    val states = new ArrayBuffer[State]
    val resources = new ArrayBuffer[Resource]
    
    override def attach(child: Node) {
        children += child
    }
  
    override def attach(state: State) {
        states += state
    }
    
    override def attach(resource: Resource) {
        resources += resource
    }
    
    override def render(context: Context) {
        states.foreach { state => state.preRender(context) }
        children.foreach { child => child.render(context) }
        states.reverse.foreach { state => state.postRender(context) }
    }
    
    override def accept(visitor: NodeVisitor) {
        visitor.visit(this)
    }
    
}

/**
 * The top node in a scene graph.
 */
class World extends Group {

    override def render(context: Context) {
        context.gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
        super.render(context)
        context.gl.glFlush
    }
  
}

/**
 * A marker class for nodes that behave as overlays.
 * An overlay is a graphic item that is drawn on top in the current frame
 * buffer, regardless of the depth buffer state.
 */
abstract class Overlay extends Node

/**
 * An overlay made of an image.
 */
class ImageOverlay(var x: Int = 0,
                   var y: Int = 0,
                    var width: Int,
                   var height: Int,
                   var imageType: ImageType,
                   var rawImage: ByteBuffer)
extends Overlay {

    override def render(context: Context) {
        if (rawImage != null) {
            context.gl.glWindowPos2i(x, y)
            context.gl.glDrawPixels(width, height, imageType, GL_UNSIGNED_BYTE, rawImage)
        }
    }
    
}

/**
 * An overlay made of text.
 */
class TextOverlay(var x: Int = 0,
                  var y: Int = 0,
                  var font: Font,
                  var text: String)
extends Overlay {

    override def render(context: Context) {
        // TODO: move to the 'init' part
        // TODO: should use opengl default
        context.gl.glPixelStorei(GL_UNPACK_ALIGNMENT, 1)
        
        context.gl.glWindowPos2i(x, y)
        text.foreach { c => {
            val character = font.characters(c)
            context.gl.glBitmap(character.width, character.height, 0, 0, character.width, 0, character.bitmap)
        }}
    }
    
}


abstract class Geometry extends Node

class CompositeGeometry extends Geometry {

    var geometries : List[Geometry] = Nil
    
    def addGeometry(geometry: Geometry) {
        geometries = geometry :: geometries
    }
    
    def render(context: Context) {
        geometries.foreach { geometry => geometry.render(context) }
    }
    
}

class SimpleGeometry[VertexBuffer <: java.nio.Buffer, ColorBuffer <: java.nio.Buffer]
(
    val renderable: Renderable,
    val vertices: Vertices[VertexBuffer],
    val normals: Normals,
    val color: Color,
    val colors: Colors[ColorBuffer],
    val textureCoordinates: TextureCoordinates,
    val texture: Texture
)
extends Geometry {

    def this(vertices: Vertices[VertexBuffer]) =
        this(RenderableBuilder.createRenderable(vertices), vertices, null, null, null, null, null)

    def this(vertices: Vertices[VertexBuffer], normals: Normals) =
        this(RenderableBuilder.createRenderable(vertices, normals), vertices, normals, null, null, null, null)

    // TODO: remove (use a color state)
    def this(vertices: Vertices[VertexBuffer], color: Color) =
        this(RenderableBuilder.createRenderable(vertices, color), vertices, null, color, null, null, null)

    def this(vertices: Vertices[VertexBuffer], color: Color, normals: Normals) =
        this(RenderableBuilder.createRenderable(vertices, color, normals), vertices, normals, color, null, null, null)

    def this(vertices: Vertices[VertexBuffer], colors: Colors[ColorBuffer]) =
        this(RenderableBuilder.createRenderable(vertices, colors), vertices, null, null, colors, null, null)

    def this(vertices: Vertices[VertexBuffer], colors: Colors[ColorBuffer], normals: Normals) =
        this(RenderableBuilder.createRenderable(vertices, colors, normals), vertices, normals, null, colors, null, null)

    def this(vertices: Vertices[VertexBuffer], textureCoordinates: TextureCoordinates, texture: Texture) =
        this(RenderableBuilder.createRenderable(vertices, textureCoordinates, texture), vertices, null, null, null, textureCoordinates, texture)

    def this(vertices: Vertices[VertexBuffer], textureCoordinates: TextureCoordinates, texture: Texture, normals: Normals) =
        this(RenderableBuilder.createRenderable(vertices, textureCoordinates, texture, normals), vertices, normals, null, null, textureCoordinates, texture)

    def render(context: Context) {
        renderable.render(context)
    }

}

// TODO: nothing related to VBO here, apart from the name...
class SimpleGeometryVBO(val renderable: Renderable) extends Geometry {

    def render(context: Context) {
        renderable.render(context)
    }

}

class BufferedGeometry(val attributes: List[VertexAttributeObject], val indicesCount: Int, val primitiveType: Int) extends Geometry {

    def render(context: Context) {
    	attributes.foreach { attribute =>
            attribute.prepare(context)
    		context.gl.glEnableVertexAttribArray(attribute.attributeIndex)
    		context.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, attribute.bufferName)
    		context.gl.glVertexAttribPointer(attribute.attributeIndex, attribute.componentCount, attribute.dataType, false, 0, 0)
    	}
        context.gl.glDrawArrays(primitiveType, 0, indicesCount)
    	attributes.foreach {
            attribute => context.gl.glDisableVertexAttribArray(attribute.attributeIndex)
        }
        context.gl.glBindBuffer(GL.GL_ARRAY_BUFFER, 0)
    }

}
