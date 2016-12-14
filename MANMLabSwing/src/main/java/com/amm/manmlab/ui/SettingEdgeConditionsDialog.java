package com.amm.manmlab.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingEdgeConditionsDialog extends JPanel {

    public SettingEdgeConditionsDialog() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        setConditionsButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ratioSpinner = new javax.swing.JSpinner();
        modulusSpinner = new javax.swing.JSpinner();

        headerLabel.setText("Установка граничных условий");

        setConditionsButton.setText("Выполнить");
        setConditionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setConditionsButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Коэф. Пуассона");
        jLabel1.setToolTipText("");

        jLabel2.setText("Модуль Юнга");

        ratioSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.2f), Float.valueOf(0.0f), Float.valueOf(0.999999f), Float.valueOf(0.1f)));

        modulusSpinner.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.1f), Float.valueOf(0.0f), Float.valueOf(9999999.0f), Float.valueOf(0.1f)));
        modulusSpinner.setName(""); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(headerLabel)
                    .addComponent(setConditionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(ratioSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(modulusSpinner)
                            .addComponent(jLabel2))))
                .addContainerGap(186, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ratioSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modulusSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(setConditionsButton)
                .addContainerGap(189, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setConditionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setConditionsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_setConditionsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner modulusSpinner;
    private javax.swing.JSpinner ratioSpinner;
    private javax.swing.JButton setConditionsButton;
    // End of variables declaration//GEN-END:variables

    
    public JButton getSetConditionsButton() {
        return setConditionsButton;
    }
    
    public Double getFiniteCoeff(int ind){
        return ind == 1 ? new Double(ratioSpinner.getValue().toString()) : 
                          new Double(modulusSpinner.getValue().toString());
    }
}
