package scalascenegraph.builders

import java.nio._
import scala.math._
import scala.collection.mutable._
import com.jogamp.common.nio._
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
import scalascenegraph.core.Utils._

class BufferedGeometryBuilder {

    private var attributes: List[VertexAttributeObject] = Nil
    private var primitiveType = -1
    private var indicesCount = -1

    def create: BufferedGeometry = {
        null
    }
    
}