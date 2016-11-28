package com.amm.manmlab.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TriangulationResultDialog extends JPanel {

    public TriangulationResultDialog() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        renumberingButton = new javax.swing.JButton();

        headerLabel.setText("Результат триангуляции");

        renumberingButton.setText("Выполнить перенумеровку");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerLabel)
                    .addComponent(renumberingButton))
                .addContainerGap(206, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(18, 18, 18)
                .addComponent(renumberingButton)
                .addContainerGap(228, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel headerLabel;
    private javax.swing.JButton renumberingButton;
    // End of variables declaration//GEN-END:variables

    public JButton getRenumberingButton() {
        return renumberingButton;
    }

}
