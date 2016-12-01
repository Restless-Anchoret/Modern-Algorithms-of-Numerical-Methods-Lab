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
    
    private Point getVector(Point begin, Point end) {
       return new Point(end.getX() - begin.getX(), end.getY() - begin.getY());
    }
    
    private double angle(Point a, Point b, Point c) {
        double baLen = distance(b, a);
        double bcLen = distance(b, c);
        Point ba = getVector(b, a);
        Point bc = getVector(b, c);
        double cos = scalarMul(bc, ba) / (baLen * bcLen);     
        return Math.acos(cos);
    }
    
    private boolean isCutting(Point a, Point b, Point c) {
        double angle = angle(a, b, c);
        if (angle <= 75) {
            return true; // вырезка
        } else if (angle >= 90) {
            return false; // выемка
        } else {
            // выемка, если один из углов маленький
            return !(angle(b, a, c) < 30 || angle(b, c, a) < 30);
        }
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
