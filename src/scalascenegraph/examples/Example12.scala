package scalascenegraph.examples

import scala.math._
import java.awt.{Color => JColor }
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
import scalascenegraph.core._

class Example12 extends Example with WorldBuilder {

    val trianglePositions = attributes(POSITION_ATTRIBUTE_INDEX, 3,
                                Array(0.0f, 0.5f, 0.0f,
                                      -0.5f, -0.5f, 0.0f,
                                      0.5f, -0.5f, 0.0f))

    def example =
        world {
            translation(0.0f, 0.0f, -5.0f)
            attach(trianglePositions)
            attach(new BufferedGeometry(attributes = List(trianglePositions),
                                        count = 3,
                                        primitiveType = GL_TRIANGLES))
            showFramesPerSecond
        }

}
