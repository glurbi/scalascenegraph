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
            val currentColorUniform = new Uniform(this, "currentColor")            
            override def prepareUniforms(context: Context) {
                mvpUniform.prepare(context)
                currentColorUniform.prepare(context)
            }
            override def setUniforms(context: Context) {
                context.gl.glUniformMatrix4fv(mvpUniform.id, 1, false, context.matrixStack.getModelViewProjectionMatrix.m, 0)
                val c = context.currentColor; context.gl.glUniform4f(currentColorUniform.id, c.r, c.g, c.b, c.a)
            }
        }
    }
    
}
