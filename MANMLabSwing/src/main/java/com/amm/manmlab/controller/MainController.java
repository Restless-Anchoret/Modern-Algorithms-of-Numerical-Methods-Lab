package com.amm.manmlab.controller;

import com.amm.manmlab.utils.fileinput.FileInputLoader;
import com.amm.manmlab.ui.MainFrame;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import java.util.Collections;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {

    private FileInputLoader fileInputLoader;
    private MainFrame mainFrame;
    
    private PointsWithEdges initialPointsWithEdges = new PointsWithEdges(Collections.EMPTY_LIST, Collections.EMPTY_LIST);

    public MainController(FileInputLoader fileInputLoader) {
        this.fileInputLoader = fileInputLoader;
    }
    
    public void startApplication() {
        loadData();
        loadUI();
        initListeners();
        mainFrame.setVisible(true);
    }

    private void loadData() {
        initialPointsWithEdges = fileInputLoader.loadInputFromFile();
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
        mainFrame.getImagePanel().addImagePanelListener(new SettingEdgeController());
        mainFrame.getImagePanel().setImagePanelPaintStrategy(new SimplePaintStrategy(initialPointsWithEdges));
    }

}