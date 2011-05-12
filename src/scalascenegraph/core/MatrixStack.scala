package scalascenegraph.core

import scala.collection.mutable.Stack

class MatrixStack {

    private val mvpMatrices = new Stack[Matrix44].push(Matrix44.identity) // the Model View Projection matrix
    private val mvMatrices = new Stack[Matrix44].push(Matrix44.identity)  // the Model View matrix
    
    def pushProjection(matrix: Matrix44) {
        mvpMatrices.push(mvpMatrices.top.mult(matrix))
    }

    def pushModelView(matrix: Matrix44) {
        mvpMatrices.push(mvpMatrices.top.mult(matrix))
        mvMatrices.push(mvMatrices.top.mult(matrix))
    }

    def popProjection {
        mvpMatrices.pop
    }

    def popModelView {
        mvpMatrices.pop
        mvMatrices.pop
    }

    def getModelViewProjectionMatrix: Matrix44 = {
        mvpMatrices.top
    }
    
    def getModelViewMatrix: Matrix44 = {
        mvMatrices.top
    }
    
    def reset {
        mvpMatrices.clear
        mvMatrices.clear
        mvpMatrices.push(Matrix44.identity)
        mvMatrices.push(Matrix44.identity)
    }
    
}
