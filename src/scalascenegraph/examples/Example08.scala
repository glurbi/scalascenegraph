package scalascenegraph.examples

import javax.swing._
import java.awt._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import java.awt.image._
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

object Example08 {
    def main(args: Array[String]) {
        val example08 = new Example08
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
        Browser.getDefault(world = example08.example, animated = true).show
    }
}

class Example08 extends WorldBuilder {

    val fpsHook = (o: TextOverlay, c: Context) => {
        o.x = 10
        o.y = c.height - 30
        o.text = "FPS: " + c.frameRate
    }
    
    val centerHook = (o: ImageOverlay, c: Context) => {
        o.x = c.width / 2 - o.width / 2
        o.y = c.height / 2 - o.height / 2
    }
    
    val angleHook = (r: Rotation, c: Context) => {
        r.angle = (c.elapsed / 20.0f) % 360.0f
    }
    
    val image = {
        val img = new BufferedImage(400, 200, BufferedImage.TYPE_4BYTE_ABGR)
        val g2d = img.createGraphics
        val font = new JFont(g2d.getFont.getFontName, JFont.BOLD, 25)
        val metrics = g2d.getFontMetrics
        val text = "...Overlay..."
        g2d.setFont(font)
        g2d.setColor(new JColor(0.0f, 0.0f, 0.0f, 0.0f))
        g2d.fillRect(0, 0, img.getWidth, img.getHeight)
        g2d.setColor(JColor.red)
        g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND))
        g2d.drawRoundRect(0, 0, img.getWidth-1, img.getHeight-1, 40, 40)
        val x = img.getWidth / 2 - metrics.stringWidth(text)
        val y = img.getHeight / 2 + metrics.getHeight / 2
        g2d.drawString(text, x, y)
        g2d.dispose
        img
    }
    
    val defaultFont = FontBuilder.create(new JFont("Default", JFont.PLAIN, 20))
    val serifFont = FontBuilder.create(new JFont("Serif", JFont.BOLD, 20))

    def example =
        world {
            attach(defaultFont)
            attach(serifFont)
            depthTest(On)
            blending(On)
            light(On)
            light(GL_LIGHT0, On)
            ambient(Intensity(0.4f, 0.4f, 0.4f, 1.0f))
            light(GL_LIGHT0, new Position3D(-2.0f, -2.0f, 0.0f))
            light(GL_LIGHT0, GL_DIFFUSE, JColor.white)
            group {
                translation(0.0f, 0.0f, -1.8f)
                rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
                material(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, JColor.cyan)
                cube(normals = true)
            }
            overlay(0, 0, image, centerHook)
            overlay(10, 10, defaultFont, "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-+")
            overlay(10, 40, defaultFont, "abcdefghijklmnopqrstuvwxyz~`|{}[]\"\\/?><,.=:;")
            overlay(0, 0, serifFont, "", fpsHook)
        }
        
}
