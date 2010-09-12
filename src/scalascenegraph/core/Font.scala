package scalascenegraph.core

import java.nio._
import scala.collection.mutable._

class Character(val char: Char, val width: Int, val height: Int, val bitmap: ByteBuffer)

class Font(val characters: Map[Char, Character]) extends Node
