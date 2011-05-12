package scalascenegraph.core

import java.io._
import java.util._
import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._
import scala.math._

import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._

object Camera {
    
    def get(config: InputStream): Camera = {
        try {
            val props = new Properties
            props.load(config)
            val left = props.getProperty("camera.clipping.volume.left").toDouble
            val right = props.getProperty("camera.clipping.volume.right").toDouble
            val bottom = props.getProperty("camera.clipping.volume.bottom").toDouble
            val top = props.getProperty("camera.clipping.volume.top").toDouble
            val near = props.getProperty("camera.clipping.volume.near").toDouble
            val far = props.getProperty("camera.clipping.volume.far").toDouble
            val clippingVolume = new ClippingVolume(left, right, bottom, top, near, far)
            props.get("camera.projection.type") match {
                case "perspective" => new PerspectiveCamera(clippingVolume)
                case "parallel" => new ParallelCamera(clippingVolume)
                case _ => throw new RuntimeException("Missing or invalid property camera.projection.type")
            }
        } finally {
            config.close
        }
    }
    
    def get(projectionType: ProjectionType, clippingVolume: ClippingVolume): Camera = {
        projectionType match {
            case Perspective => new PerspectiveCamera(clippingVolume)
            case Parallel => new ParallelCamera(clippingVolume)
        }
    }
}

// more or less direct scala translation of http://www.codecolony.de/opengl.htm#camera2
abstract class Camera(val clippingVolume: ClippingVolume) extends Node {

     val projectionType: ProjectionType

    var positionV = new Position3D(0.0f, 0.0f, 0.0f)
    var directionV = new Vector3D(0.0f, 0.0f, -1.0f)
    var rightV = new Vector3D(1.0f, 0.0f, 0.0f)
    var upV = new Vector3D(0.0f, 1.0f, 0.0f)

    def reset {
        positionV = new Position3D(0.0f, 0.0f, 0.0f)
        directionV = new Vector3D(0.0f, 0.0f, -1.0f)
        rightV = new Vector3D(1.0f, 0.0f, 0.0f)
        upV = new Vector3D(0.0f, 1.0f, 0.0f)
    }

    def rotateX(deg: Float) {
        directionV = normalize(directionV * cos(toRadians(deg)) + upV * sin(toRadians(deg)))
        upV = crossProduct(directionV, rightV) * -1
    }

    def rotateY(deg: Float) {
        directionV = normalize(directionV * cos(toRadians(deg)) - rightV * sin(toRadians(deg)))
        rightV  = crossProduct(directionV, upV)
    }

    def rotateZ(deg: Float) {
        rightV = normalize(rightV * cos(toRadians(deg)) + upV * sin(toRadians(deg)))
        upV = crossProduct(directionV, rightV) * -1
    }

    def moveRight(dist: Float) {
        positionV = positionV + (rightV * dist)
    }

    def moveLeft(dist: Float) {
        positionV = positionV - (rightV * dist)
    }

    def moveUp(dist: Float) {
        positionV = positionV + (upV * dist)
    }

    def moveDown(dist: Float) {
        positionV = positionV - (upV * dist)
    }

    def moveForward(dist: Float) {
        positionV = positionV + (directionV * dist)
    }

    def moveBackward(dist: Float) {
        positionV = positionV - (directionV * dist)
    }

    protected def positionAndOrient(context: Context) {
        val centerV = positionV + directionV
        context.glu.gluLookAt(
            positionV.x, positionV.y, positionV.z,
            centerV.x, centerV.y, centerV.z,
            upV.x, upV.y, upV.z)
    }

    override def toString: String =
        "position=" + positionV + " direction=" + directionV + " right=" + rightV + " up=" + upV
}

class ClippingVolume(
    var left: Double,
    var right: Double,
    var bottom: Double,
    var top: Double,
    var near: Double,
    var far: Double)

class PerspectiveCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
    val projectionType = Perspective
    
    def render(context: Context) {
        import clippingVolume._
        import context.gl
        gl.glMatrixMode(GL_PROJECTION)
        gl.glLoadIdentity
        val f = context.width.asInstanceOf[Float] / context.height // preserves aspect ratio
        gl.glFrustum(left * f, right * f, bottom, top, near, far)
        gl.glMatrixMode(GL_MODELVIEW)
        gl.glLoadIdentity
        positionAndOrient(context)
        context.matrixStack.reset
        context.matrixStack.pushProjection(Matrix44.frustum(left * f, right * f, bottom, top, near, far))
        //println(context.matrixStack.getModelViewProjectionMatrix)
        println(context.matrixStack.getModelViewProjectionMatrix.mult(new Vector4(Array(0.0f, 0.0f, -50.0f, 1.0f))))
        //val m = new Array[Float](16)
        //context.gl.glGetFloatv(GL_PROJECTION_MATRIX, m, 0)
        //println(Arrays.toString(m))
    }
}

class ParallelCamera(clippingVolume: ClippingVolume)
extends Camera(clippingVolume)
{
    val projectionType = Parallel
    
    def render(context: Context) {
        import clippingVolume._
        import context.gl
        gl.glMatrixMode(GL_PROJECTION)
        gl.glLoadIdentity
        val f = context.width.asInstanceOf[Float] / context.height // preserves aspect ratio
        gl.glOrtho(left * f , right * f, bottom, top, near, far)
        gl.glMatrixMode(GL_MODELVIEW)
        gl.glLoadIdentity
        context.matrixStack.reset
        positionAndOrient(context)
    }
}
