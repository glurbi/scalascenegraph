package scalascenegraph.ui.browser

import java.util.concurrent._
import java.awt._
import java.awt.image._
import javax.imageio._

class BrowserOverlay extends Runnable {
	
	@volatile private var width = 0
	@volatile private var height = 0
	//@volatile private var image: BufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
	@volatile var image: BufferedImage = null
	private val executor = Executors.newSingleThreadScheduledExecutor
	
	//executor.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS)
	executor.submit(this)
	
	def setSize(w: Int, h: Int) {
		width = w
		height = h
	}
	
	def run {
		image = new BufferedImage(500, 300, BufferedImage.TYPE_INT_ARGB)
				drawOverlay
		
		/*
		val melon = getClass.getResourceAsStream("/scalascenegraph/examples/melon.png")
		image = ImageIO.read(melon)
		println(width + "," + height)
		if (width != 0 && height != 0) {
			if (image.getWidth != width && image.getHeight != height) {
				image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
				drawOverlay
			}
		}
		 */
	}
	
	private def drawOverlay {
		val g2d = image.getGraphics.asInstanceOf[Graphics2D]
		g2d.setColor(Color.blue)
		//g2d.setColor(Color.white)
		g2d.fillRect(0, 0, width, height)
		g2d.setColor(new Color(0xFFFFFFFF))
		g2d.drawString("Overlay...", 10, 10)
	}
	
	def getImage: BufferedImage = image
	
}
