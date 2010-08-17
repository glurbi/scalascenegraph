package scalascenegraph.core

class Context {
	val creationTime = System.currentTimeMillis
	var elapsed = 0L
	var renderer: Renderer = _
}
