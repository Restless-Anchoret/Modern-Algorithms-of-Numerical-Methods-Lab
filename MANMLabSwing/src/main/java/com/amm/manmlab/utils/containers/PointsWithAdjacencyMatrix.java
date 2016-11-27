package com.amm.manmlab.utils.containers;

import com.amm.manmlab.errors.ErrorMessages;
import com.amm.manmlab.utils.primitives.Point;

import java.util.LinkedList;
import java.util.List;

public class PointsWithAdjacencyMatrix {

    public boolean[][] adjacencyMatrix;
    public Point[] points;

    public PointsWithAdjacencyMatrix(Point[] points, boolean[][] adjacencyMatrix) {
        if (points.length != adjacencyMatrix.length ||
                points.length != adjacencyMatrix[0].length) {
            throw new IllegalArgumentException(ErrorMessages.POINTS_SIZE_DOESNT_EQUAL_ADJECENCY_MATRIX_SIZE.toString());
        }
        this.adjacencyMatrix = adjacencyMatrix;
        this.points = points;
    }


}
