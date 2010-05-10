package scalascenegraph.ui.browser

import scalascenegraph.core._

abstract class Browser(val world: World) {

    protected var camera: Camera = _

    def getCamera = camera
    def setCamera(camera: Camera) {
        this.camera = camera
        repaint
    }
    
    def show
    def repaint
}
