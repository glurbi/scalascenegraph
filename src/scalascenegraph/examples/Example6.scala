package scalascenegraph.examples

import java.awt.{Color => JColor }
import scala.math._

import scalascenegraph.core._
import scalascenegraph.core.Predefs._
import scalascenegraph.dsl._

class Example6 extends Example with WorldBuilder {
	
	val angleHook = (r: Rotation, c: Context) => {
		val current = System.currentTimeMillis
		val elapsed = current - c.creationTime
		r.angle = (elapsed / 30.0f) % 360.0f
	}
	
	def example =
		world {
			group {
				fog(JColor.blue, 1.0f, 10.0f, Linear)
				group {
					translation(0.0f, -1.0f, -5.0f)
					rotation(-90.0f, 1.0f, 0.0f, 0.0f)
					checkerBoard(20, 20, JColor.white, JColor.black)
				}
				group {
					light(On)
					light(DiffuseLight, Position(0.0f, 0.0f, 0.0f), JColor.white)
					material(Front, DiffuseLight, JColor.orange)
					group {
						translation(-1.5f, 0.5f, -4.0f)
						rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
						torus(30, 1.0f, 0.5f)
					}
					group {
						translation(1.5f, 0.5f, -7.0f)
						rotation(0.0f, 0.0f, 1.0f, 0.0f, angleHook)
						torus(30, 1.0f, 0.5f)
					}
				}
			}
		}
	
}
