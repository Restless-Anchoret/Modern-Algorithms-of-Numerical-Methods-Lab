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
        Double[][] lMatrix = new Double[rowSize][rowSize];
        Double [] x = new Double[rowSize];
        Double [] y = new Double[rowSize];
        
        //Заполним дополнительную матрицу. Она понадобится для вычисления решений.
        for (int i = 0; i < rowSize; i++){
            Double l = 0.0;
            for (int j = 0; j < i + Math.floor(bandSize/2) && j < i - Math.floor(bandSize/2); j++){
                if (i == j){
                    for (int k = 0; k < i - 1; k++){
                        l = Math.pow(processedMatrix.getElement(i, k), 2);
                    }
                    lMatrix[i][i] = Math.sqrt(processedMatrix.getElement(i, i) - l);
                }
                else{
                    for (int k = 0; k < i-1; k++){
                        l = processedMatrix.getElement(i, k)*processedMatrix.getElement(j, k);
                    }
                    lMatrix[i][j] = (1/lMatrix[i][i])*(processedMatrix.getElement(j, i) - l);
                }
            } 
        }

        
        //Заполним дополнительный вектор.
        for(int i = 0; i < rowSize; i++){
            Double l = 0.0;
            for (int j = 0; j < rowSize; j++){
                for (int k = 0; k < i - 1; k++){
                    l = lMatrix[i][k] * y[k];
                }
                y[i] = (1/lMatrix[i][i])*(borderConditions[i] - l);
            }
        }
        
        //Найдём решения.
        for(int i = rowSize; i > 0; i--){
            Double l = 0.0;
            for (int j = 0; j < rowSize; j++){
                for (int k = 0; k < i - 1; k++){
                    l = lMatrix[k][i] * x[k];
                }
                x[i] = (1/lMatrix[i][i])*(borderConditions[i] - l);
            }
        }

        LOG.debug("] (return {})", x);
        return new CholeskyMethodOutput (x);
    }
}
