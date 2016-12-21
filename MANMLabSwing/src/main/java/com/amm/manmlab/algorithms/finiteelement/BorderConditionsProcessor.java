package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.matrix.BandedMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * TODO как бы его получше назвать...
 * Алгоритм добавления начальных условий в исходную ленточную матрицу
 *
 * @author Aleksandr Pyatakov
 */
class BorderConditionsProcessor implements Algorithm<BorderConditionProcessorInput, BorderConditionsProcessorOutput> {

    private static final Logger LOG = LoggerFactory.getLogger(BorderConditionsProcessor.class);

    @Override
    public BorderConditionsProcessorOutput doAlgorithm(BorderConditionProcessorInput borderConditionProcessorInput) {

        BandedMatrix inputMatrix = borderConditionProcessorInput.getInitialMatrix();
        Double[] borderConditions = borderConditionProcessorInput.getBorderConditions();

        boolean[] visitedRows = new boolean[inputMatrix.getRowSize()];

        int rowSize = inputMatrix.getRowSize();

        Double[] rightHandSide = new Double[rowSize];
        for (int i = 0; i < rowSize; i++) {
            rightHandSide[i] = 0d;
        }

        /*
         * Первый этап: заполняем правую часть по граничным условиям (зануляем все элементы в строке, кроме диагонального)
         * внимание: саму матрицу эта часть не меняет, все зануления производятся во втором этапе
         * на диагонали ставим единицу во избежание деления на маленькое число и накопления погрешности
         * в методе Халецкого
         */
        for (int i = 0; i < rowSize; i++) {
            Double currentCondition = borderConditions[i];
            if (currentCondition != null) {
                rightHandSide[i] = currentCondition;
                inputMatrix.setElement(i, i, 1);
                visitedRows[i] = true;
            }
        }

        // Второй этап: переносим компоненты, содержащие известные значения, в правую часть (убираем столбцы)
        //TODO возможно, можно сэкономить на итерациях
        for (int j = 0; j < rowSize; j++) {
            int i = 0;
            while (i < rowSize) {
                if (!visitedRows[i] && borderConditions[j] != null) {
                    rightHandSide[i] -= inputMatrix.getElement(i, j) * borderConditions[j];
                    inputMatrix.setElement(i, j, 0);
                }
                i++;
            }
        }

        // Третий этап: зануляем все элементы в строках, где были заданы граничные условия
        //TODO учесть результаты второго этапа
        for (int i = 0; i < rowSize - 1; i++)
            if (visitedRows[i]) {
                for (int j = i + 1; j < rowSize; j++) {
                    inputMatrix.setElement(i, j, 0);
                }
            }

        LOG.debug("Матрица после добавления граничных условий", Arrays.deepToString(inputMatrix.getFullMatrix()));

        return new BorderConditionsProcessorOutput(inputMatrix, rightHandSide);

    }
}
