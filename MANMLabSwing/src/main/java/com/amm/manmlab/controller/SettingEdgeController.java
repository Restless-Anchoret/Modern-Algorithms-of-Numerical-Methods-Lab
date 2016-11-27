package com.amm.manmlab.controller;

import com.amm.manmlab.ui.ImagePanel;
import com.amm.manmlab.ui.ImagePanelListener;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Point;

public class SettingEdgeController implements ImagePanelListener {

    private PointsWithEdges pointsWithEdges;
    private Point chosenPoint = null;
    
    public SettingEdgeController() {
    }

    public SettingEdgeController(PointsWithEdges pointsWithEdges) {
        this.pointsWithEdges = pointsWithEdges;
    }
    
    @Override
    public void mouseLeftClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
//        System.out.println("Left clicked");
    }

    @Override
    public void mouseMiddleClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
    }

    @Override
    public void mouseRightClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
//        System.out.println("Right clicked");
    }

    @Override
    public void mouseLeftDragged(ImagePanel imagePanel, int x, int y, int width, int height) {
//        System.out.println("Left dragged");
    }

    @Override
    public void mouseMiddleDragged(ImagePanel imagePanel, int x, int y, int width, int height) {
    }

    @Override
    public void mouseRightDragged(ImagePanel imagePanel, int x, int y, int width, int height) {
    }

    @Override
    public void mouseMovedWithoutPressedButtons(ImagePanel imagePanel, int x, int y, int width, int height) {
//        System.out.println("Moved");
    }

}