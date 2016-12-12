package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;
import com.amm.manmlab.matrix.BandedMatrixImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Aleksandr Pyatakov
 */
public class BorderConditionsProcessorTest {

    private BorderConditionsProcessor processor;

    @Before
    public void setUp() {
        processor = new BorderConditionsProcessor();
    }

    @Test
    public void basicTest() {

        /*
         * Инициализация входных данных участка алгоритма
         * См. Сегерлинд, п. 7.2.1
         */

        Double[] borderConditions = {1d, null, 3d, null, null, null};

        /*
         * Трехдиагональная матрица 6*6
         *
         * [[1.0, 2.0, 0.0, 0.0, 0.0, 0.0],
         *  [2.0, 2.0, 3.0, 0.0, 0.0, 0.0],
         *  [0.0, 3.0, 3.0, 4.0, 0.0, 0.0],
         *  [0.0, 0.0, 4.0, 4.0, 5.0, 0.0],
         *  [0.0, 0.0, 0.0, 5.0, 5.0, 6.0],
         *  [0.0, 0.0, 0.0, 0.0, 6.0, 6.0]]
         */
        BandedMatrix initialMatrix = new BandedMatrixImpl(6, 1);

        for (int i = 0; i < 6; i++) {
            initialMatrix.setElement(i, i, i + 1);
        }

        for (int i = 0; i < 5; i++) {
            initialMatrix.setElement(i, i + 1, i + 2);
        }

        // Проверяем, что она таки симметрична
        for (int i = 0; i < 5; i++) {
            double symmetricalElement = initialMatrix.getElement(i + 1, i);
            Assert.assertEquals(symmetricalElement, i + 2, 1E-8);
        }

        //System.out.println(Arrays.deepToString(initialMatrix.getFullMatrix()));

        BorderConditionProcessorInput input = new BorderConditionProcessorInput(initialMatrix, borderConditions);

        /*
         *  Инициализация ожидаемых значений результата работы алгоритма
         */

        /*
         * [[1.0, 0.0, 0.0, 0.0, 0.0, 0.0],
         *  [0.0, 2.0, 0.0, 0.0, 0.0, 0.0],
         *  [0.0, 0.0, 1.0, 0.0, 0.0, 0.0],
         *  [0.0, 0.0, 0.0, 4.0, 5.0, 0.0],
         *  [0.0, 0.0, 0.0, 5.0, 5.0, 6.0],
         *  [0.0, 0.0, 0.0, 0.0, 6.0, 6.0]]
         *
         */
        BandedMatrix expectedMatrix = new BandedMatrixImpl(6, 1);

        expectedMatrix.setElement(0, 0, 1);
        expectedMatrix.setElement(1, 1, 2);
        expectedMatrix.setElement(2, 2, 1);
        expectedMatrix.setElement(3, 3, 4);
        expectedMatrix.setElement(4, 4, 5);
        expectedMatrix.setElement(5, 5, 6);

        expectedMatrix.setElement(3, 4, 5);
        expectedMatrix.setElement(4, 5, 6);


        Double[] rightHandSide = {1d, -11d, 3d, -12d, 0d, 0d};


        BorderConditionsProcesorOutput result =
                processor.doAlgorithm(input);

        Assert.assertNotNull(result);

        Assert.assertArrayEquals("Правые части должны совпадать",
                rightHandSide, result.getRightHandSide());

        checkThatMatricesAreEqual(expectedMatrix, result.getProcessedMatrix());


    }

    @Test
    public void shouldWorkWithFirstAndLastElementsAsBoundaryConditions() {

        /*
         * Инициализация входных данных участка алгоритма
         * См. Сегерлинд, п. 7.2.1
         */

        Double[] borderConditions = {1d, null, null, null, null, 1d};

        /*
         * Трехдиагональная матрица 6*6
         *
         * [[1.0, 2.0, 0.0, 0.0, 0.0, 0.0],
         *  [2.0, 2.0, 3.0, 0.0, 0.0, 0.0],
         *  [0.0, 3.0, 3.0, 4.0, 0.0, 0.0],
         *  [0.0, 0.0, 4.0, 4.0, 5.0, 0.0],
         *  [0.0, 0.0, 0.0, 5.0, 5.0, 6.0],
         *  [0.0, 0.0, 0.0, 0.0, 6.0, 6.0]]
         */
        BandedMatrix initialMatrix = new BandedMatrixImpl(6, 1);

        for (int i = 0; i < 6; i++) {
            initialMatrix.setElement(i, i, i + 1);
        }

        for (int i = 0; i < 5; i++) {
            initialMatrix.setElement(i, i + 1, i + 2);
        }

        System.out.println(Arrays.deepToString(initialMatrix.getFullMatrix()));

        BorderConditionProcessorInput input = new BorderConditionProcessorInput(initialMatrix, borderConditions);

        /*
         *  Инициализация ожидаемых значений результата работы алгоритма
         */

        /*
         *
         * [[1.0, 0.0, 0.0, 0.0, 0.0, 0.0],
         *  [0.0, 2.0, 3.0, 0.0, 0.0, 0.0],
         *  [0.0, 3.0, 3.0, 4.0, 0.0, 0.0],
         *  [0.0, 0.0, 4.0, 4.0, 5.0, 0.0],
         *  [0.0, 0.0, 0.0, 5.0, 5.0, 0.0],
         *  [0.0, 0.0, 0.0, 0.0, 0.0, 1.0]]
         *
         */
        BandedMatrix expectedMatrix = new BandedMatrixImpl(6, 1);

        for (int i = 0; i < 5; i++) {
            expectedMatrix.setElement(i, i, i + 1);
        }

        expectedMatrix.setElement(5, 5, 1);

        for (int i = 1; i < 4; i++) {
            expectedMatrix.setElement(i + 1, i, i + 2);
        }

        System.out.println(Arrays.deepToString(expectedMatrix.getFullMatrix()));

        Double[] rightHandSide = {1d, -2d, 0d, 0d, -6d, 1d};


        BorderConditionsProcesorOutput result =
                processor.doAlgorithm(input);

        Assert.assertNotNull(result);

        Assert.assertArrayEquals("Правые части должны совпадать",
                rightHandSide, result.getRightHandSide());

        checkThatMatricesAreEqual(expectedMatrix, result.getProcessedMatrix());

    }

    @Test
    public void shouldWorkWithFilledMatrix() {
        /*
         * Инициализация входных данных участка алгоритма
         * См. Сегерлинд, п. 7.2.1
         */

        Double[] borderConditions = {1d, null, 2d, null, null, 1d};

        /*
         * [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0],
         *  [2.0, 2.0, 3.0, 4.0, 5.0, 6.0],
         *  [3.0, 3.0, 3.0, 4.0, 5.0, 6.0],
         *  [4.0, 4.0, 4.0, 4.0, 5.0, 6.0],
         *  [5.0, 5.0, 5.0, 5.0, 5.0, 6.0],
         *  [6.0, 6.0, 6.0, 6.0, 6.0, 6.0]]
         */
        BandedMatrix initialMatrix = new BandedMatrixImpl(6, 5);

        for (int i = 0; i < 6; i++) {
            initialMatrix.setElement(i, i, i + 1);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 6; j++) {
                initialMatrix.setElement(i, j, j + 1);
            }
        }


        BorderConditionProcessorInput input = new BorderConditionProcessorInput(initialMatrix, borderConditions);

        /*
         *  Инициализация ожидаемых значений результата работы алгоритма
         */

        /*
         * [[1.0, 0.0, 0.0, 0.0, 0.0, 0.0],
         *  [0.0, 2.0, 0.0, 4.0, 5.0, 0.0],
         *  [0.0, 0.0, 1.0, 0.0, 0.0, 0.0],
         *  [0.0, 4.0, 0.0, 4.0, 5.0, 0.0],
         *  [0.0, 5.0, 0.0, 5.0, 5.0, 0.0],
         *  [0.0, 0.0, 0.0, 0.0, 0.0, 1.0]]
         */
        BandedMatrix expectedMatrix = new BandedMatrixImpl(6, 5);

        expectedMatrix.setElement(0, 0, 1);
        expectedMatrix.setElement(1, 1, 2);
        expectedMatrix.setElement(2, 2, 1);
        expectedMatrix.setElement(3, 3, 4);
        expectedMatrix.setElement(4, 4, 5);
        expectedMatrix.setElement(5, 5, 1);

        expectedMatrix.setElement(1, 3, 4);
        expectedMatrix.setElement(1, 4, 5);
        expectedMatrix.setElement(3, 4, 5);


        Double[] rightHandSide = {1d, -14d, 2d, -18d, -21d, 1d};


        BorderConditionsProcesorOutput result =
                processor.doAlgorithm(input);

        Assert.assertNotNull(result);

        Assert.assertArrayEquals("Правые части должны совпадать",
                rightHandSide, result.getRightHandSide());

        checkThatMatricesAreEqual(expectedMatrix, result.getProcessedMatrix());

    }

    @Test
    public void shouldYieldIdentityMatrix() {
        /*
         * Инициализация входных данных участка алгоритма
         * См. Сегерлинд, п. 7.2.1
         */

        Double[] borderConditions = {1d, 1d, 2d, 2d, 3d, 3d};

        /*
         * [[1.0, 2.0, 3.0, 4.0, 5.0, 6.0],
         *  [2.0, 2.0, 3.0, 4.0, 5.0, 6.0],
         *  [3.0, 3.0, 3.0, 4.0, 5.0, 6.0],
         *  [4.0, 4.0, 4.0, 4.0, 5.0, 6.0],
         *  [5.0, 5.0, 5.0, 5.0, 5.0, 6.0],
         *  [6.0, 6.0, 6.0, 6.0, 6.0, 6.0]]
         */
        BandedMatrix initialMatrix = new BandedMatrixImpl(6, 5);

        for (int i = 0; i < 6; i++) {
            initialMatrix.setElement(i, i, i + 1);
        }

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 6; j++) {
                initialMatrix.setElement(i, j, j + 1);
            }
        }

        System.out.println(Arrays.deepToString(initialMatrix.getFullMatrix()));

        BorderConditionProcessorInput input = new BorderConditionProcessorInput(initialMatrix, borderConditions);

         /*
         *  Инициализация ожидаемых значений результата работы алгоритма
         */

        /*
         * [[1.0, 0.0, 0.0, 0.0, 0.0, 0.0],
         *  [0.0, 1.0, 0.0, 0.0, 0.0, 0.0],
         *  [0.0, 0.0, 1.0, 0.0, 0.0, 0.0],
         *  [0.0, 0.0, 0.0, 1.0, 0.0, 0.0],
         *  [0.0, 0.0, 0.0, 0.0, 1.0, 0.0],
         *  [0.0, 0.0, 0.0, 0.0, 0.0, 1.0]]
         */
        BandedMatrix expectedMatrix = new BandedMatrixImpl(6, 1);

        for (int i = 0; i < 6; i++) {
            expectedMatrix.setElement(i, i, 1);
        }

        BorderConditionsProcesorOutput result =
                processor.doAlgorithm(input);

        Assert.assertNotNull(result);

        checkThatMatricesAreEqual(expectedMatrix, result.getProcessedMatrix());


    }

    private void checkThatMatricesAreEqual(BandedMatrix expectedMatrix, BandedMatrix processedMatrix) {

        for (int i = 0; i < 6; i++) {
            for (int j = i; j < 6; j++) {
                Assert.assertEquals(String.format("Element with indexes %d, %d differs from expectation", i, j), expectedMatrix.getElement(i, j), processedMatrix.getElement(i, j), 1E-8);
            }
        }

    }


}