package com.amm.manmlab.controller;

import com.amm.manmlab.controller.MatrixPaintStrategy;
import com.amm.manmlab.ui.ImagePanel;
import com.amm.manmlab.ui.ImagePanelListener;
import com.amm.manmlab.utils.LabConstants;
import com.amm.manmlab.utils.primitives.Point;

public class SettingConditionController implements ImagePanelListener {
    
    private MatrixPaintStrategy paintStrategy;
    
    public SettingConditionController(MatrixPaintStrategy paintStrategy) {
        this.paintStrategy = paintStrategy;
    }

    @Override
    public void mouseLeftClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        Point[] points = paintStrategy.getPointsMatrix();
        int chosenPointIndex = paintStrategy.getChosenPointIndex();
        int pointIndex = findPointIndex(x, y);
        if (chosenPointIndex == -1) {        
            //if(paintStrategy.equalPointExists(pointIndex))
                if (pointIndex == -1) {
                    
                } else {
                    paintStrategy.setChosenPointIndex(pointIndex);
                }
        } else {
            if (pointIndex == -1) {
                paintStrategy.setBorderCondition(chosenPointIndex, 
                        x - points[chosenPointIndex].getX(), 
                        y - points[chosenPointIndex].getY());
            } else {
                paintStrategy.setBorderCondition(chosenPointIndex, 0., 0.);
            }
            paintStrategy.setChosenPointIndex(-1);
        }
        imagePanel.repaint();
    }

    @Override
    public void mouseMiddleClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
    }

    @Override
    public void mouseRightClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        paintStrategy.setChosenPointIndex(-1);
        int pointIndex = findPointIndex(x, y);
        if (pointIndex != -1) {
            paintStrategy.resetBorderCondition(pointIndex);
        }
        imagePanel.repaint();
    }

    @Override
    public void mouseLeftDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) { }

    @Override
    public void mouseMiddleDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) { }

    @Override
    public void mouseRightDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) { }

    @Override
    public void mouseMovedWithoutPressedButtons(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        paintStrategy.setCursorX(x);
        paintStrategy.setCursorY(y);
        imagePanel.repaint();
    }
    
    private int findPointIndex(int x, int y) {
        Point[] points = paintStrategy.getPointsMatrix();
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            double diffX = x - point.getX();
            double diffY = y - point.getY();
            double dist = Math.sqrt(diffX * diffX + diffY * diffY);
            if (dist < LabConstants.VERTICE_CAPTURE_RADIUS) {
                return i;
            }
        }
        return -1;
    }
}
