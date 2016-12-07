package com.amm.manmlab.algorithms.finiteelement;

/**
 *
 * @author Целыковский
 */
class CholeskyMethodOutput {
    
    //Сам вектор решения.
    private final Double[] answerVertex;
    
    CholeskyMethodOutput (Double[] answerVertex){
        this.answerVertex = answerVertex;
    }
    
    public Double[] getAnswerVertex(){
        return answerVertex;
    }
    
}
