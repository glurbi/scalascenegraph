package scalascenegraph.core

class Context {
	val creationTime = System.currentTimeMillis
	var elapsed = 0L
	var renderer: Renderer = _
	var upKeyPressed: Boolean = _
	var downKeyPressed: Boolean = _
	var rightKeyPressed: Boolean = _
	var leftKeyPressed: Boolean = _
	var controlKeyPressed: Boolean = _
	var shiftKeyPressed: Boolean = _
	var spaceKeyPressed: Boolean = _
	var escapeKeyPressed: Boolean = _
}
