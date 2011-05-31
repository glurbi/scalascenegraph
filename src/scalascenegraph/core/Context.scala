package scalascenegraph.core

import java.util._
import java.awt.{Color => JColor }
import javax.media.opengl._
import javax.media.opengl.glu._

import scalascenegraph.core.Predefs._

class Context {
    
    var gl: GL3bc = _
    var glu: GLU = _
    
    val creationTime = System.currentTimeMillis
    var lastFrameTimeNano = System.nanoTime
    var currentTime = creationTime
    var elapsed = 0L
    var totalFrameCount = 0L
    var frameRate = 0L
    
    var upKeyPressed: Boolean = _
    var downKeyPressed: Boolean = _
    var rightKeyPressed: Boolean = _
    var leftKeyPressed: Boolean = _
    var controlKeyPressed: Boolean = _
    var shiftKeyPressed: Boolean = _
    var spaceKeyPressed: Boolean = _
    var escapeKeyPressed: Boolean = _
    
    var pressedKeys: Set[Int] = _

    var camera: Camera = _
    
    var width = 0
    var height = 0
    
    def isKeyPressed(keyCode: Int) = pressedKeys.contains(keyCode)
    
    val matrixStack = new MatrixStack
    var currentColor = Color(1.0f, 1.0f, 1.0f, 1.0f)
    var currentProgram: Program = _
}
