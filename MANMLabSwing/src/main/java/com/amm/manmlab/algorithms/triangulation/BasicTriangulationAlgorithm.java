package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.controller.MainController;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import com.amm.manmlab.utils.primitives.PathPoint;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Алгоритм триангуляции методом вырезка-выемка
 */
public class BasicTriangulationAlgorithm implements Algorithm<PointsWithEdges, PointsWithEdges> {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
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

    private double scalarMul(double len1, double len2, double angle) {
        return len1 * len2 * Math.cos(angle);
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

    //вырезка
    private PathPoint cut(PathPoint pp) {
        PathPoint prev = pp.getPrev();
        PathPoint next = pp.getNext();
        edges.add(new Edge(prev.getIndex(), next.getIndex()));
        prev.setNext(next);
        next.setPrev(prev);
        pathLength--;
        return next;
    }

    // выемка
    private PathPoint groove(PathPoint pp) {
        Point vec1 = getVector(pp, pp.getPrev());
        Point vec2 = getVector(pp, pp.getNext());
        double len1 = module(vec1);
        double len2 = module(vec2);
        double len = (len1 + len2) / 2;
        double angle = angle(pp) / 2;
        double scalarMul1 = scalarMul(len1, len, angle);
        double scalarMul2 = scalarMul(len2, len, angle);

        double a1, a2, b1, b2;
        // составляем систему уравнений, надо избежать деления на 0
        // a1 не должно быть = 0
        // Сама система
        // a1*x + b1*y = scalarMul1
        // a2*x + b2*y = scalarMul2
        if (vec1.getX() != 0) {
            a1 = vec1.getX();
            b1 = vec1.getY();
            a2 = vec2.getX();
            b2 = vec2.getY();
        } else {
            a1 = vec2.getX();
            b1 = vec2.getY();
            a2 = vec1.getX();
            b2 = vec1.getY();
        }

        double y = (scalarMul1 * a2 - scalarMul2 * a1) / (b1 * a2 - b2 * a1);
        double x = (scalarMul1 - y * b1) / a1;

        // переводим координаты вектора в новую точку
        PathPoint npp = new PathPoint(pp.getX() + x, pp.getY() + y, points.size());
        // исключаем предыдущую точку из контура
        npp.setPrev(pp.getPrev());
        npp.setNext(pp.getNext());
        npp.getPrev().setNext(npp);
        npp.getNext().setPrev(npp);
        // добавляем новые связи и новую точку в коллекции
        points.add(npp.toPoint());
        pathPoints.add(npp); // для удобства последующего извлечения по индексу
        edges.add(new Edge(pp.getIndex(), npp.getIndex()));
        edges.add(new Edge(npp.getPrev().getIndex(), npp.getIndex()));
        edges.add(new Edge(npp.getIndex(), npp.getNext().getIndex()));

        return npp;
    }

    private PathPoint cutOrGroove(PathPoint pp) {
        double angle = Math.toDegrees(angle(pp));
        if (angle < 75) {
            return cut(pp); // вырезка
        } else if (angle > 89) {
            return groove(pp); // выемка
        } else {
            // вычисляем два другие угла треугольника
            double ang1 = angle(getVector(pp.getPrev(), pp), getVector(pp.getPrev(), pp.getNext()));
            ang1 = Math.toDegrees(Math.abs(ang1));
            double ang2 = angle(getVector(pp.getNext(), pp), getVector(pp.getNext(), pp.getPrev()));
            ang2 = Math.toDegrees(Math.abs(ang2));
            if (ang1 < 30 || ang2 < 30) {
                return groove(pp);
            } else {
                return cut(pp);
            }
        }
    }

    private PathPoint convertDataToPath() {
        pathPoints = new ArrayList<>(points.size());
        for (int i = 0; i < points.size(); i++) {
            pathPoints.add(new PathPoint(points.get(i), i));
        }
        int index = 0;
        PathPoint pp = pathPoints.get(index);
        pp.setPrev(pp); // для избежания ошибки на первом шаге
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
                LOG.info("Some points aren't linked with others! The path can't be created!");
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
        LOG.info("Triangulation (cut-groove) starts  ...");
        edges = data.getEdges();
        points = data.getPoints();
        PathPoint pp = convertDataToPath();
        clockwise = clockwiseTest(pp); // по часовой стрелке?
        pathLength = pathPoints.size();
        //showAngles(pp);
        while (pathLength > 3) { // пока не останется 1 треугольник
            int minIndex = findMinAngle(pp);
            pp = cutOrGroove(pathPoints.get(minIndex));
        }
        LOG.info("Triangulation (cut-groove) ended.");
        return new PointsWithEdges(edges, points);
    }
}
