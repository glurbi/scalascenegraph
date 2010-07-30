package scalascenegraph.core

object Predefs {

	abstract class Face
	case object Front extends Face
	case object Back extends Face
	case object FrontAndBack extends Face

	abstract class DrawingMode
	case object Point extends DrawingMode
	case object Line extends DrawingMode
	case object Fill extends DrawingMode

	type Hook = (Node, Context) => Unit
	
}