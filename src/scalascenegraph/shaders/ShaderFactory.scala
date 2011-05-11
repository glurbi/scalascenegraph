package scalascenegraph.shaders

import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.core._

object ShaderFactory {

    lazy val default = {
        val vertex = getStreamAsString(getClass.getResourceAsStream("default.vert"))
        val fragment = getStreamAsString(getClass.getResourceAsStream("default.frag"))
        new Program(vertex, fragment, Map(POSITION_ATTRIBUTE_INDEX -> "position")) {
            val mvpUniform = new Uniform(this, "mvp")            
            override def prepareUniforms(context: Context) {
                mvpUniform.prepare(context)
            }
            override def setUniforms(context: Context) {
                context.gl.glUniformMatrix4fv(mvpUniform.id, 1, false, context.matrixStack.getModelViewProjectionMatrix.m, 0)
            }
        }
    }
    
}
