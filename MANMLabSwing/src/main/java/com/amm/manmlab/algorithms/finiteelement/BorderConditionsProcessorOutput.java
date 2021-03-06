package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;
import java.util.Arrays;

/**
 * Контейнер для результата алгоритма добавления граничных условий
 * @author Aleksandr Pyatakov
 */
class BorderConditionsProcessorOutput {

    /**
     * Преобразованная ленточная матрица, которую можно передавать
     * в алгоритм метода Халецкого
     */
    private final BandedMatrix processedMatrix;

    /**
     * Вектор правой части
     */
    private final Double[] rightHandSide;

    BorderConditionsProcessorOutput(BandedMatrix processedMatrix, Double[] rightHandSide) {
        this.processedMatrix = processedMatrix;
        this.rightHandSide = rightHandSide;
    }

    public BandedMatrix getProcessedMatrix() {
        return processedMatrix;
    }

    public Double[] getRightHandSide() {
        return rightHandSide;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("BorderConditionsProcessorOutput{");
        sb.append("processorMatrix=").append(processedMatrix);
        sb.append(", rightHandSide=").append(Arrays.toString(rightHandSide));
        sb.append('}');
        return sb.toString();
    }
    
}
