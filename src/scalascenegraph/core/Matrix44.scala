package scalascenegraph.core

import scala.math._

import scalascenegraph.core.Predefs._

object Matrix44 {

    /**
     * the identity 4x4 matrix
     */
    val identity: Matrix44 = {
    	val m = new Array[Float](16)
    	m(0) = 1.0f
    	m(5) = 1.0f
    	m(10) = 1.0f
    	m(15) = 1.0f
    	new Matrix44(m)
    }
    
    /**
     * Generate a orthogonal projection matrix as defined at:
     * http://www.opengl.org/sdk/docs/man/xhtml/glOrtho.xml
     */
    def  ortho(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix44 = {
    	val m = new Array[Float](16)
        m(0) = 2 / (right - left)
        m(1) = 0.0f
        m(2) = 0.0f
        m(3) = 0.0f
        m(4) = 0.0f
        m(5) = 2 / (top - bottom)
        m(6) = 0.0f
        m(7) = 0.0f
        m(8) = 0.0f
        m(9) = 0.0f
        m(10) = -2 / (far - near)
        m(11) = 0.0f
        m(12) = - (right + left) / (right - left)
        m(13) = - (top + bottom) / (top - bottom)
        m(14) = - (far + near) / (far - near)
        m(15) = 1.0f
    	new Matrix44(m)
    }
    
    /**
     * Generate a perspective projection matrix as defined at:
     * http://www.opengl.org/sdk/docs/man/xhtml/glFrustum.xml
     */
    def frustum(left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float): Matrix44 = {
    	val m = new Array[Float](16)
        m(0) = 2 * near / (right - left)
        m(1) = 0.0f
        m(2) = 0.0f
        m(3) = 0.0f
        m(4) = 0.0f
        m(5) = 2 * near / (top - bottom)
        m(6) = 0.0f
        m(7) = 0.0f
        m(8) = (right + left) / (right - left)
        m(9) = (top + bottom) / (top - bottom)
        m(10) = - (far + near) / (far - near)
        m(11) = -1.0f
        m(12) = 0.0f
        m(13) = 0.0f
        m(14) = -2.0f * far * near / (far - near)
        m(15) = 0.0f
    	new Matrix44(m)
    }

    /**
     * Generate a translation matrix as defined at:
     * http://www.opengl.org/sdk/docs/man/xhtml/glTranslate.xml
     */
    def translation(x: Float, y: Float, z: Float): Matrix44 = {
    	val m = new Array[Float](16)
        m(0) = 1.0f
        m(1) = 0.0f
        m(2) = 0.0f
        m(3) = 0.0f
        m(4) = 0.0f
        m(5) = 1.0f
        m(6) = 0.0f
        m(7) = 0.0f
        m(8) = 0.0f
        m(9) = 0.0f
        m(10) = 1.0f
        m(11) = 0.0f
        m(12) = x
        m(13) = y
        m(14) = z
        m(15) = 1.0f
    	new Matrix44(m)
    }

    /**
     * Generate a rotation matrix as defined at:
     * http://www.opengl.org/sdk/docs/man/xhtml/glRotate.xml
     */
    def rotation(a: Float, x: Float, y: Float, z: Float): Matrix44 = {
    	val m = new Array[Float](16)
        val c = cos(toRadians(a))
        val s = sin(toRadians(a))
        m(0) = x * x * (1 - c) + c
        m(1) = y * x * (1 - c) + z * s
        m(2) = x * z * (1 - c) - y * s
        m(3) = 0.0f
        m(4) = y * x * (1 - c) - z * s
        m(5) = y * y * (1 - c) + c
        m(6) = y * z * (1 - c) + x * s
        m(7) = 0.0f
        m(8) = x * z * (1 - c) + y * s
        m(9) = y * z * (1 - c) - x * s
        m(10) = z * z * (1 - c) + c
        m(11) = 0.0f
        m(12) = 0.0f
        m(13) = 0.0f
        m(14) = 0.0f
        m(15) = 1.0f
    	new Matrix44(m)
    }
    
    /**
     * cf gluLookAt
     * http://www.unix.com/man-page/All/3/gluLookAt/
     */
    def lookAt(eyeX: Float, eyeY: Float, eyeZ: Float,
               centerX: Float, centerY: Float, centerZ: Float,
               upX: Float, upY: Float, upZ: Float): Matrix44 =
    {
    	val m = new Array[Float](16)
        m(0) = 0.0f
        m(1) = 0.0f
        m(2) = 0.0f
        m(3) = 0.0f
        m(4) = 0.0f
        m(5) = 0.0f
        m(6) = 0.0f
        m(7) = 0.0f
        m(8) = 0.0f
        m(9) = 0.0f
        m(10) = 0.0f
        m(11) = 0.0f
        m(12) = 0.0f
        m(13) = 0.0f
        m(14) = 0.0f
        m(15) = 0.0f
    	new Matrix44(m)
    }
    
}

/**
 * A 4x4 matrix
 */
class Matrix44(val m: Array[Float] = new Array[Float](16)) {
    
    def mult(that: Matrix44): Matrix44 = {
    	val matrix = new Matrix44
    	for (i <- 0 to 3) {
    		for (j <- 0 to 3) {
    			matrix.m(i+j*4) =
    				this.m(i+0) * that.m(j*4+0) +
    				this.m(i+4) * that.m(j*4+1) +
    				this.m(i+8) * that.m(j*4+2) +
    				this.m(i+12) * that.m(j*4+3)
    		}
    	}
    	matrix
    }

    def mult(v: Vector4): Vector4 = {
        val result = new Array[Float](4)
        for (i <- 0 to 3) {
            result(i) =
                m(i+0) * v.x +
                m(i+4) * v.y +
                m(i+8) * v.z +
                m(i+12) * v.w
        }
        new Vector4(result)
    }

    override def toString: String = {
    	"Matrix44 = [" +
           m(0) + " " + m(4) + " " + m(8) + " " + m(12) + " " +
           m(1) + " " + m(5) + " " + m(9) + " " + m(13) + " " +
    	   m(2) + " " + m(6) + " " + m(10) + " " + m(14) + " " +
    	   m(3) + " " + m(7) + " " + m(11) + " " + m(15) + "]"
    }
    
}
