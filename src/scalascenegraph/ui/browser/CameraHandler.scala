package scalascenegraph.ui.browser

import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class CameraHandler extends Renderable {

    def render(context: Context) {
        val tFac = 0.5f * 60 / (if (context.frameRate > 0) context.frameRate else 60)
        val rFac = 3.0f * 60 / (if (context.frameRate > 0) context.frameRate else 60)
        if (!context.controlKeyPressed && !context.shiftKeyPressed) {
            if (context.upKeyPressed) { context.camera.moveForward(tFac) }
            if (context.downKeyPressed) { context.camera.moveBackward(tFac) }
            if (context.leftKeyPressed) { context.camera.rotateY(rFac) }
            if (context.rightKeyPressed) { context.camera.rotateY(-rFac) }
        } else if (context.controlKeyPressed && !context.shiftKeyPressed) {
            if (context.upKeyPressed) { context.camera.moveUp(tFac) }
            if (context.downKeyPressed) { context.camera.moveDown(tFac) }
            if (context.leftKeyPressed) { context.camera.moveLeft(tFac) }
            if (context.rightKeyPressed) { context.camera.moveRight(tFac) }
        } else if (!context.controlKeyPressed && context.shiftKeyPressed) {
            if (context.upKeyPressed) { context.camera.rotateX(-rFac) }
            if (context.downKeyPressed) { context.camera.rotateX(rFac) }
            if (context.leftKeyPressed) { context.camera.rotateZ(rFac) }
            if (context.rightKeyPressed) { context.camera.rotateZ(-rFac) }
        }
        if (context.spaceKeyPressed) {
            context.camera.reset
        }
        if (context.pressedKeys.size != 0) {
            println(context.camera)
        }
    }

}
