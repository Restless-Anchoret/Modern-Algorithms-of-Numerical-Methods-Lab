package com.amm.manmlab.controller;

import com.amm.manmlab.ui.ImagePanel;
import com.amm.manmlab.ui.ImagePanelListener;
import com.amm.manmlab.utils.LabConstants;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import java.util.ArrayList;
import java.util.List;

public class SettingEdgeController implements ImagePanelListener {

    private PointsWithEdges pointsWithEdges;
    private int chosenPointIndex = -1;
    
    public SettingEdgeController() {
    }

    public SettingEdgeController(PointsWithEdges pointsWithEdges) {
        this.pointsWithEdges = pointsWithEdges;
    }
    
    @Override
    public void mouseLeftClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        int pointIndex = findPointIndex(x, y);
        if (chosenPointIndex == -1) {
            if (pointIndex == -1) {
                Point newPoint = new Point(x, y);
                pointsWithEdges.getPoints().add(newPoint);
            } else {
                chosenPointIndex = pointIndex;
            }
        } else {
            if (pointIndex == -1) {
                Point newPoint = new Point(x, y);
                pointsWithEdges.getPoints().add(newPoint);
                Edge newEdge = new Edge(chosenPointIndex, pointsWithEdges.getPoints().size() - 1);
                pointsWithEdges.getEdges().add(newEdge);
            } else {
                Edge newEdge = new Edge(chosenPointIndex, pointIndex);
                pointsWithEdges.getEdges().add(newEdge);
            }
            chosenPointIndex = -1;
        }
        imagePanel.repaint();
    }

    @Override
    public void mouseMiddleClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
    }

    @Override
    public void mouseRightClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        int pointIndex = findPointIndex(x, y);
        if (pointIndex != -1) {
            List<Edge> newEdges = new ArrayList<>();
            for (Edge edge: pointsWithEdges.getEdges()) {
                if (edge.getFirstIndex() != pointIndex && edge.getSecondIndex() != pointIndex) {
                    int newFirstIndex = (edge.getFirstIndex() > pointIndex ? edge.getFirstIndex() - 1 : edge.getFirstIndex());
                    int newSecondIndex = (edge.getSecondIndex()> pointIndex ? edge.getSecondIndex() - 1 : edge.getSecondIndex());
                    newEdges.add(new Edge(newFirstIndex, newSecondIndex));
                }
            }
            pointsWithEdges.getEdges().clear();
            pointsWithEdges.getEdges().addAll(newEdges);
            pointsWithEdges.getPoints().remove(pointIndex);
            imagePanel.repaint();
            return;
        }
        
        int edgeIndex = findEdgeIndex(x, y);
        if (edgeIndex != -1) {
            pointsWithEdges.getEdges().remove(edgeIndex);
            imagePanel.repaint();
        }
    }

    @Override
    public void mouseLeftDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        chosenPointIndex = -1;
        int pointIndex = findPointIndex(previousX, previousY);
        if (pointIndex == -1) {
            return;
        }
        pointsWithEdges.getPoints().get(pointIndex).moveTo(x, y);
        imagePanel.repaint();
    }

    @Override
    public void mouseMiddleDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
    }

    @Override
    public void mouseRightDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
    }

    @Override
    public void mouseMovedWithoutPressedButtons(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        // todo: make new edge displayed
    }
    
    private int findPointIndex(int x, int y) {
        for (int i = 0; i < pointsWithEdges.getPoints().size(); i++) {
            Point point = pointsWithEdges.getPoints().get(i);
            double diffX = x - point.getX();
            double diffY = y - point.getY();
            double dist = Math.sqrt(diffX * diffX + diffY * diffY);
            if (dist < LabConstants.VERTICE_CAPTURE_RADIUS) {
                return i;
            }
        }
        return -1;
    }

    private int findEdgeIndex(int x, int y) {
        // todo
        return -1;
    }
    
}