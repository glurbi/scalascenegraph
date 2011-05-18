package scalascenegraph.examples

import javax.swing._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
import scala.math._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.ui.browser._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._
import scalascenegraph.builders._
import scalascenegraph.core._
import scalascenegraph.shaders._
import ExampleUtils._

object Example09 {
    def main(args: Array[String]) {
        val example09 = new Example09
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example09.example, animated = true).show
    }
}

class Example09 extends WorldBuilder {

    val myvertexshadersource =
    """
    uniform float r;
    uniform vec4 color;
    const vec3 lightPos = vec3(-6.0, -6.0, 0.0);
    void main (void)
    {
        vec4 v = gl_Vertex;
        v.z = v.z + sin(r * v.x) * 0.25;
        gl_Position = gl_ModelViewProjectionMatrix * v;
        
        vec3 N = gl_NormalMatrix * normalize(v.xyz);
        vec4 V = gl_ModelViewMatrix * gl_Vertex;
        vec3 L = normalize(lightPos - V.xyz);
        float NdotL = dot(N, L);
        vec4 ambient = vec4(0.2, 0.2, 0.2, 1.0);
        vec4 diffuse = vec4(max(0.0, NdotL));
        gl_FrontColor = color * (ambient + diffuse);
    }
    """
        
    val myfragmentshadersource =
    """
    void main (void)
    {
        gl_FragColor = gl_Color;
    }
    """

    val angleHook = (r: Rotation, c: Context) => {
        r.angle = (c.elapsed / 50.0f) % 360.0f
    }
    
    val uniformHook = (u: UniformState, c: Context) => {
        u.value = (sin(c.elapsed / 1000.0f) * 10.0f).asInstanceOf[Float]
    }

    val myvertexshader = new Shader(GL_VERTEX_SHADER, myvertexshadersource)
    val myfragmentshader = new Shader(GL_FRAGMENT_SHADER, myfragmentshadersource)
    val myprogram = new Program(List(myvertexshader, myfragmentshader), Map())
    val rUniform = new Uniform(myprogram, "r")
    val colorUniform = new Uniform(myprogram, "color")
        
    def example =
        world {
            cullFace(On)
            depthTest(On)
            attach(myvertexshader)
            attach(myfragmentshader)
            attach(myprogram)
            attach(rUniform)
            attach(colorUniform)
            useProgram(myprogram)
            group {
                setUniform(colorUniform, JColor.orange)
                setUniform(rUniform, 0.0f, uniformHook)
                translation(0.0f, 0.0f, -4.0f)
                rotation(0.0f, 1.0f, 0.5f, 1.0f, angleHook)
                box(4.0f, 2.0f, 1.0f, 40, 20, 10, normals = false)
            }
            showFramesPerSecond
        }
    
}
