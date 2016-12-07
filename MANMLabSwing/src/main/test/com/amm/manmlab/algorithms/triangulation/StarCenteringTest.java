package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class StarCenteringTest {

    private static final Logger LOG = LoggerFactory.getLogger(StarCenteringTest.class);

    @Test
    public void starCenteringTest(){
        LOG.info("[");

        List<Point> points = Arrays.asList(
                new Point (2.0, 0.0),
                new Point (0.0, 2.0),
                new Point (-2.0, 0.0),
                new Point (0.0, -2.0),
                new Point (1.0, 0.0));
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1),
                new Edge(1, 2),
                new Edge(2, 3),
                new Edge(3, 0),
                new Edge(0, 4),
                new Edge(1, 4),
                new Edge(2, 4),
                new Edge(3, 4));
        LOG.trace("points : {}", points);
        LOG.trace("edges : {}", edges);
        PointsWithEdges pointsWithEdges = new PointsWithEdges(edges, points);

        TriangulationAlgorithm algorithm = new TriangulationAlgorithm();
        pointsWithEdges = algorithm.starCentering(pointsWithEdges);

        Point actualPoint = pointsWithEdges.getPoints().get(4);

        LOG.debug("actualPoint : {}", actualPoint);
        Assert.assertEquals(0.0, actualPoint.getX(), 0.1);
        Assert.assertEquals(0.0, actualPoint.getY(), 0.1);
    }
}
