package scalascenegraph.examples

import javax.swing._
import java.awt.{Color => JColor }
import java.awt.event._
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

object Example03 {
    def main(args: Array[String]) {
        val example03 = new Example03
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example03.example, animated = true).show
    }
}

class Example03 extends WorldBuilder {

    val mycone = cone(20, 10, 4.0f, 6.0f, normals = true)
    val mysphere1 = bufferedSphereWithNormals(20, 4.0f)
    val mysphere2 = bufferedSphereWithNormals(15, 5.0f)
    val myellipsoid = ellipsoid(20, 20, 6.0f, 6.0f, 2.0f, normals = true)
    val mytorus = torus(30, 5.0f, 1.5f, normals = true)
    
    val mylighter = new BlinnPhongShader

    val nPressed = (context: Context) => context.pressedKeys.contains(KeyEvent.VK_N)

    val angleHook = (r: Rotation, c: Context) => {
        r.angle = (c.elapsed / 50.0f) % 360.0f
    }
    
    val colorMaterialObjects =
        detached {
            colorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE)
            group {
                color(JColor.cyan)
                translation(-6.0f, 0.0f, 1.0f)
                useProgram(mylighter)
                attach(mysphere1)
                conditional(nPressed) { attach(normals(mysphere1)) }
            }
            group {
                color(JColor.red)
                translation(6.0f, 0.0f, 0.0f)
                attach(mycone)
                conditional(nPressed) { attach(normals(mycone)) }
            }
            group {
                color(JColor.green)
                translation(-15.0f, 0.0f, 12.0f)
                rotation(90.0f, 0.0f, 1.0f, 0.0f, angleHook)
                attach(myellipsoid)
                conditional(nPressed) { attach(normals(myellipsoid)) }
            }
            group {
                color(JColor.pink)
                translation(15.0f, 0.0f, 12.0f)
                rotation(90.0f, 0.0f, 1.0f, 0.0f, angleHook)
                attach(mytorus)
                conditional(nPressed) { attach(normals(mytorus)) }
            }
        }

    val materialObjects =
        detached {
            group {
                cullFace(Off)
                lineWidth(4.0f)
                group {
                    polygonMode(GL_FRONT_AND_BACK, GL_LINE)
                    translation(-10.0f, 0.0f, 30.0f)
                    rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
                    color(JColor.yellow)
                    useProgram(mylighter)
                    attach(mysphere2)
                }
                group {
                    polygonMode(GL_FRONT_AND_BACK, GL_LINE)
                    translation(0.0f, -10.0f, 30.0f)
                    rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
                    material(GL_FRONT_AND_BACK, GL_AMBIENT, JColor.blue)
                    material(GL_FRONT_AND_BACK, GL_DIFFUSE, JColor.blue)
                    cube(5.0f, normals = true)
                }
            }
            group {
                translation(10.0f, 0.0f, 30.0f)
                color(JColor.orange)
                useProgram(mylighter)
                attach(mysphere2)
            }
            group {
                translation(0.0f, 10.0f, 30.0f)
                rotation(0.0f, -1.0f, -0.5f, 1.0f, angleHook)
                material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.magenta)
                cube(5.0f, normals = true)
            }
        }

    def example =
        world {
            depthTest(On)
            group {
                cullFace(On)
                light(On)
                light(GL_LIGHT0, On)
                light(GL_LIGHT0, GL_POSITION, Array(0.0f, 0.0f, 0.0f, 1.0f))
                light(GL_LIGHT0, GL_DIFFUSE, JColor.white)
                ambient(Intensity(0.2f, 0.2f, 0.2f, 1.0f))
                translation(0.0f, 0.0f, -15.0f)
                attach(colorMaterialObjects)
                attach(materialObjects)
            }
            group {
                color(JColor.gray)
                polygonMode(GL_FRONT_AND_BACK, GL_LINE)
                useProgram(ShaderFactory.default)
                attach(bufferedBox(40.0f, 40.0f, 40.0f, 40, 40, 40, normals = false))
            }
        }

}
