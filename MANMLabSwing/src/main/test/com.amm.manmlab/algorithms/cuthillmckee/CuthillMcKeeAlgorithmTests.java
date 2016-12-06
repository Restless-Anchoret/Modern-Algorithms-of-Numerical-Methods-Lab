package com.amm.manmlab.algorithms.cuthillmckee;

import com.amm.manmlab.utils.containers.PointsWithAdjacencyMatrix;
import com.amm.manmlab.utils.primitives.Point;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CuthillMcKeeAlgorithmTests {

    private static final Logger LOG = LoggerFactory.getLogger(CuthillMcKeeAlgorithmTests.class);

    @Test
    public void basicTest(){
        LOG.info("[");

        Point[] points = new Point[]{
                new Point(1., 1.),
                new Point(0., 1.),
                new Point(2., 1.),
                new Point(1., 0.),
                new Point(0., 0.),
                new Point(2., 0.)
            };
        boolean[][] adjacencyMatrix = new boolean[][]{
                {true, true, true, true, true, true},
                {true, true, false, false, true, false},
                {true, false, true, false, false, true},
                {true, false, false, true, true, true},
                {true, true, false, true, true, false},
                {true, false, true, true, false, true},
            };
        PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix =
                new PointsWithAdjacencyMatrix(points, adjacencyMatrix);
        LOG.debug("pointsWithAdjacencyMatrix : {}", pointsWithAdjacencyMatrix);

        CuthillMcKeeAlgorithm algorithm = new CuthillMcKeeAlgorithm();
        pointsWithAdjacencyMatrix = algorithm.doAlgorithm(pointsWithAdjacencyMatrix);
        LOG.debug("pointsWithAdjacencyMatrix : {}", pointsWithAdjacencyMatrix);

        Point[] expectedPoints = new Point[]{
                new Point(0., 0.),
                new Point(0., 1.),
                new Point(1., 0.),
                new Point(1., 1.),
                new Point(2., 0.),
                new Point(2., 1.)
            };
        boolean[][] expectedAdjacencyMatrix = new boolean[][]{
                {true, true, true, true, false, false},
                {true, true, false, true, false, false},
                {true, false, true, true, true, false},
                {true, true, true, true, true, true},
                {false, false, true, true, true, true},
                {false, false, false, true, true, true},
            };

        for (int i = 0; i < expectedAdjacencyMatrix.length; i++) {
            Assert.assertArrayEquals(
                    "Rows " + i + " doesn't equal",
                    expectedAdjacencyMatrix[i],
                    pointsWithAdjacencyMatrix.getAdjacencyMatrix()[i]
                );
        }

        Assert.assertArrayEquals(expectedPoints, pointsWithAdjacencyMatrix.getPoints());

        LOG.info("]");
    }

}
