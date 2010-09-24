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

class PolygonState(var face: Face, var mode: DrawingMode) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_POLYGON_BIT)
		context.gl.glPolygonMode(face, mode)
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

class LightPositionState(instance: LightInstance, position: Position) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		val p = position.asFloatArray
		context.gl.glLightfv(instance, GL_POSITION, Array(p(0), p(1), p(2), 1.0f), 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class LightColorState(instance: LightInstance, lightType: LightType, color: Color) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glLightfv(instance, lightType, color.asFloatArray, 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class AmbientLightState(intensity: Intensity) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glLightModelfv(GL_LIGHT_MODEL_AMBIENT, intensity.asFloatArray, 0)
	}
	override def postRender(context: Context) {
		context.gl.glPopAttrib
	}
}

class MaterialState(face: Face, lightType: LightType, color: Color)  extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LIGHTING_BIT)
		context.gl.glMaterialfv(face, lightType, color.asFloatArray, 0)
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

class LineWidthState(width: Float) extends State {
	override def preRender(context: Context) {
		context.gl.glPushAttrib(GL_LINE_BIT)
		context.gl.glLineWidth(width)
	}
	override def postRender(context: Context) {
		context.renderer.gl.glPopAttrib
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
    	context.gl.glFogfv(GL_FOG_COLOR, color.asFloatArray, 0)
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
		saved = context.renderer.currentProgram
		context.renderer.useProgram(program.programId)
	}
	override def postRender(context: Context) {
		context.renderer.useProgram(saved)
	}
}

class UniformState(var uniform: Uniform, var value: Any) extends State {
	var saved: Any = _
	override def preRender(context: Context) {
		saved = uniform.value
		value match {
			case a: Float => context.renderer.setUniformValue(uniform, a)
			case Array(a: Float, b: Float, c: Float, d: Float) => context.renderer.setUniformValue(uniform, a, b, c, d)
			case default => 
		}
	}
	override def postRender(context: Context) {
		saved match {
			case a: Float => context.renderer.setUniformValue(uniform, a)
			case Array(a: Float, b: Float, c: Float, d: Float) => context.renderer.setUniformValue(uniform, a, b, c, d)
			case default => 
		}
	}
}
