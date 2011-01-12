package scalascenegraph.tests

import java.awt.{Font => JFont}

import scalascenegraph.core._
import scalascenegraph.builders._
import scalascenegraph.core.Predefs._
import scalascenegraph.core.Utils._

object FontBuilderTest {

    def main(args: Array[String]) {
        val font = FontBuilder.create(new JFont("Default", JFont.PLAIN, 20))
        font.characters.get('M') match {
            case Some(c) => dumpBitmap(c.width, c.height, c.bitmap)
            case None => println("Damn it!")
        }
    }

}