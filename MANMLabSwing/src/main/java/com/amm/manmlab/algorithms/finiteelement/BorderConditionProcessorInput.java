package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;

/**
 * @author Aleksandr Pyatakov
 */
class BorderConditionProcessorInput {

    private final BandedMatrix initialMatrix;
    private final Double[] borderConditions;

    BorderConditionProcessorInput(BandedMatrix initialMatrix, Double[] borderConditions) {
        this.initialMatrix = initialMatrix;
        this.borderConditions = borderConditions;
    }

    public Double[] getBorderConditions() {
        return borderConditions;
    }

    public BandedMatrix getInitialMatrix() {
        return initialMatrix;
    }
}