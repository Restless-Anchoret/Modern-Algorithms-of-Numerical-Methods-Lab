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
    public void testLittle() {
        testWithParameters(6, 5, 3, 1e-8);
    }
    
    @Test
    public void testMiddle() {
        testWithParameters(10, 50, 10, 1e-8);
    }
    
    @Test
    public void testBig() {
        testWithParameters(10, 1000, 100, 1e-8);
    }
    
    @Test
    public void testHuge() {
        testWithParameters(100, 10000, 100, 1e-8);
    }
    
    private void testWithParameters(int numbersBound, int dimension, int band, double epsilon) {
        System.out.println("numbersBound = " + numbersBound);
        System.out.println("dimension = " + dimension);
        System.out.println("band = " + band);
        Random random = new Random(0);
        BandedMatrix matrix = new BandedMatrixImpl(dimension, band);
        for (int i = 0; i < dimension; i++) {
            for (int j = i; j <= Math.min(dimension - 1, i + band); j++) {
                matrix.setElement(i, j, ((int)(random.nextDouble() * numbersBound * 10)) / 10.0 + 0.1);
            }
        }
        double[] x = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            x[i] = ((int)(random.nextDouble() * numbersBound * 10)) / 10.0 + 0.1;
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
        System.out.println();
        assertTrue(Double.isFinite(maxError) && maxError < epsilon);
    }
    
}