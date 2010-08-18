package scalascenegraph.core

import scalascenegraph.core.Predefs._

trait State {
	def preRender(context: Context) {}
	def postRender(context: Context) {}
}

class DynamicState[T <: State](val hook: StateHook[T], val state: T) extends State {
	override def preRender(context: Context) {
		hook(state, context)
		state.preRender(context)
	}
	override def postRender(context: Context) {
		state.postRender(context)
	}
}

trait Transformation extends State {}

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

class CullFaceState(var cullFace: Boolean) extends State {
	override def preRender(context: Context) {
		context.renderer.pushCullFace
		cullFace match {
			case true => context.renderer.enableCullFace
			case false => context.renderer.disableCullFace
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

class LightState(var state: OnOffState) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.setLightState(state)
	}
	override def postRender(context: Context) {
		context.renderer.popLightState
	}
}

class Light(lightType: LightType, position: Position, color: Color) extends State {
	override def preRender(context: Context) {
		context.renderer.pushLightState
		context.renderer.enableLight(lightType, position, color)
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
