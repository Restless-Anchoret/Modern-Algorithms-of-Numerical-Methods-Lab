package com.amm.manmlab.controller;

import com.amm.manmlab.ui.MainFrame;

public class MainController {

    private MainFrame mainFrame;
    
    public void startApplication() {
        loadUI();
    }
    
    private void loadUI() {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
    
}