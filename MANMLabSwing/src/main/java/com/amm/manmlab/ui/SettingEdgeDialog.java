package com.amm.manmlab.ui;

import javax.swing.JPanel;

public class SettingEdgeDialog extends JPanel {

    public SettingEdgeDialog() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        cleanButton = new javax.swing.JButton();
        readFileButton = new javax.swing.JButton();
        autoTriangulationButton = new javax.swing.JButton();
        manualTriangulationButton = new javax.swing.JButton();

        headerLabel.setText("Установка границы");

        cleanButton.setText("Очистить поле");

        readFileButton.setText("Считать из файла");

        autoTriangulationButton.setText("Автоматическая триангуляция");

        manualTriangulationButton.setText("Ручная триангуляция");
        manualTriangulationButton.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(headerLabel)
                    .addComponent(autoTriangulationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cleanButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(readFileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manualTriangulationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(18, 18, 18)
                .addComponent(cleanButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(readFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(autoTriangulationButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manualTriangulationButton)
                .addContainerGap(114, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton autoTriangulationButton;
    private javax.swing.JButton cleanButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JButton manualTriangulationButton;
    private javax.swing.JButton readFileButton;
    // End of variables declaration//GEN-END:variables

}
