package scalascenegraph.examples

import java.awt.{Color => JColor }

import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example01 extends Example with WorldBuilder {
	
	def example =
		world {
    		translation(0.0f, 0.0f, -5.0f)
    			
    		group {
    			color(JColor.magenta)
    			triangle(
    				Vertice3D(0.0f, 4.0f, 0.0f),
    				Vertice3D(-4.0f, 3.0f, 0.0f),
    				Vertice3D(4.0f, 3.0f, 0.0f))
    		}
    		
			triangle(
				Vertice3D(-1.0f, 0.0f, 0.0f),
				Vertice3D(1.0f, 0.0f, 0.0f),
				Vertice3D(0.0f, 2.0f, 0.0f),
				Color(1.0f, 0.0f, 0.0f),
				Color(0.0f, 1.0f, 0.0f),
				Color(0.0f, 0.0f, 1.0f))
   					
			triangle(
				Vertice3D(-4.0f, 2.0f, 0.0f),
				Vertice3D(-3.0f, 0.0f, 0.0f),
				Vertice3D(-2.0f, 2.0f, 0.0f))
					
			quad(
				Vertice3D(2.0f, 0.0f, 0.0f),
				Vertice3D(4.0f, 0.0f, 0.0f),
				Vertice3D(4.0f, 2.0f, 0.0f),
				Vertice3D(2.0f, 2.0f, 0.0f),
				Color(1.0f, 1.0f, 0.0f))
				
			group {
				color(JColor.green)
				quad(
					Vertice3D(4.0f, -2.0f, 0.0f),
					Vertice3D(-4.0f, -2.0f, 0.0f),
					Vertice3D(-4.0f, -4.0f, 0.0f),
					Vertice3D(4.0f, -4.0f, 0.0f))
			}
	    }
	
}
