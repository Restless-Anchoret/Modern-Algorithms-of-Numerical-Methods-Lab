package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import java.util.List;

/**
 * Алгоритм трингуляции - выемка-вырезка + центрирование звезд
 */
public class TriangulationAlgorithm implements Algorithm<PointsWithEdges, PointsWithEdges> {

    @Override
    public PointsWithEdges doAlgorithm(PointsWithEdges screenObjects) {
        return starCentering((new BasicTriangulationAlgorithm()).doAlgorithm(screenObjects));
    }

    //центрирование звёзд
    private PointsWithEdges starCentering(PointsWithEdges data) {
        // Тут надо написать код
        return data.clone();
    }
}
