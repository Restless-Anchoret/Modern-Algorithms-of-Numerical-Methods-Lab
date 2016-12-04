package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.matrix.BandedMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * TODO как бы его получше назвать...
 * Алгоритм добавления начальных условий в исходную ленточную матрицу
 * @author Aleksandr Pyatakov
 */
class BorderConditionsProcessor implements Algorithm<BorderConditionProcessorInput, BorderConditionsProcesorOutput> {

    private static final Logger LOG = LoggerFactory.getLogger(BorderConditionsProcessor.class);

    @Override
    public BorderConditionsProcesorOutput doAlgorithm(BorderConditionProcessorInput borderConditionProcessorInput) {

        BandedMatrix inputMatrix = borderConditionProcessorInput.getInitialMatrix();
        Double[] borderConditions = borderConditionProcessorInput.getBorderConditions();

        boolean[] visitedRows = new boolean[inputMatrix.getRowSize()];

        int rowSize = inputMatrix.getRowSize();

        Double[] rightHandSide = new Double[rowSize];
        for (int i = 0; i < rowSize; i++) {
            rightHandSide[i] = 0d;
        }

        //первый этап: заполняем правую часть по граничным условиям (зануляем все элементы в строке, кроме диагонального)
        //внимание: саму матрицу эта часть не меняет, все зануления производятся во втором этапе
        for (int i = 0; i < rowSize; i++) {
            Double currentCondition = borderConditions[i];
            if (currentCondition != null && currentCondition > 1E-8) {
                double r = inputMatrix.getElement(i, i) * currentCondition;
                rightHandSide[i] = r;
                visitedRows[i] = true;
            }
        }

        //второй этап: переносим компоненты, содержащие известные значения, в правую часть (убираем столбцы)
        //TODO можно сэкономить на итерациях
        for (int j = 0; j < rowSize; j++) {
            int i = 0;
            while (i < rowSize) {
                if (!visitedRows[i] && borderConditions[j] != null && borderConditions[j] > 1E-8) {
                    rightHandSide[i] -= inputMatrix.getElement(i, j) * borderConditions[j];
                    inputMatrix.setElement(i, j, 0);
                }
                i++;
            }

        }

        LOG.debug("Матрица после добавления граничных условий", Arrays.deepToString(inputMatrix.getFullMatrix()));

        return new BorderConditionsProcesorOutput(inputMatrix, rightHandSide);

    }
}
