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
import scalascenegraph.core.Utils._
import scalascenegraph.builders._
import scalascenegraph.core._
import scalascenegraph.shaders._

class Example12 extends Example with WorldBuilder {

    def animate(r: Rotation, c: Context, f: Float) {
        r.angle = sin(c.elapsed / f) * 45.0f + 90.0f
        val x = sin(c.elapsed / f)
        val y = 1.0f
        val z = cos(1.7*c.elapsed / f)
        val rot = normalize(new Vector3D(x, y, z))
        r.x = rot.x
        r.y = rot.y
        r.z = rot.z
    }

    val rotationHook = (r: Rotation, c: Context) => animate(r, c, 360.0f)

    val mytorus = bufferedTorus(30, 1.0f, 0.5f)

    def example =
        world {
            cullFace(On)
            attach(ShaderFactory.default)
            translation(0.0f, 0.0f, -5.0f)
            rotation(0.0f, 0.0f, 0.0f, 0.0f, rotationHook)
            useProgram(ShaderFactory.default)
            attach(mytorus.attributes)
            attach(mytorus)
            showFramesPerSecond
        }

}
