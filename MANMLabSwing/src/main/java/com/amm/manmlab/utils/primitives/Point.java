package com.amm.manmlab.utils.primitives;

import java.util.Objects;

public class Point {

    private static final double EPSILON = 1e-8;
    
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Math.abs(this.x - point.x) < EPSILON &&
                Math.abs(this.y - point.y) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Point{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Point clone() {
        return new Point(x, y);
    }
    
}
