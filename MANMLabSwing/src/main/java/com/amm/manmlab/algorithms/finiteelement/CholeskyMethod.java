package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;
import com.amm.manmlab.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 *
 * @author Целыковский
 */
class CholeskyMethod implements Algorithm <BorderConditionsProcesorOutput, CholeskyMethodOutput> {

    private static final Logger LOG = LoggerFactory.getLogger(CholeskyMethod.class);

    @Override
    public CholeskyMethodOutput doAlgorithm(BorderConditionsProcesorOutput borderConditionsProcesorOutput) {
        BandedMatrix processedMatrix = borderConditionsProcesorOutput.getProcessedMatrix();
        Double[] borderConditions = borderConditionsProcesorOutput.getRightHandSide();
        LOG.debug("[ (processedMatrix : {}, borderConditions : {})",
                Arrays.deepToString(processedMatrix.getFullMatrix()),
                Arrays.deepToString(borderConditions));


        int rowSize = processedMatrix.getRowSize();
        int bandSize = processedMatrix.getBandSize();
        LOG.debug("rowSize = {}, bandSize = {}", rowSize, bandSize);

        //Создадим дополнительную матрицу, совпадающую по размерности с processedMatrix.
        //Дополнительно создадим 2 вектора.
        Double[][] bMatrix = new Double[rowSize][rowSize];

        Double [] x = new Double[rowSize];
        Double [] y = new Double[rowSize];

        for (int i = 0; i < rowSize; i++){
            x[i] = 0.0;
            y[i] = 0.0;
            for (int j = 0; j < rowSize; j++){
                bMatrix[i][j] = 0.0;
            }
        }

        for (int i = 0; i < rowSize; i++){
            bMatrix[i][0] = processedMatrix.getElement(i, 0);
        }
        LOG.debug("bMatrix = :{}", Arrays.deepToString(bMatrix));

        //Составим новую матрицу bMatrix.

        for (int i = 1; i < rowSize; i++){
            for (int j = i; j < rowSize; j++){
                for (int k = 0; k < j; k++){
                    bMatrix [i][j] = processedMatrix.getElement(i, j) - bMatrix[i][k] * bMatrix[j][k] / bMatrix[k][k];
                }
            }
        }
        LOG.debug("new bMatrix = :{}", Arrays.deepToString(bMatrix));

        //Заполним дополнительный вектор y.

        y[0] = borderConditions[0];

        for(int i = 1; i < rowSize; i++){
            for (int k = 0; k < i - 1; k++){
                y[i] = borderConditions[i] - bMatrix[i][k] * y[k];
            }
            y[i] = y[i] / bMatrix[i][i];
        }
        LOG.debug("y[] = {}", Arrays.deepToString(y));


        //Найдём решения x.

        x[rowSize - 1] = y[rowSize - 1];

        LOG.debug("x = {}", Arrays.deepToString(x));
        for(int i = rowSize - 2; i > -1; i--){
            for (int k = i + 1; k < rowSize; k++){
                x[i] = y[i] - bMatrix[k][i] * x[k] / bMatrix[i][i];
            }
        }
        LOG.debug("x[] = {}", Arrays.deepToString(x));

        LOG.debug("] (return {})", x);
        return new CholeskyMethodOutput (x);
    }
}
