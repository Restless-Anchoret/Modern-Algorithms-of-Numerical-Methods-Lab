package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;
import com.amm.manmlab.matrix.BandedMatrixImpl;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;


public class CholeskyTest extends Assert {
    
    @Test
    public void test() {
        Random random = new Random(0);
        int numbersBound = 100;
        int dimension = 1000;
        int band = 100;
        BandedMatrix matrix = new BandedMatrixImpl(dimension, band);
        for (int i = 0; i < dimension; i++) {
            for (int j = i; j <= Math.min(dimension - 1, i + band); j++) {
                matrix.setElement(i, j, random.nextInt(numbersBound));
            }
        }
        double[] x = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            x[i] = random.nextInt(numbersBound);
        }
        Double[] rightSide = new Double[dimension];
        Arrays.fill(rightSide, 0.0);
        for (int i = 0; i < dimension; i++) {
            for (int j = Math.max(0, i - band); j <= Math.min(dimension - 1, i + band); j++) {
                rightSide[i] += matrix.getElement(i, j) * x[j];
            }
        }
        CholeskyMethod method = new CholeskyMethod();
        Date start = new Date();
        CholeskyMethodOutput output = method.doAlgorithm(new BorderConditionsProcessorOutput(matrix, rightSide));
        Date finish = new Date();
        Double[] solution = output.getAnswerVertex();
        double maxError = 0.0;
        for (int i = 0; i < dimension; i++) {
            maxError = Math.max(maxError, Math.abs(solution[i] - x[i]));
        }
        System.out.println("Time: " + (finish.getTime() - start.getTime()) + " millis");
        System.out.println("Max error: " + maxError);
        double epsilon = 1e-8;
        assertTrue(Double.isFinite(maxError) && maxError < epsilon);
    }
    
}