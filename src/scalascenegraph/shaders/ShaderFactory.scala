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

    def blinnphongdir(dir: Vector3D) = {
        val vertex = getStreamAsString(getClass.getResourceAsStream("blinnphongdir.vert"))
        val fragment = getStreamAsString(getClass.getResourceAsStream("blinnphongdir.frag"))
        new Program(vertex, fragment, Map(POSITION_ATTRIBUTE_INDEX -> "vPosition", NORMAL_ATTRIBUTE_INDEX -> "vNormal"))
        {
            val mvpUniform = new Uniform(this, "mvp")
            val mvUniform = new Uniform(this, "mv")
            val colorUniform = new Uniform(this, "color")            
            val lightDirUniform = new Uniform(this, "lightDir")
            override def prepareUniforms(context: Context) {
                mvpUniform.prepare(context)
                mvUniform.prepare(context)
                colorUniform.prepare(context)
                lightDirUniform.prepare(context)
            }
            override def setUniforms(context: Context) {
                context.gl.glUniformMatrix4fv(mvpUniform.id, 1, false, context.matrixStack.getModelViewProjectionMatrix.m, 0)
                context.gl.glUniformMatrix4fv(mvUniform.id, 1, false, context.matrixStack.getModelViewMatrix.m, 0)
                val c = context.currentColor; context.gl.glUniform3f(colorUniform.id, c.r, c.g, c.b)
                context.gl.glUniform3f(lightDirUniform.id, dir.x, dir.y, dir.z)
            }
        }
    }
    
}
