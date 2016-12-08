package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.matrix.BandedMatrix;
import com.amm.manmlab.matrix.BandedMatrixImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class CholeskyMethodTest {

    private static final Logger LOG = LoggerFactory.getLogger(CholeskyMethodTest.class);

    /**
     * {{1,2},{3,4}}*{x,y} = {1,0}
     * x = -2
     * y = 3/2
     */
    @Test
    public void basicTest() {
        LOG.info("[");
        BandedMatrix matrix = new BandedMatrixImpl(2,2);
        matrix.setElement(0, 0, 1.0);
        matrix.setElement(0, 1, 2.0);
        matrix.setElement(1, 0, 3.0);
        matrix.setElement(1, 1, 4.0);
        LOG.debug("matrix : {{1,2},{3,4}}");

        Double[] b = new Double[]{new Double(1.0), new Double(0.0)};

        BorderConditionsProcesorOutput input = new BorderConditionsProcesorOutput(
                matrix,
                b
            );

        CholeskyMethod method = new CholeskyMethod();
        CholeskyMethodOutput output = method.doAlgorithm(input);

        Double[] actualOutput = output.getAnswerVertex();
        Double[] expectedOutput = new Double[]{-2.0, 1.5};
        LOG.debug("actualOutput : {}", actualOutput);
        LOG.debug("expectedOutput : {}", expectedOutput);
        Assert.assertEquals("Sizes don't equal!", expectedOutput.length, actualOutput.length);
        for (int i = 0; i < expectedOutput.length; i++) {
            Assert.assertEquals("Elements " + i + " don't equal",
                    expectedOutput[i], actualOutput[i], 0.1);
        }
    }

}