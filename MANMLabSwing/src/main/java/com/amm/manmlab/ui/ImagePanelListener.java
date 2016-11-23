package com.amm.manmlab.ui;

public interface ImagePanelListener {
    
    void mouseLeftClicked(ImagePanel imagePanel, int x, int y, int width, int height);
    void mouseMiddleClicked(ImagePanel imagePanel, int x, int y, int width, int height);
    void mouseRightClicked(ImagePanel imagePanel, int x, int y, int width, int height);

}