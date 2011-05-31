package scalascenegraph.shaders

import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.core._

class BlinnPhongShader extends Program {

    vertexShaderSource = getStreamAsString(getClass.getResourceAsStream("blinnphongdir.vert"))
    fragmentShaderSource = getStreamAsString(getClass.getResourceAsStream("blinnphongdir.frag"))
    attributes = Map(POSITION_ATTRIBUTE_INDEX -> "vPosition", NORMAL_ATTRIBUTE_INDEX -> "vNormal")

    val lightDirection = new Vector3D(0.0f, 0.0f, -1.0f)
    val ambientLight = new Color(0.2f, 0.2f, 0.2f, 1.0f)
    
    val mvpUniform = new Uniform(this, "mvp")
    val mvUniform = new Uniform(this, "mv")
    val colorUniform = new Uniform(this, "color")
    val lightDirectionUniform = new Uniform(this, "lightDir")
    val ambientLightUniform = new Uniform(this, "ambientLight")
    
    override def prepareUniforms(context: Context) {
        mvpUniform.prepare(context)
        mvUniform.prepare(context)
        colorUniform.prepare(context)
        lightDirectionUniform.prepare(context)
    }
    
    override def setUniforms(context: Context) {
        context.gl.glUniformMatrix4fv(mvpUniform.id, 1, false, context.matrixStack.getModelViewProjectionMatrix.m, 0)
        context.gl.glUniformMatrix4fv(mvUniform.id, 1, false, context.matrixStack.getModelViewMatrix.m, 0)
        val c = context.currentColor; context.gl.glUniform3f(colorUniform.id, c.r, c.g, c.b)
        context.gl.glUniform3f(lightDirectionUniform.id, lightDirection.x, lightDirection.y, lightDirection.z)
        context.gl.glUniform4f(ambientLightUniform.id, ambientLight.r, ambientLight.g, ambientLight.b, ambientLight.a)
    }
    
}
