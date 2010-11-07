package scalascenegraph.core

import javax.media.opengl.GL._
import javax.media.opengl.GL2._
import javax.media.opengl.GL2GL3._
import javax.media.opengl.GL2ES1._
import javax.media.opengl.GL2ES2._
import javax.media.opengl.fixedfunc._
import javax.media.opengl.fixedfunc.GLLightingFunc._
import javax.media.opengl.fixedfunc.GLPointerFunc._
import javax.media.opengl.fixedfunc.GLMatrixFunc._

import scalascenegraph.core.Predefs._

/**
 * A State instance controls the state of the renderer for all child nodes of a given group.
 * <p>
 * A state can be overriden in child nodes.
 */
trait State {
	
	/**
	 * Called before a node is actually rendered (Node.doRender() method)
	 * Implementations should save the current renderer state and then modify it.
	 */
	def preRender(context: Context) {}
	
	/**
	 * Called after a node is actually rendered (Node.doRender() method)
	 * Implementations should restore the previous state of the renderer.
	 */
	def postRender(context: Context) {}
	
}

/**
 * Wraps a state so that the specified hook can be called before the wrapped
 * states preRender() method is called, for each frame. The hook code allows
 * the user to change some node attributes before rendering, thus making the
 * wrapped state dynamic. 
 */
class DynamicState[T <: State](val hook: StateHook[T], val state: T) extends State {
	override def preRender(context: Context) {
		hook(state, context)
		state.preRender(context)
	}
	override def postRender(context: Context) {
		state.postRender(context)
	}
}

/**
 * A marker trait for transformations.
 */
trait Transformation extends State {}

class DepthTestState(var depthTest: OnOffState) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_DEPTH_BUFFER_BIT)
		depthTest match {
			case On => context.gl.glEnable(GL_DEPTH_TEST)
			case Off => context.gl.glDisable(GL_DEPTH_TEST)
		}
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class ColorState(var color: Color) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_CURRENT_BIT)
		context.gl.glColor3f(color.r, color.g , color.b)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class PointSizeState(var pointSize: Float) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_POINT_BIT)
		context.gl.glPointSize(pointSize)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class LineWidthState(var lineWidth: Float) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LINE_BIT)
		context.gl.glLineWidth(lineWidth)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class LineStippleState(var factor: Int, var pattern: Short) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LINE_BIT)
		context.gl.glEnable(GL_LINE_STIPPLE)
		context.gl.glLineStipple(factor, pattern)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class SmoothState(var smooth: SmoothType) extends State {
	override def preRender(context: Context) {
		smooth match {
			case GL_POINT_SMOOTH => context.gl.glPushAttrib(GL_POINT_BIT)
			case GL_LINE_SMOOTH => context.gl.glPushAttrib(GL_LINE_BIT)
			case GL_POLYGON_SMOOTH => context.gl.glPushAttrib(GL_POLYGON_BIT)
		}
		context.gl.glEnable(smooth)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class PolygonModeState(var face: Face, var mode: DrawingMode) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_POLYGON_BIT)
		context.gl.glPolygonMode(face, mode)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class PolygonOffsetState(var factor: Float, var units: Float) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_POLYGON_BIT)
		context.gl.glEnable(GL_POLYGON_OFFSET_FILL)
		context.gl.glPolygonOffset(factor, units)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class BlendingState(var blending: OnOffState) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_CURRENT_BIT)
		blending match {
			case On => {
				context.gl.glEnable(GL_BLEND)
				context.gl.glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
			}
			case Off => context.gl.glDisable(GL_BLEND)
		}
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class CullFaceState(var cullFace: OnOffState) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_ENABLE_BIT)
		cullFace match {
			case On => context.gl.glEnable(GL_CULL_FACE)
			case Off => context.gl.glDisable(GL_CULL_FACE)
		}
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class FrontFaceState(var frontFace: FrontFace) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_POLYGON_BIT)
		context.gl.glFrontFace(frontFace)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class GlobalLightState(var state: OnOffState) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
    	state match {
    		case On => context.gl.glEnable(GL_LIGHTING)
    		case Off => context.gl.glDisable(GL_LIGHTING)
    	}
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class ColorMaterialState(var face: Face, var lightType: LightType) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glEnable(GL_COLOR_MATERIAL)
    	context.gl.glColorMaterial(face, lightType)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class LightState(instance: LightInstance, var state: OnOffState) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
    	state match {
    		case On => context.gl.glEnable(instance)
    		case Off => context.gl.glDisable(instance)
    	}
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

// TODO: remove this state coz replaced by LightParameterState
class LightPositionState(instance: LightInstance, position: Position3D) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		val p = position.xyz
		context.gl.glLightfv(instance, GL_POSITION, Array(p(0), p(1), p(2), 1.0f), 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class LightParameterState(instance: LightInstance, parameter: LightParameter, value: Array[Float]) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glLightfv(instance, parameter, value, 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class LightColorState(instance: LightInstance, lightType: LightType, color: Color) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glLightfv(instance, lightType, color.rgba, 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class AmbientLightState(intensity: Intensity) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, intensity.rgba, 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class MaterialState(face: Face, lightType: LightType, color: Color)  extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glMaterialfv(face, lightType, color.rgba, 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class MaterialShininessState(face: Face, shininess: Int) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glMateriali(face, GL_SHININESS, shininess)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class ShadeModelState(shadeModel: ShadeModel) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glShadeModel(shadeModel)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class Translation(var x: Float, var y: Float, var z: Float) extends Transformation {
	override def preRender(context: Context) {
		context.gl.glPushMatrix
		context.gl.glTranslatef(x, y, z)
	}
	override def postRender(context: Context) {
		context.gl.glPopMatrix
	}
}

class Rotation(var angle: Float, var x: Float, var y: Float, var z: Float) extends Transformation {
	def this() = this(0.0f, 0.0f, 0.0f, 0.0f)
	override def preRender(context: Context) {
		context.gl.glPushMatrix
		context.gl.glRotatef(angle, x, y, z)
	}
	override def postRender(context: Context) {
		context.gl.glPopMatrix
	}
}

class FogState(var color: Color, var mode: FogMode) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_FOG_BIT)
    	context.gl.glEnable(GL_FOG)
    	context.gl.glFogfv(GL_FOG_COLOR, color.rgba, 0)
    	mode match {
    		case Linear(start, end) => {
    			context.gl.glFogf(GL_FOG_START, start)
    			context.gl.glFogf(GL_FOG_END, end)
    			context.gl.glFogf(GL_FOG_MODE, GL_LINEAR)
    		}
    		case Exp(density) => {
    			context.gl.glFogf(GL_FOG_DENSITY, density)
    			context.gl.glFogf(GL_FOG_MODE, GL_EXP)
    		}
    		case Exp2(density) => {
    			context.gl.glFogf(GL_FOG_DENSITY, density)
    			context.gl.glFogf(GL_FOG_MODE, GL_EXP2)
    		}
    	}
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class ProgramState(var program: Program) extends State {
	var saved: ProgramId = _
	override def preRender(context: Context) {
		val id = Array[Int](1)
		context.gl.glGetIntegerv(GL_CURRENT_PROGRAM, id, 0)
		saved = id(0)
		context.gl.glUseProgram(program.id)
	}
	override def postRender(context: Context) {
		context.gl.glUseProgram(saved)
	}
}

class UniformState(var uniform: Uniform, var value: Any) extends State {
	var saved: Any = _
	override def preRender(context: Context) {
		saved = uniform.value
		value match {
			case a: Float => setUniformValue(context, uniform, a)
			case Array(a: Float, b: Float, c: Float, d: Float) => setUniformValue(context, uniform, a, b, c, d)
			case default => 
		}
	}
	override def postRender(context: Context) {
		saved match {
			case a: Float => setUniformValue(context, uniform, a)
			case Array(a: Float, b: Float, c: Float, d: Float) => setUniformValue(context, uniform, a, b, c, d)
			case default => 
		}
	}
	private def setUniformValue(context: Context, uniform: Uniform, a: Float, b: Float, c: Float, d: Float) {
		context.gl.glUniform4f(uniform.id, a, b, c, d)
		uniform.value = Array(a, b, c, d)
	}
	private def setUniformValue(context: Context, uniform: Uniform, a: Float) {
		context.gl.glUniform1f(uniform.id, a)
		uniform.value = a
	}
}

class PointSpriteState(var state: OnOffState) extends State {
	var enabledSaved: Boolean = _
	var texEnvSaved: Int = _
	override def preRender(context: Context) {
		enabledSaved = context.gl.glIsEnabled(GL_POINT_SPRITE)
		val tmp = new Array[Int](1)
		context.gl.glGetTexEnviv(GL_POINT_SPRITE, GL_COORD_REPLACE, tmp, 0)
		texEnvSaved = tmp(0)
		state match {
			case On => context.gl.glEnable(GL_POINT_SPRITE)
			case Off => context.gl.glDisable(GL_POINT_SPRITE)
		}
		context.gl.glTexEnvi(GL_POINT_SPRITE, GL_COORD_REPLACE, GL_TRUE)
		context.gl.glPointParameteri(GL_POINT_SPRITE_COORD_ORIGIN, GL_LOWER_LEFT)
	}
	override def postRender(context: Context) {
		context.gl.glTexEnvi(GL_POINT_SPRITE, GL_COORD_REPLACE, texEnvSaved)
		enabledSaved match {
			case true => context.gl.glEnable(GL_POINT_SPRITE)
			case false => context.gl.glDisable(GL_POINT_SPRITE)
		}
	}
}

class BindTextureState(var textureType: TextureType, var texture: Texture) extends State {
	var saved: Int = _
	override def preRender(context: Context) {
		val tmp = new Array[Int](1)
		context.gl.glGetIntegerv(textureType, tmp, 0)
		saved = tmp(0)
		context.gl.glBindTexture(textureType, texture.id)
	}
	override def postRender(context: Context) {
		context.gl.glBindTexture(textureType, saved)
	}
}
