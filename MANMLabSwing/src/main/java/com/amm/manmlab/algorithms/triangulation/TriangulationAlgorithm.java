package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import java.util.List;

public class TriangulationAlgorithm implements Algorithm<PointsWithEdges, PointsWithEdges> {

    private double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private double scalarMul(Point a, Point b) {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }

    //метод выемка вырезка  
    private PointsWithEdges triangulation(PointsWithEdges data) {
        List<Edge> edges = data.getEdges();
        List<Point> points = data.getPoints();
        return data.clone();
    }

    @Override
    public PointsWithEdges doAlgorithm(PointsWithEdges screenObjects) {
        return starCentering(triangulation(screenObjects));
    }

    //центрирование звёзд
    private PointsWithEdges starCentering(PointsWithEdges data) {
        // Тут надо написать код
        return data.clone();
    }
}
