package scalascenegraph.ui.browser

import java.awt.event._

class BrowserMouseListener extends MouseAdapter {

    val mouseAccel = 0.01f
  
    var scale = 1.0f
    var xTranslate = 0.0f
    var yTranslate = 0.0f
    var xRotate = 0.0f
    var yRotate = 0.0f
  
    var lastX: Int = 0
    var lastY: Int = 0
        
    override def mouseWheelMoved(e: MouseWheelEvent) {
        val rotation = e.getWheelRotation
        if (rotation > 0) {
            scale /= rotation * 2
        } else if (rotation < 0) {
            scale *= -rotation * 2
        }
    }

    override def mousePressed(e: MouseEvent) {
        lastX = e.getX
        lastY = e.getY
    }
    
    override def mouseDragged(e: MouseEvent) {
        val difX = e.getX - lastX
        val difY = e.getY - lastY
        lastX = e.getX
        lastY = e.getY
        if ((e.getModifiers & InputEvent.BUTTON1_MASK) != 0) {
            xTranslate += mouseAccel * difX / scale
            yTranslate -= mouseAccel * difY / scale
        } else if ((e.getModifiers & InputEvent.BUTTON3_MASK) != 0) {
            xRotate += difY
            yRotate += difX
        }
    }

}
