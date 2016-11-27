package com.amm.manmlab.utils.containers;

import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;

import java.util.LinkedList;
import java.util.List;

public class PointsWithEdges {

    public List<Edge> edges;
    public List<Point> points;

    public PointsWithEdges() {
        this.edges = new LinkedList<>();
        this.points = new LinkedList<>();
    }

}
