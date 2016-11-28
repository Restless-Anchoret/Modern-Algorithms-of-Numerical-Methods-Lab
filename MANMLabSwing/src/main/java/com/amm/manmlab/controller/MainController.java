package com.amm.manmlab.controller;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.fileinput.FileInputLoader;
import com.amm.manmlab.ui.MainFrame;
import com.amm.manmlab.utils.containers.FiniteElementMethodInput;
import com.amm.manmlab.utils.containers.PointsWithAdjacencyMatrix;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import java.util.Collections;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {

    private FileInputLoader fileInputLoader;
    private Algorithm<PointsWithEdges, PointsWithEdges> triangulationAlgorithm;
    private Algorithm<PointsWithAdjacencyMatrix, PointsWithAdjacencyMatrix> renumberingAlgorithm;
    private Algorithm<FiniteElementMethodInput, Double[]> finiteElementMethodAlgorithm;
    private MainFrame mainFrame;
    
    private PointsWithEdges initialPointsWithEdges = new PointsWithEdges(Collections.EMPTY_LIST, Collections.EMPTY_LIST);

    public MainController(FileInputLoader fileInputLoader,
            Algorithm<PointsWithEdges, PointsWithEdges> triangulationAlgorithm,
            Algorithm<PointsWithAdjacencyMatrix, PointsWithAdjacencyMatrix> renumberingAlgorithm,
            Algorithm<FiniteElementMethodInput, Double[]> finiteElementMethodAlgorithm) {
        this.fileInputLoader = fileInputLoader;
        this.triangulationAlgorithm = triangulationAlgorithm;
        this.renumberingAlgorithm = renumberingAlgorithm;
        this.finiteElementMethodAlgorithm = finiteElementMethodAlgorithm;
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
        SimplePaintStrategy paintStrategy = new SimplePaintStrategy(initialPointsWithEdges);
        mainFrame.getImagePanel().addImagePanelListener(new SettingEdgeController(paintStrategy));
        mainFrame.getImagePanel().setImagePanelPaintStrategy(paintStrategy);
    }

}