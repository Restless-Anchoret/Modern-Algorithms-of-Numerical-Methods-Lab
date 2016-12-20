package com.amm.manmlab.utils.containers;

import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import java.util.ArrayList;

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
        StringBuilder sb = new StringBuilder("PointsWithEdges{");
        sb.append("edges=").append(edges);
        sb.append(", points=").append(points).append('}');
        return sb.toString();
    }

    @Override
    public PointsWithEdges clone() {
        List<Edge> newEdges = new ArrayList<>(edges.size());
        for (Edge edge: edges) {
            newEdges.add(edge);
        }
        List<Point> newPoints = new ArrayList<>(points.size());
        for (Point point: points) {
            newPoints.add(point);
        }
        return new PointsWithEdges(newEdges, newPoints);
    }

}
