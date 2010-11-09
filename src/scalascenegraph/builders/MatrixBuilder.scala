package scalascenegraph.builders

import java.io._
import java.nio._
import java.awt.image._
import java.awt.{Color => JColor}
import java.awt.{Font => JFont}
import scala.collection.mutable.Stack
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
import scalascenegraph.builders._
import scalascenegraph.core.Predefs._

trait MatrixBuilder {

	// cf http://www.opengl.org/resources/features/StencilTalk/tsld021.htm
	def shadowMatrix(plane: Plane3D, light: Position3D): Array[Float] = {

		val m = new Array[Float](16)
		val dot = plane.a * light.x + plane.b * light.y + plane.c * light.z + plane.d

		m(0)  = dot - light.x * plane.a
		m(4)  = -light.x * plane.b
		m(8)  = -light.x * plane.c 
		m(12) = -light.x * plane.d

		m(1)  = -light.y * plane.a
		m(5)  = dot - light.y * plane.b
		m(9)  = -light.y * plane.c
		m(13) = -light.y * plane.d

		m(2)  = -light.z * plane.a 
		m(6)  = -light.z * plane.b
		m(10) = dot - light.z * plane.c
		m(14) = -light.z * plane.d

		m(3)  = -light.w * plane.a
		m(7)  = -light.w * plane.b
		m(11) = -light.w * plane.c
		m(15) = dot - light.w * plane.d

		m
	}
	
}
