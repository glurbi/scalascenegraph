package scalascenegraph.core

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
		context.renderer.pushDepthTestState
		depthTest match {
			case On => context.renderer.enableDepthTest
			case Off => context.renderer.disableDepthTest
		}
	}
	override def postRender(context: Context) {
		context.renderer.popDepthTestState
	}
}

class ColorState(var color: Color) extends State {
	override def preRender(context: Context) {
		context.renderer.pushColorState
		context.renderer.setColor(color)
	}
	override def postRender(context: Context) {
		context.renderer.popColorState
	}
}

class PolygonState(var face: Face, var mode: DrawingMode) extends State {
	override def preRender(context: Context) {
		context.renderer.pushPolygonMode
		context.renderer.setPolygonMode(face, mode)
	}
	override def postRender(context: Context) {
		context.renderer.popPolygonMode
	}
}

class BlendingState(var blending: OnOffState) extends State {
	override def preRender(context: Context) {
		context.renderer.pushColorState
		blending match {
			case On => context.renderer.enableBlending
			case Off => context.renderer.disableBlending
		}
	}
	override def postRender(context: Context) {
		context.renderer.popColorState
	}
}

class CullFaceState(var cullFace: OnOffState) extends State {
	override def preRender(context: Context) {
		context.renderer.pushCullFace
		cullFace match {
			case On => context.renderer.enableCullFace
			case Off => context.renderer.disableCullFace
		}
	}
	override def postRender(context: Context) {
		context.renderer.popCullFace
	}
}

class FrontFaceState(var frontFace: FrontFace) extends State {
	override def preRender(context: Context) {
		context.renderer.pushFrontFace
		context.renderer.setFrontFace(frontFace)
	}
	override def postRender(context: Context) {
		context.renderer.popFrontFace
	}
}

class GlobalLightState(var state: OnOffState) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.setLightState(state)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class LightState(instance: LightInstance, var state: OnOffState) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.setLightState(instance, state)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class LightPositionState(instance: LightInstance, position: Position) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.lightPosition(instance, position)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class LightColorState(instance: LightInstance, lightType: LightType, color: Color) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.lightColor(instance, lightType, color)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class AmbientLightState(intensity: Intensity) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.setAmbientLight(intensity)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class MaterialState(face: Face, lightType: LightType, color: Color)  extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.setMaterial(face, lightType, color)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class MaterialShininessState(face: Face, shininess: Int) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.setShininess(face, shininess)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class LineWidthState(width: Float) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLineState
		context.renderer.setLineWidth(width)
	}
	override def postRender(context: Context) {
		context.renderer.popLineState
	}
}

class ShadeModelState(shadeModel: ShadeModel) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.setShadeModel(shadeModel)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class Translation(var x: Float, var y: Float, var z: Float) extends Transformation {
	override def preRender(context: Context) {
		context.renderer.pushMatrix
		context.renderer.translate(x, y, z)
	}
	override def postRender(context: Context) {
		context.renderer.popMatrix
	}
}

class Rotation(var angle: Float, var x: Float, var y: Float, var z: Float) extends Transformation {
	def this() = this(0.0f, 0.0f, 0.0f, 0.0f)
	override def preRender(context: Context) {
		context.renderer.pushMatrix
		context.renderer.rotate(angle, x, y, z)
	}
	override def postRender(context: Context) {
		context.renderer.popMatrix
	}
}

class FogState(var color: Color, var mode: FogMode) extends State {
	override def preRender(context: Context) {
		context.renderer.pushFogState
		context.renderer.setFogState(color, mode)
	}
	override def postRender(context: Context) {
		context.renderer.popFogState
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

class UniformState(var uniform: Uniform, var value: Array[Float]) extends State {
	var saved: Any = _
	override def preRender(context: Context) {
		saved = uniform.value
		value match {
			case Array(a: Float, b: Float, c: Float, d: Float) => context.renderer.setUniformValue(uniform, a, b, c, d)
			case default => 
		}
	}
	override def postRender(context: Context) {
		saved match {
			case Array(a: Float, b: Float, c: Float, d: Float) => context.renderer.setUniformValue(uniform, a, b, c, d)
			case default => 
		}
	}
}
