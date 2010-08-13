package scalascenegraph.core

import scala.math._

import scalascenegraph.core.Predefs._

object Utils {

	def vector(v1: Vertice, v2: Vertice): Vector =
		Vector(v2.x - v1.x, v2.y - v1.y, v2.z -v1.z)
	
	def crossProduct(u: Vector, v: Vector): Vector = {
		Vector(u.y*v.z - u.z*v.y, u.z*v.x - u.x*v.z, u.x*v.y - u.y*v.x)
	}
	
	def normalize(v: Vector): Vector = {
		implicit def doubleToFloat(d: Double): Float = d.asInstanceOf[Float]
		val norm = sqrt(v.x*v.x + v.y*v.y + v.z*v.z)
		Vector(v.x / norm, v.y / norm, v.z / norm)
	}
	
	
}