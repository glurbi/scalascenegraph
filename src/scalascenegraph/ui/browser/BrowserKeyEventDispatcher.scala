package scalascenegraph.ui.browser

import java.lang.{Boolean => JBoolean}
import java.awt._
import java.awt.event._
import java.util._
import java.util.concurrent._

class BrowserKeyEventDispatcher extends KeyEventDispatcher {

    val pressedKeys = Collections.newSetFromMap(new ConcurrentHashMap[Int, JBoolean])
    
    def dispatchKeyEvent(e: KeyEvent): Boolean = {
        e.getID match {
            case KeyEvent.KEY_PRESSED => pressedKeys.add(e.getKeyCode)
            case KeyEvent.KEY_RELEASED => pressedKeys.remove(e.getKeyCode)
            case KeyEvent.KEY_TYPED =>
        }
        true
    }
    
    def isKeyPressed(keyCode: Int) = pressedKeys.contains(keyCode)
}
