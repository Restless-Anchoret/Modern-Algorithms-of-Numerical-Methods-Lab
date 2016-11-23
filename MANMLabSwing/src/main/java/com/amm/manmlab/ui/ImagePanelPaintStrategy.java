package com.amm.manmlab.ui;

import java.awt.Graphics2D;

@FunctionalInterface
public interface ImagePanelPaintStrategy {

    void paint(Graphics2D graphics2D, int width, int height);
    
}