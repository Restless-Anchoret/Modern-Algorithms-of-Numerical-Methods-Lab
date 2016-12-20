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
    
    public PointsWithAdjacencyMatrix(PointsWithEdges pointsWithEdges)
    {
        int matrixDimension = pointsWithEdges.getPoints().size();
        points = new Point[matrixDimension];
        for(int i=0; i<matrixDimension; i++)
            points[i] = pointsWithEdges.getPoints().get(i);
        
        adjacencyMatrix = new boolean[matrixDimension][matrixDimension];
        for (int i =0; i < matrixDimension; i++)
            for (int j =0; j < matrixDimension; j++)
                adjacencyMatrix[i][j] = i==j;
        pointsWithEdges.getEdges().forEach((edge) -> {
            int first = edge.getFirstIndex();
            int sec = edge.getSecondIndex();
            adjacencyMatrix[first][sec] = true;
            adjacencyMatrix[sec][first] = true;
        });
    }

    public boolean[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public Point[] getPoints() {
        return points;
    }
    
    public void setPoints(Point[] points){
        this.points = points;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PointsWithAdjacencyMatrix{");
        sb.append("adjacencyMatrix=").append(Arrays.deepToString(adjacencyMatrix));
        sb.append(", points=").append(Arrays.deepToString(points)).append('}');
        return sb.toString();
    }

    @Override
    public PointsWithAdjacencyMatrix clone(){
        int length = points.length;
        boolean[][] newAdjacencyMatrix = new boolean[length][length];
        for (int i =0; i < length; i++)
            for (int j =0; j < length; j++)
                newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
        
        Point[] newPoints = new Point[points.length];
        for (int i =0; i < length; i++)
            newPoints[i] = points[i];
        
        return new PointsWithAdjacencyMatrix(newPoints, newAdjacencyMatrix);
    }
}