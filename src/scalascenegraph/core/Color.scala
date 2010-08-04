package scalascenegraph.core

object Color {
  
    val white = new Color(1.0f, 1.0f, 1.0f)
    val grey = new Color(0.5f, 0.5f, 0.5f)
    val blue = new Color(0.0f, 0.0f, 1.0f)
    val red = new Color(1.0f, 0.0f, 0.0f)
    
}

case class Color(val red: Float, val green: Float, val blue: Float, val alpha: Float = 1.0f)