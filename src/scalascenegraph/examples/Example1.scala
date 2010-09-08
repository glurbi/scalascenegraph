package scalascenegraph.examples

import java.awt.{Color => JColor }

import scalascenegraph.core.Predefs._
import scalascenegraph.builders._

class Example1 extends Example with WorldBuilder {
	
	def example =
		world {
    		translation(0.0f, 0.0f, -5.0f)
    			
    		group {
    			color(JColor.magenta)
    			triangle(
    				Vertice(0.0f, 4.0f, 0.0f),
    				Vertice(-4.0f, 3.0f, 0.0f),
    				Vertice(4.0f, 3.0f, 0.0f))
    		}
    		
			triangle(
				Vertice(-1.0f, 0.0f, 0.0f),
				Vertice(1.0f, 0.0f, 0.0f),
				Vertice(0.0f, 2.0f, 0.0f),
				Color(1.0f, 0.0f, 0.0f),
				Color(0.0f, 1.0f, 0.0f),
				Color(0.0f, 0.0f, 1.0f))
   					
			triangle(
				Vertice(-4.0f, 2.0f, 0.0f),
				Vertice(-3.0f, 0.0f, 0.0f),
				Vertice(-2.0f, 2.0f, 0.0f))
					
			quad(
				Vertice(2.0f, 0.0f, 0.0f),
				Vertice(4.0f, 0.0f, 0.0f),
				Vertice(4.0f, 2.0f, 0.0f),
				Vertice(2.0f, 2.0f, 0.0f),
				Color(1.0f, 1.0f, 0.0f))
				
			group {
				color(JColor.green)
				quad(
					Vertice(4.0f, -2.0f, 0.0f),
					Vertice(-4.0f, -2.0f, 0.0f),
					Vertice(-4.0f, -4.0f, 0.0f),
					Vertice(4.0f, -4.0f, 0.0f))
			}
	    }
	
}
