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

trait StateBuilder extends GraphBuilder {

	def color(c: Color) {
		stack.top.attach(new ColorState(c))
	}

	def smooth(smooth: SmoothType) {
		stack.top.attach(new SmoothState(smooth))
	}
	
	def pointSize(size: Float) {
		stack.top.attach(new PointSizeState(size))
	}
	
	def lineWidth(width: Float) {
		stack.top.attach(new LineWidthState(width))
	}

	def lineStipple(factor: Int, pattern: Short) {
		stack.top.attach(new LineStippleState(factor, pattern))
	}
	
	def translation(x: Float, y: Float, z: Float) {
		stack.top.attach(new Translation(x, y, z))
	}

	def translation(x: Float, y: Float, z: Float, hook: StateHook[Translation]) {
		stack.top.attach(new DynamicState(hook, new Translation(x, y, z)))
	}
	
	def translation(hook: StateHook[Translation]) {
		stack.top.attach(new DynamicState(hook, new Translation(0.0f, 0.0f, 0.0f)))
	}
	
	def rotation(angle: Float, x: Float, y: Float, z: Float) {
		stack.top.attach(new Rotation(angle, x, y, z))
	}

	def rotation(angle: Float, x: Float, y: Float, z: Float, hook: StateHook[Rotation]) {
		stack.top.attach(new DynamicState(hook, new Rotation(angle, x, y, z)))
	}

	def rotation(hook: StateHook[Rotation]) {
		stack.top.attach(new DynamicState(hook, new Rotation))
	}

	def multMatrix(m: Array[Float]) {
		stack.top.attach(new MultMatrix(m))
	}
	
	def polygonMode(face: Face, mode: DrawingMode) {
		stack.top.attach(new PolygonModeState(face, mode))
	}

	def polygonOffset(factor: Float, units: Float) {
		stack.top.attach(new PolygonOffsetState(factor, units))
	}
	
	def frontFace(frontFace: FrontFace) {
		stack.top.attach(new FrontFaceState(frontFace))
	}
	
	def cullFace(cullFace: OnOffState) {
		stack.top.attach(new CullFaceState(cullFace))
	}

	def depthTest(depthTest: OnOffState) {
		stack.top.attach(new DepthTestState(depthTest))
	}
	
	def light(mode: OnOffState) {
		stack.top.attach(new GlobalLightState(mode))
	}

	def light(instance: LightInstance, mode: OnOffState) {
		stack.top.attach(new LightState(instance, mode))
	}

	// TODO: remove (replace with light parameter)
	def light(instance: LightInstance, lightType: LightType, color: Color) {
		stack.top.attach(new LightColorState(instance, lightType, color))
	}

	def light(instance: LightInstance, position: Position3D) {
		stack.top.attach(new LightPositionState(instance, position))
	}
	
	def light(instance: LightInstance, parameter: LightParameter, value: Array[Float]) {
		stack.top.attach(new LightParameterState(instance, parameter, value))
	}

	def shininess(face: Face, shininess: Int) {
		stack.top.attach(new MaterialShininessState(face, shininess))
	}
	
	def ambient(intensity: Intensity) {
		stack.top.attach(new AmbientLightState(intensity))
	}

	def colorMaterial(face: Face, lightType: LightType) {
		stack.top.attach(new ColorMaterialState(face, lightType))
	}
	
	def material(face: Face, lightType: LightType, color: Color) {
		stack.top.attach(new MaterialState(face, lightType, color))
	}
	
	def fog(color: Color, mode: FogMode) {
		stack.top.attach(new FogState(color, mode))
	}
	
	def shadeModel(shadeModel: ShadeModel) {
		stack.top.attach(new ShadeModelState(shadeModel))
	}

	def blending(mode: OnOffState) {
		stack.top.attach(new BlendingState(mode))
	}

	def useProgram(program: Program) {
		stack.top.attach(new ProgramState(program))
	}
	
	def program(program: Program) {
		stack.top.attach(new ProgramState(program))
	}
	
	def setUniform(uniform: Uniform, color: Color) {
		stack.top.attach(new UniformState(uniform, color.rgba))
	}
	
	def setUniform(uniform: Uniform, value: Float) {
		stack.top.attach(new UniformState(uniform, value))
	}

	def setUniform(uniform: Uniform, value: Float, hook: StateHook[UniformState]) {
		stack.top.attach(new DynamicState(hook, new UniformState(uniform, value)))
	}

	def setUniform(uniform: Uniform, value: Matrix44, hook: StateHook[UniformState]) {
		stack.top.attach(new DynamicState(hook, new UniformState(uniform, value)))
	}

	def pointSprite(mode: OnOffState) {
		stack.top.attach(new PointSpriteState(mode))
	}

	def bindTexture(textureType: TextureType, texture: Texture) {
		stack.top.attach(new BindTextureState(textureType, texture))
	}

}
