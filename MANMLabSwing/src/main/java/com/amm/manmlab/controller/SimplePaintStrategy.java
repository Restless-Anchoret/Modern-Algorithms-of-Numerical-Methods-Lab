package com.amm.manmlab.controller;

import com.amm.manmlab.ui.ImagePanelPaintStrategy;
import com.amm.manmlab.utils.LabConstants;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import java.awt.Color;
import java.awt.Graphics2D;

public class SimplePaintStrategy implements ImagePanelPaintStrategy {

    private PointsWithEdges pointsWithEdges;
    private int chosenPointIndex = -1;
    private int cursorX;
    private int cursorY;

    public SimplePaintStrategy(PointsWithEdges pointsWithEdges) {
        this.pointsWithEdges = pointsWithEdges;
    }

    public PointsWithEdges getPointsWithEdges() {
        return pointsWithEdges;
    }

    public void setPointsWithEdges(PointsWithEdges pointsWithEdges) {
        this.pointsWithEdges = pointsWithEdges;
    }

    public int getChosenPointIndex() {
        return chosenPointIndex;
    }

    public void setChosenPointIndex(int chosenPointIndex) {
        this.chosenPointIndex = chosenPointIndex;
    }

    public int getCursorX() {
        return cursorX;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
    }

    @Override
    public void paint(Graphics2D graphics, int width, int height) {
        graphics.setColor(Color.BLACK);
        for (Edge edge: pointsWithEdges.getEdges()) {
            Point first = pointsWithEdges.getPoints().get(edge.getFirstIndex());
            Point second = pointsWithEdges.getPoints().get(edge.getSecondIndex());
            graphics.drawLine((int)first.getX(), (int)first.getY(),
                    (int)second.getX(), (int)second.getY());
        }
        if (chosenPointIndex != -1) {
            Point chosenPoint = pointsWithEdges.getPoints().get(chosenPointIndex);
            graphics.drawLine(cursorX, cursorY, (int)chosenPoint.getX(), (int)chosenPoint.getY());
        }
        int verticeRadius = LabConstants.VERTICE_SHOW_RADIUS;
        int verticeDiameter = verticeRadius * 2;
        for (Point point: pointsWithEdges.getPoints()) {
            graphics.fillOval((int)point.getX() - verticeRadius, (int)point.getY() - verticeRadius,
                    verticeDiameter, verticeDiameter);
        }
    }
    
}