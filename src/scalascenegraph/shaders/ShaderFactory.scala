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

    lazy val vcolor = {
        val vertex = getStreamAsString(getClass.getResourceAsStream("vcolor.vert"))
        val fragment = getStreamAsString(getClass.getResourceAsStream("vcolor.frag"))
        new Program(vertex, fragment, Map(POSITION_ATTRIBUTE_INDEX -> "position", COLOR_ATTRIBUTE_INDEX -> "color"))
        {
            val mvpUniform = new Uniform(this, "mvp")            
            override def prepareUniforms(context: Context) {
                mvpUniform.prepare(context)
            }
            override def setUniforms(context: Context) {
                context.gl.glUniformMatrix4fv(mvpUniform.id, 1, false, context.matrixStack.getModelViewProjectionMatrix.m, 0)
            }
        }
    }
    
    lazy val vorientcolor = {
        val vertex = getStreamAsString(getClass.getResourceAsStream("vorientcolor.vert"))
        val fragment = getStreamAsString(getClass.getResourceAsStream("vorientcolor.frag"))
        new Program(vertex, fragment, Map(POSITION_ATTRIBUTE_INDEX -> "position"))
        {
            val mvpUniform = new Uniform(this, "mvp")            
            override def prepareUniforms(context: Context) {
                mvpUniform.prepare(context)
            }
            override def setUniforms(context: Context) {
                context.gl.glUniformMatrix4fv(mvpUniform.id, 1, false, context.matrixStack.getModelViewProjectionMatrix.m, 0)
            }
        }
    }
    
    lazy val flatvcolor = {
        val vertex = getStreamAsString(getClass.getResourceAsStream("flatvcolor.vert"))
        val fragment = getStreamAsString(getClass.getResourceAsStream("flatvcolor.frag"))
        new Program(vertex, fragment, Map(POSITION_ATTRIBUTE_INDEX -> "position"))
        {
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
