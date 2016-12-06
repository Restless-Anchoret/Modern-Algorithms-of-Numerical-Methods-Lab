package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;

/**
 * Контейнер для начальных условий алгоритма добавления граничных условий
 * @author Aleksandr Pyatakov
 */
class BorderConditionProcessorInput {

    /**
     * Исходная ленточная матрица, полученная после
     * вычисления матриц жесткости для каждого элемента
     * и записи их в единую матрицу
     */
    private final BandedMatrix initialMatrix;

    /**
     * Вектор начальных условий
     */
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