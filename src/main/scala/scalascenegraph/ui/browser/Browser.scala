package scalascenegraph.ui.browser

import scalascenegraph.core._

abstract class Browser(val world: World) {

    var camera: Camera = _
  
    def show
    
}
