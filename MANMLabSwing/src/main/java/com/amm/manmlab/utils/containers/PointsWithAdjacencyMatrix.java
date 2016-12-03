package com.amm.manmlab.utils.containers;

import com.amm.manmlab.errors.ErrorMessages;
import com.amm.manmlab.utils.primitives.Point;

import java.util.Arrays;


public class PointsWithAdjacencyMatrix {

    private boolean[][] adjacencyMatrix;
    private Point[] points;

    public PointsWithAdjacencyMatrix(Point[] points, boolean[][] adjacencyMatrix) {
        if (points.length != adjacencyMatrix.length ||
                points.length != adjacencyMatrix[0].length) {
            throw new IllegalArgumentException(ErrorMessages.POINTS_SIZE_DOESNT_EQUAL_ADJECENCY_MATRIX_SIZE.toString());
        }
        this.adjacencyMatrix = adjacencyMatrix;
        this.points = points;
    }

    public boolean[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public Point[] getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "PointsWithAdjacencyMatrix{" +
                "adjacencyMatrix=" + Arrays.deepToString(adjacencyMatrix) +
                ", points=" + Arrays.deepToString(points) + '}';
    }

}