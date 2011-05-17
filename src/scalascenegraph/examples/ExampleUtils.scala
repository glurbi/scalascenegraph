package scalascenegraph.examples

import scala.math._

import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.builders._
import scalascenegraph.core._
import scalascenegraph.shaders._
import ExampleUtils._

object ExampleUtils {

    def rotationHook(f: Float) = (r: Rotation, c: Context) => {
        r.angle = sin(c.elapsed / f) * 45.0f + 90.0f
        val x = sin(c.elapsed / f)
        val y = 1.0f
        val z = cos(1.7*c.elapsed / f)
        val rot = normalize(new Vector3D(x, y, z))
        r.x = rot.x
        r.y = rot.y
        r.z = rot.z
    }

}

