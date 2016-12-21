package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;
import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.matrix.BandedMatrixImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


/**
 *
 * @author Целыковский
 */
class CholeskyMethod implements Algorithm <BorderConditionsProcessorOutput, CholeskyMethodOutput> {

    private static final Logger LOG = LoggerFactory.getLogger(CholeskyMethod.class);

    @Override
    public CholeskyMethodOutput doAlgorithm(BorderConditionsProcessorOutput borderConditionsProcesorOutput) {
        BandedMatrix processedMatrix = borderConditionsProcesorOutput.getProcessedMatrix();
        Double[] borderConditions = borderConditionsProcesorOutput.getRightHandSide();
        LOG.debug("[ (processedMatrix : {}, borderConditions : {})",
                processedMatrix,
                Arrays.toString(borderConditions));
        
        int n = processedMatrix.getRowSize();
        int bandSize = processedMatrix.getBandSize();
        BandedMatrix lMatrix = new BandedMatrixImpl(n, processedMatrix.getBandSize());
        
        for (int i = 0; i < n; i++) {
            for (int j = Math.max(0, i - bandSize); j <= i; j++) {
                if (j == i) {
                    double res = processedMatrix.getElement(i, i);
                    for (int k = Math.max(0, i - bandSize); k < i; k++) {
                        double currElement = lMatrix.getElement(i, k);
                        res -= currElement * currElement;
                    }
                    lMatrix.setElement(i, i, Math.sqrt(res));
                } else {
                    double res = processedMatrix.getElement(i, j);
                    for (int k = Math.max(0, i - bandSize); k < i; k++) {
                        res -= lMatrix.getElement(i, k) * lMatrix.getElement(j, k);
                    }
                    lMatrix.setElement(i, j, res / lMatrix.getElement(j, j));
                }
            }
        }
        
        double[] y = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double res = borderConditions[i];
            for (int j = i + 1; j <= Math.min(i + bandSize, n - 1); j++) {
                res -= y[j] * lMatrix.getElement(i, j);
            }
            y[i] = res / lMatrix.getElement(i, i);
        }
        
        Double[] x = new Double[n];
        for (int i = 0; i < n; i++) {
            double res = borderConditions[i];
            for (int j = Math.max(i - bandSize, 0); j < i; j++) {
                res -= x[j] * lMatrix.getElement(i, j);
            }
            x[i] = res / lMatrix.getElement(i, i);
        }
        
        return new CholeskyMethodOutput(x);
        
//        int rowSize = processedMatrix.getRowSize();
//        int bandSize = processedMatrix.getBandSize();
//        LOG.debug("rowSize = {}, bandSize = {}", rowSize, bandSize);
//
//        //Создадим дополнительную матрицу, совпадающую по размерности с processedMatrix.
//        //Дополнительно создадим 2 вектора.
//        Double[][] bMatrix = new Double[rowSize][rowSize];
//
//        Double [] x = new Double[rowSize];
//        Double [] y = new Double[rowSize];
//
//        for (int i = 0; i < rowSize; i++){
//            x[i] = 0.0;
//            y[i] = 0.0;
//            for (int j = 0; j < rowSize; j++){
//                bMatrix[i][j] = 0.0;
//            }
//        }
//
//        for (int i = 0; i < rowSize; i++){
//            bMatrix[i][0] = processedMatrix.getElement(i, 0);
//        }
//        LOG.debug("bMatrix = :{}", Arrays.deepToString(bMatrix));
//
//        //Составим новую матрицу bMatrix.
//
//        for (int i = 1; i < rowSize; i++){
//            for (int j = i; j < rowSize; j++){
//                for (int k = 0; k < j; k++){
//                    bMatrix [i][j] = processedMatrix.getElement(i, j) - bMatrix[i][k] * bMatrix[j][k] / bMatrix[k][k];
//                }
//            }
//        }
//        LOG.debug("new bMatrix = :{}", Arrays.deepToString(bMatrix));
//
//        //Заполним дополнительный вектор y.
//
//        y[0] = borderConditions[0];
//
//        for(int i = 1; i < rowSize; i++){
//            for (int k = 0; k < i - 1; k++){
//                y[i] = borderConditions[i] - bMatrix[i][k] * y[k];
//            }
//            y[i] = y[i] / bMatrix[i][i];
//        }
//        LOG.debug("y[] = {}", Arrays.deepToString(y));
//
//
//        //Найдём решения x.
//
//        x[rowSize - 1] = y[rowSize - 1];
//
//        LOG.debug("x = {}", Arrays.deepToString(x));
//        for(int i = rowSize - 2; i > -1; i--){
//            for (int k = i + 1; k < rowSize; k++){
//                x[i] = y[i] - bMatrix[k][i] * x[k] / bMatrix[i][i];
//            }
//        }
//        LOG.debug("x[] = {}", Arrays.deepToString(x));
//
//        LOG.debug("] (return {})", x);
//        return new CholeskyMethodOutput (x);
    }
}
