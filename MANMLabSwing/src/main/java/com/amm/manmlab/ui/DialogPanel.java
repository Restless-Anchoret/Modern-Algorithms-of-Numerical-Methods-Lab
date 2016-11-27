package com.amm.manmlab.ui;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class DialogPanel extends JPanel {

    public static final String SETTING_EDGE_DIALOG = "setting_edge_dialog";
    public static final String MANUAL_TRIANGULATION_DIALOG = "manual_triangulation_dialog";
    public static final String TRIANGULATION_RESULT_DIALOG = "triangulation_result_dialog";
    public static final String SETTING_EDGE_CONDITIONS_DIALOG = "setting_edge_conditions_dialog";
    public static final String RESULT_DIALOG = "result_dialog";
    
    private SettingEdgeDialog settingEdgeDialog;
    private ManualTriangulationDialog manualTriangulationDialog;
    private TriangulationResultDialog triangulationResultDialog;
    private SettingEdgeConditionsDialog settingEdgeConditionsDialog;
    private ResultDialog resultDialog;
    
    private CardLayout layout;
    private String currentDialogIdentifier;
    
    public DialogPanel() {
        initCustomComponents();
    }

    private void initCustomComponents() {
        layout = new CardLayout();
        setLayout(layout);
        
        settingEdgeDialog = new SettingEdgeDialog();
        manualTriangulationDialog = new ManualTriangulationDialog();
        triangulationResultDialog = new TriangulationResultDialog();
        settingEdgeConditionsDialog = new SettingEdgeConditionsDialog();
        resultDialog = new ResultDialog();
        
        add(settingEdgeDialog, SETTING_EDGE_DIALOG);
        add(manualTriangulationDialog, MANUAL_TRIANGULATION_DIALOG);
        add(triangulationResultDialog, TRIANGULATION_RESULT_DIALOG);
        add(settingEdgeConditionsDialog, SETTING_EDGE_CONDITIONS_DIALOG);
        add(resultDialog, RESULT_DIALOG);
        
        setCurrentDialog(SETTING_EDGE_DIALOG);
    }

    public SettingEdgeDialog getSettingEdgeDialog() {
        return settingEdgeDialog;
    }

    public ManualTriangulationDialog getManualTriangulationDialog() {
        return manualTriangulationDialog;
    }

    public TriangulationResultDialog getTriangulationResultDialog() {
        return triangulationResultDialog;
    }

    public SettingEdgeConditionsDialog getSettingEdgeConditionsDialog() {
        return settingEdgeConditionsDialog;
    }

    public ResultDialog getResultDialog() {
        return resultDialog;
    }

    public String getCurrentDialogIdentifier() {
        return currentDialogIdentifier;
    }

    public void setCurrentDialog(String currentDialogIdentifier) {
        this.currentDialogIdentifier = currentDialogIdentifier;
        layout.show(this, currentDialogIdentifier);
    }
    
}