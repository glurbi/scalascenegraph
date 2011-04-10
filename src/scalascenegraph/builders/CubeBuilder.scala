package scalascenegraph.builders

import java.nio._
import com.jogamp.common.nio._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._


import scalascenegraph.core._
import scalascenegraph.core.Predefs._

class CubeBuilder {

	val createPositions = 
			Array(-0.5f, -0.5f, -0.5f,
				  -0.5f, 0.5f, -0.5f,
				  0.5f, 0.5f, -0.5f,
                  -0.5f, -0.5f, -0.5f,
				  -0.5f, 0.5f, -0.5f,
				  0.5f, -0.5f, -0.5f,

				  -0.5f, -0.5f, -0.5f,
				  -0.5f, -0.5f, 0.5f,
				  -0.5f, 0.5f, 0.5f,
				  -0.5f, -0.5f, -0.5f,
				  -0.5f, -0.5f, 0.5f,
				  -0.5f, 0.5f, -0.5f,

				  -0.5f, -0.5f, -0.5f,
				  0.5f, -0.5f, -0.5f,
				  0.5f, -0.5f, 0.5f,
				  -0.5f, -0.5f, -0.5f,
				  0.5f, -0.5f, -0.5f,
				  -0.5f, -0.5f, 0.5f,

				  -0.5f, -0.5f, 0.5f,
				  0.5f, -0.5f, 0.5f,
				  0.5f, 0.5f, 0.5f,
				  -0.5f, -0.5f, 0.5f,
				  0.5f, -0.5f, 0.5f,
				  -0.5f, 0.5f, 0.5f,

				  0.5f, -0.5f, -0.5f,
				  0.5f, 0.5f, -0.5f,
				  0.5f, 0.5f, 0.5f,
				  0.5f, -0.5f, -0.5f,
				  0.5f, 0.5f, -0.5f,
				  0.5f, -0.5f, 0.5f,

				  -0.5f, 0.5f, -0.5f,
				  -0.5f, 0.5f, 0.5f,
				  0.5f, 0.5f, 0.5f,
				  -0.5f, 0.5f, -0.5f,
				  -0.5f, 0.5f, 0.5f,
				  0.5f, 0.5f, -0.5f)
				  
	val createNormals =
			Array(0.0f, 0.0f, -1.0f,
				  0.0f, 0.0f, -1.0f,
				  0.0f, 0.0f, -1.0f,
                  0.0f, 0.0f, -1.0f,
				  0.0f, 0.0f, -1.0f,
				  0.0f, 0.0f, -1.0f,

				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,
				  -1.0f, 0.0f, 0.0f,

				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,
				  0.0f, -1.0f, 0.0f,

				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,
				  0.0f, 0.0f, 1.0f,

				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,
				  1.0f, 0.0f, 0.0f,

				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f,
				  0.0f, 1.0f, 0.0f)
	                     
	val createTextureCoordinates =
			Array(0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
                  0.0f, 0.0f, 
				  1.0f, 0.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  0.0f, 1.0f,

				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  1.0f, 1.0f,
				  0.0f, 0.0f,
				  1.0f, 0.0f,
				  0.0f, 1.0f)
}

