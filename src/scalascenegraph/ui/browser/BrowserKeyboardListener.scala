package scalascenegraph.ui.browser

import java.lang.{Boolean => JBoolean}
import java.awt.event._
import java.util._
import java.util.concurrent._

class BrowserKeyboardListener extends KeyListener {

	private val pressedKeys = Collections.newSetFromMap(new ConcurrentHashMap[Int, JBoolean])
	
	def keyPressed(e: KeyEvent) { pressedKeys.add(e.getKeyCode)	}
	def keyReleased(e: KeyEvent) { pressedKeys.remove(e.getKeyCode) }
	def keyTyped(e: KeyEvent) {}
	
	def isKeyPressed(keyCode: Int) = pressedKeys.contains(keyCode)
}
