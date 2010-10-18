package scalascenegraph.ui.browser

import java.awt.event._

import scalascenegraph.core._

class ClippingVolumeHandler extends Renderable {

	var defaultClippingVolume: ClippingVolume = _

	def render(context: Context) {
		val fac = 0.1f * 60 / (if (context.frameRate > 0) context.frameRate else 60)
		if (context.isKeyPressed(KeyEvent.VK_L)) {
			if (context.isKeyPressed(KeyEvent.VK_ADD)) { context.camera.clippingVolume.left += fac }
			if (context.isKeyPressed(KeyEvent.VK_SUBTRACT)) { context.camera.clippingVolume.left -= fac }
		}
		if (context.isKeyPressed(KeyEvent.VK_R)) {
			if (context.isKeyPressed(KeyEvent.VK_ADD)) { context.camera.clippingVolume.right += fac }
			if (context.isKeyPressed(KeyEvent.VK_SUBTRACT)) { context.camera.clippingVolume.right -= fac }
		}
		if (context.isKeyPressed(KeyEvent.VK_T)) {
			if (context.isKeyPressed(KeyEvent.VK_ADD)) { context.camera.clippingVolume.top += fac }
			if (context.isKeyPressed(KeyEvent.VK_SUBTRACT)) { context.camera.clippingVolume.top -= fac }
		}
		if (context.isKeyPressed(KeyEvent.VK_B)) {
			if (context.isKeyPressed(KeyEvent.VK_ADD)) { context.camera.clippingVolume.bottom += fac }
			if (context.isKeyPressed(KeyEvent.VK_SUBTRACT)) { context.camera.clippingVolume.bottom -= fac }
		}
		if (context.isKeyPressed(KeyEvent.VK_N)) {
			if (context.isKeyPressed(KeyEvent.VK_ADD)) { context.camera.clippingVolume.near += fac }
			if (context.isKeyPressed(KeyEvent.VK_SUBTRACT)) { context.camera.clippingVolume.near -= fac }
		}
		if (context.isKeyPressed(KeyEvent.VK_F)) {
			if (context.isKeyPressed(KeyEvent.VK_ADD)) { context.camera.clippingVolume.far += fac }
			if (context.isKeyPressed(KeyEvent.VK_SUBTRACT)) { context.camera.clippingVolume.far -= fac }
		}
		if (context.spaceKeyPressed && defaultClippingVolume != null) {
			context.camera.clippingVolume.left = defaultClippingVolume.left
			context.camera.clippingVolume.right = defaultClippingVolume.right
			context.camera.clippingVolume.bottom = defaultClippingVolume.bottom
			context.camera.clippingVolume.top = defaultClippingVolume.top
			context.camera.clippingVolume.near = defaultClippingVolume.near
			context.camera.clippingVolume.far = defaultClippingVolume.far
		}
	}
	
}
