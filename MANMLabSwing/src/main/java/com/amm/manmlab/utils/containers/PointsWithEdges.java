package com.amm.manmlab.utils.containers;

import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;

import java.util.List;

public class PointsWithEdges {

    private List<Edge> edges;
    private List<Point> points;

    public PointsWithEdges(List<Edge> edges, List<Point> points) {
        this.edges = edges;
        this.points = points;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Point> getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "PointsWithEdges{" + "edges=" + edges + ", points=" + points + '}';
    }

}
