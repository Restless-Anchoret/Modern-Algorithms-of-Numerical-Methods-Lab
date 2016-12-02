package com.amm.manmlab.utils.primitives;

import com.amm.manmlab.utils.primitives.Point;

/**
 * Точка со ссылками на предыдущую и последующую
 */
public class PathPoint extends Point {

    private int index = -1;
    private PathPoint next;
    private PathPoint prev;

    public PathPoint(double x, double y, int index) {
        super(x, y);
        this.index = index;
    }
    
    public PathPoint(Point p, int index) {
        super(p.getX(), p.getY());
        this.index = index;
    }
    
    public void setPrev(PathPoint prev) {
        this.prev = prev;
    }

    public PathPoint getPrev() {
        return prev;
    }

    public void setNext(PathPoint next) {
        this.next = next;
    }

    public PathPoint getNext() {
        return next;
    }

    public int getIndex() {
        return index;
    }
}
