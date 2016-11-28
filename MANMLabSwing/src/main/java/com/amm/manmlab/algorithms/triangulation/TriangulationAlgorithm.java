package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.containers.PointsWithEdges;

public class TriangulationAlgorithm implements Algorithm<PointsWithEdges, PointsWithEdges> {

    @Override
    public PointsWithEdges doAlgorithm(PointsWithEdges screenObjects) {

        //метод выемка вырезка
        //call Algorithm<PointsWithEdges, PointsWithEdges>

        //центрирование звёзд
        //call Algorithm<PointsWithEdges, PointsWithEdges>
        return screenObjects.clone();
    }
}
