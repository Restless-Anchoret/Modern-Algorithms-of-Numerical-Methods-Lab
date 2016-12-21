package com.amm.manmlab.algorithms.finiteelement;

import java.util.Arrays;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CholeskyMethodOutput{");
        sb.append(", answerVertex=").append(Arrays.toString(answerVertex));
        sb.append('}');
        return sb.toString();
    }
    
}
