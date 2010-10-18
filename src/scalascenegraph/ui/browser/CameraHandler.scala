package scalascenegraph.ui.browser

import scalascenegraph.core._

class CameraHandler extends Renderable {

	private var xT: Float = 0 // translation along x axis
	private var yT: Float = 0 // translation along y axis
	private var zT: Float = 0 // translation along z axis
	private var xR: Float = 0 // rotation around x axis
	private var yR: Float = 0 // rotation around y axis
	private var zR: Float = 0 // rotation around z axis
	
	def render(context: Context) {
		val tFac = 0.1f * 60 / (if (context.frameRate > 0) context.frameRate else 60)
		val rFac = 1.0f * 60 / (if (context.frameRate > 0) context.frameRate else 60)
		if (!context.controlKeyPressed && !context.shiftKeyPressed) {
			if (context.upKeyPressed) { zT += tFac }
			if (context.downKeyPressed) { zT -= tFac }
			if (context.leftKeyPressed) { yR -= rFac }
			if (context.rightKeyPressed) { yR += rFac }
		} else if (context.controlKeyPressed && !context.shiftKeyPressed) {
			if (context.upKeyPressed) { yT -= tFac }
			if (context.downKeyPressed) { yT += tFac }
			if (context.leftKeyPressed) { xT += tFac }
			if (context.rightKeyPressed) { xT -= tFac }
		} else if (!context.controlKeyPressed && context.shiftKeyPressed) {
			if (context.upKeyPressed) { xR += rFac }
			if (context.downKeyPressed) { xR -= rFac }
			if (context.leftKeyPressed) { zR -= rFac }
			if (context.rightKeyPressed) { zR += rFac }
		}
		if (context.spaceKeyPressed) {
			xT = 0; yT = 0; zT = 0; xR = 0; yR = 0; zR = 0
		}
		context.gl.glTranslatef(xT, yT, zT)
		context.gl.glRotatef(xR, 1.0f, 0.0f, 0.0f)
		context.gl.glRotatef(yR, 0.0f, 1.0f, 0.0f)
		context.gl.glRotatef(zR, 0.0f, 0.0f, 1.0f)
	}
	
}
