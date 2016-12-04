package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;
import com.amm.manmlab.matrix.BandedMatrixImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

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
          Инициализация входных данных участка алгоритма
         */

        Double[] borderConditions = {1.0, 0.0, 3.0, 0.0, 0.0};

        //Трехдиагональная матрица 6*6
        BandedMatrix initialMatrix = new BandedMatrixImpl(6, 1);

        //По главной диагонали цифры от 1 до 6
        for (int i = 0; i < 6; i++) {
            initialMatrix.setElement(i, i, i+1);
        }

        //По побочным диагоналям от 2 до 6
        for (int i = 0; i < 5; i++) {
            initialMatrix.setElement(i, i + 1, i + 2);
        }

        // Проверяем, что она таки симметрична
        for (int i = 0; i < 5; i++) {
            double symmetricalElement = initialMatrix.getElement(i + 1, i);
            Assert.assertEquals(symmetricalElement, i + 2, 0.00001);
        }

        System.out.println(Arrays.deepToString(initialMatrix.getFullMatrix()));

        BorderConditionProcessorInput input = new BorderConditionProcessorInput(initialMatrix, borderConditions);

        /*
            Инициализация ожидаемых значений результата работы алгоритма
         */

        BandedMatrix expectedMatrix = new BandedMatrixImpl(6, 1);
        Double[] rightHandSide = {};



        BorderConditionsProcesorOutput result =
                processor.doAlgorithm(input);

        Assert.assertNotNull(result);

    }


}