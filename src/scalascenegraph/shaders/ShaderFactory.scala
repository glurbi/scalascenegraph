package scalascenegraph.shaders

import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.core._

object ShaderFactory {

    lazy val default = {
        val vertex = getStreamAsString(getClass.getResourceAsStream("default.vert"))
        val fragment = getStreamAsString(getClass.getResourceAsStream("default.frag"))
        new Program(vertex, fragment, Map(POSITION_ATTRIBUTE_INDEX -> "positionAttribute"))
    }
    
}