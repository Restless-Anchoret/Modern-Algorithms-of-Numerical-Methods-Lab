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
class CholeskyMethod implements Algorithm<BorderConditionsProcessorOutput, CholeskyMethodOutput> {

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
                double res = 0.0;
                for (int k = Math.max(0, i - bandSize); k < j; k++) {
                    res += lMatrix.getElement(i, k) * lMatrix.getElement(j, k) / lMatrix.getElement(k, k);
                }
                lMatrix.setElement(i, j, processedMatrix.getElement(i, j) - res);
            }
        }
        
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            Double res = 0.0;
            for (int k = Math.max(0, i - bandSize); k < i; k++) {
                res += lMatrix.getElement(i, k) * y[k];
            }
            y[i] = (borderConditions[i] - res) / lMatrix.getElement(i, i);
        }
        
        Double[] x = new Double[n];
        for (int i = n - 1; i >= 0; i--) {
            double res = 0.0;
            for (int k = i + 1; k <= Math.min(n - 1, i + bandSize); k++) {
                res += lMatrix.getElement(k, i) * x[k];
            }
            x[i] = y[i] - res / lMatrix.getElement(i, i);
        }
        return new CholeskyMethodOutput(x);
    }
    
}
