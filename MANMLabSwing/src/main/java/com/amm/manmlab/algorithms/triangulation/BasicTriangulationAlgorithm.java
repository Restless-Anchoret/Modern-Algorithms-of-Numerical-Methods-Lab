package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import com.amm.manmlab.utils.primitives.PathPoint;
import java.util.List;


/**
 * Алгоритм триангуляции методом вырезка-выемка
 */
public class BasicTriangulationAlgorithm implements Algorithm<PointsWithEdges, PointsWithEdges> {
    
    private List<Point> points;
    private List<Edge> edges;
    
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
    
    private double det(Point a, Point b) {
        return a.getX()*b.getY() - b.getX()*a.getY();
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
    
    private PathPoint convertDataToPath() {
        int index = 0;
        Point p = points.get(index);
        PathPoint pp = new PathPoint(p, index);
        PathPoint fpp = pp;
        do {
           for(Edge edge: edges) {
               if(edge.getFirstIndex() == index) {
                   PathPoint npp = new PathPoint(points.get(edge.getSecondIndex()), edge.getSecondIndex());
                   npp.setPrev(pp);
                   pp.setNext(npp);
                   pp = npp;
                   break;
               }
           }
        } while (fpp != pp);

        return fpp;
    }

    @Override
    public PointsWithEdges doAlgorithm(PointsWithEdges data) {
        /*edges = data.getEdges();
        points = data.getPoints();
        PathPoint pp = convertDataToPath();*/
        return data.clone();
    }
}
