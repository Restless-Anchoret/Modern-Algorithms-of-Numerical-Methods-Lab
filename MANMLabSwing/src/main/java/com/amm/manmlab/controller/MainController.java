package com.amm.manmlab.controller;

import com.amm.manmlab.ui.MainFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {

    private MainFrame mainFrame;

    public MainController() {
        
    }
    
    public void startApplication() {
        loadUI();
        initListeners();
        initData();
        mainFrame.setVisible(true);
    }

    private void loadUI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
        mainFrame = new MainFrame();
    }
    
    private void initListeners() {
        
    }
    
    private void initData() {
        
    }

}
