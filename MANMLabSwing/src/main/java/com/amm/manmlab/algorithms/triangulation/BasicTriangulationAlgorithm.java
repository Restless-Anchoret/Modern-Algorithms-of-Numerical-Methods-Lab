package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import com.amm.manmlab.utils.primitives.PathPoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Алгоритм триангуляции методом вырезка-выемка
 */
public class BasicTriangulationAlgorithm implements Algorithm<PointsWithEdges, PointsWithEdges> {

    private List<Point> points;
    private List<Edge> edges;
    private List<PathPoint> pathPoints;
    private int pathLength;
    private boolean clockwise;

    private double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private double module(Point vec) {
        return Math.sqrt(vec.getX() * vec.getX() + vec.getY() * vec.getY());
    }

    private double scalarMul(Point a, Point b) {
        return a.getX() * b.getX() + a.getY() * b.getY();
    }

    private Point getVector(Point begin, Point end) {
        return new Point(end.getX() - begin.getX(), end.getY() - begin.getY());
    }

    // наименьший угол вращения между 2 векторами (направление вращения учитывается)
    private double angle(Point vec1, Point vec2) {
        double module1 = module(vec1);
        double module2 = module(vec2);
        double cos = scalarMul(vec1, vec2) / (module1 * module2);
        double det = det(vec1, vec2); // определяем направление вращения через векторное произведение
        return det < 0 ? -Math.acos(cos) : Math.acos(cos);
    }

    // внутренний угол многоугольника с учетом направления обхода
    private double angle(PathPoint pp) {
        double angle = angle(getVector(pp.getPrev(), pp), getVector(pp, pp.getNext()));
        return clockwise ? Math.PI + angle : Math.PI - angle;
    }

    // значение векторного произведения по сути
    private double det(Point a, Point b) {
        return a.getX() * b.getY() - b.getX() * a.getY();
    }

    private PathPoint cut(PathPoint pp) {
        PathPoint prev = pp.getPrev();
        PathPoint next = pp.getNext();
        edges.add(new Edge(prev.getIndex(), next.getIndex()));
        prev.setNext(next);
        next.setPrev(prev);
        pathLength--;
        return next;
    }

    private PathPoint groove(PathPoint pp) {
        return null;
    }

    private PathPoint cutOrGroove(PathPoint pp) {
        //double angle = angle(pp.getPrev(), pp, pp.getNext());
        return cut(pp);
//        if (angle <= 75) {
//            return cut(pp); // вырезка
//        } else if (angle >= 90) {
//            return groove(pp); // выемка
//        } else if (angle(pp, pp.getPrev(), pp.getNext()) < 30
//                || angle(pp, pp.getNext(), pp.getPrev()) < 30) {
//            // выемка, если один из углов маленький
//            return groove(pp);
//        } else {
//            return cut(pp);
//        }
    }

    private PathPoint convertDataToPath() {
        pathPoints = new ArrayList<>(points.size());
        for (int i = 0; i < points.size(); i++) {
            pathPoints.add(new PathPoint(points.get(i), i));
        }
        int index = 0;
        PathPoint pp = pathPoints.get(index);
        pp.setPrev(pp); // для избежания ошибки
        PathPoint fpp = pp;
        do {
            int nextIndex = -1;
            for (Edge edge : edges) {
                if (edge.getFirstIndex() == index && edge.getSecondIndex() != pp.getPrev().getIndex()) {
                    nextIndex = edge.getSecondIndex();
                } else if (edge.getSecondIndex() == index && edge.getFirstIndex() != pp.getPrev().getIndex()) {
                    nextIndex = edge.getFirstIndex();
                }
                if (nextIndex != -1) {
                    index = nextIndex;
                    PathPoint npp = pathPoints.get(index);
                    npp.setPrev(pp);
                    pp.setNext(npp);
                    pp = npp;
                    break;
                }
            }
            if (nextIndex == -1) {
                System.out.println("Не все точки соединены! Невозможно построить путь!");
                break;
            }
        } while (fpp != pp);

        return fpp;
    }

    public int findMinAngle(PathPoint pp) {
        PathPoint fpp = pp;
        double minAngle = 1000;
        int index = pp.getIndex();
        do {
            double angle = angle(pp);
            if (angle < minAngle) {
                index = pp.getIndex();
                minAngle = angle;
            }
            pp = pp.getNext();
        } while (pp != fpp);
        //System.err.printf("Min angle %d - %f \n", index, minAngle);
        return index;
    }

    // отладочный метод для просмотра всех внутренних углов многоугольника
    private void showAngles(PathPoint pp) {
        PathPoint fpp = pp;
        do {
            System.out.println("**********");
            System.out.println(pp.getIndex());
            System.out.println(pp.toString());
            System.out.println(angle(pp));
            pp = pp.getNext();
        } while (fpp != pp);
        System.out.println("**********");
    }

    // тест на направление обхода
    private boolean clockwiseTest(PathPoint pp) {
        Point basePoint = new Point(
                (pp.getPrev().getX() + pp.getX()) / 2,
                (pp.getPrev().getY() + pp.getY()) / 2
        );
        PathPoint lastPoint = pp.getPrev();
        double angleSum = 0;
        while (pp != lastPoint) {
            angleSum += angle(getVector(basePoint, pp), getVector(basePoint, pp.getNext()));
            pp = pp.getNext();
        }
        return angleSum < 0;
    }

    @Override
    public PointsWithEdges doAlgorithm(PointsWithEdges data) {
        System.err.println("Триангуляция началась ...");
        edges = data.getEdges();
        points = data.getPoints();
        PathPoint pp = convertDataToPath();
        clockwise = clockwiseTest(pp);
        pathLength = pathPoints.size();
        //showAngles(pp);
        while (pathLength > 3) {
            int minIndex = findMinAngle(pp);
            pp = cutOrGroove(pathPoints.get(minIndex));
        }
        System.err.println("Триангуляция закончилась ...");
        return new PointsWithEdges(edges, points);
    }
}
