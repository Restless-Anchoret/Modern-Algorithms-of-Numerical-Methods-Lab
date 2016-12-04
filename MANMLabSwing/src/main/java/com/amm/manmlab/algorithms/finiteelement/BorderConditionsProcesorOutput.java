package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;

/**
 * @author Aleksandr Pyatakov
 */
class BorderConditionsProcesorOutput {

    private final BandedMatrix processedMatrix;
    private final Double[] rightHandSide;

    BorderConditionsProcesorOutput(BandedMatrix processedMatrix, Double[] rightHandSide) {
        this.processedMatrix = processedMatrix;
        this.rightHandSide = rightHandSide;
    }

    public BandedMatrix getProcessedMatrix() {
        return processedMatrix;
    }

    public Double[] getRightHandSide() {
        return rightHandSide;
    }
}
