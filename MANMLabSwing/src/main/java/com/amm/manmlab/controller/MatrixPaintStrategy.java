package com.amm.manmlab.controller;

import com.amm.manmlab.ui.DialogPanel;
import com.amm.manmlab.ui.ImagePanelPaintStrategy;
import com.amm.manmlab.utils.LabConstants;
import com.amm.manmlab.utils.containers.PointsWithAdjacencyMatrix;
import com.amm.manmlab.utils.primitives.Point;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class MatrixPaintStrategy implements ImagePanelPaintStrategy {
    
    private PointsWithAdjacencyMatrix pointsWithMatrix;
    private Double[] borderConditions;
    private int chosenPointIndex = -1;
    private int cursorX;
    private int cursorY;
    private String currentDialog = "none";
    private boolean showInitial = false;
    
    public MatrixPaintStrategy(PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix){
        this.pointsWithMatrix = pointsWithAdjacencyMatrix;
    }
    
    
    public MatrixPaintStrategy(PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix, String dialog){
        this.pointsWithMatrix = pointsWithAdjacencyMatrix;
        this.currentDialog = dialog;
    }
    
    public MatrixPaintStrategy(PointsWithAdjacencyMatrix firstMatrix, 
            Double[] borders, String dialog, boolean show){
        this.pointsWithMatrix = firstMatrix;
        this.borderConditions = borders;
        this.currentDialog = dialog;
        this.showInitial = show;      
    }
    
    public Point[] getPointsMatrix() {
        return pointsWithMatrix.getPoints();
    }

    public void setPointsWithMatrix(Point[] pointsMatrix) {
        this.pointsWithMatrix.setPoints(pointsMatrix);
    }
    
    public void resetBorderCondition(int index){
        this.borderConditions[index*2] = null;
        this.borderConditions[index*2+1] = null;
    }
    
    public Double[] getBorderCondition(int index){
        return new Double[]{ this.borderConditions[index*2], this.borderConditions[index*2+1] };
    }
    
    public boolean conditionIsNull(int index){
        boolean res = true;
        if (borderConditions != null)
            res = borderConditions[2*index] == null & borderConditions[2*index+1] == null;
        return res;
    }
    
    public boolean conditionIsZero(int index){
        boolean res = false;
        if (borderConditions[2*index] != null)
            res = borderConditions[2*index] == 0. & borderConditions[2*index+1] == 0.;
        return res;
    }
    
    public void setBorderCondition(int index, Double x, Double y){
        this.borderConditions[index*2] = x;
        this.borderConditions[index*2+1] = y;
    }
    
    public int getChosenPointIndex() {
        return chosenPointIndex;
    }

    public void setChosenPointIndex(int chosenPointIndex) {
        this.chosenPointIndex = chosenPointIndex;
    }

    public int getCursorX() {
        return cursorX;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
    }
    
    @Override
    public void paint(Graphics2D graphics, int width, int height) { 
        
        int matDim = pointsWithMatrix.getPoints().length;
        
        for(int i=0; i<matDim; i++) {
            for(int j=i+1; j<matDim; j++) {
                if(pointsWithMatrix.getAdjacencyMatrix()[i][j])
                {
                    Point first = pointsWithMatrix.getPoints()[i];
                    Point second = pointsWithMatrix.getPoints()[j];
                                                       
                    switch(currentDialog) {
                        case DialogPanel.SETTING_EDGE_CONDITIONS_DIALOG:
                            if(conditionIsZero(i) & conditionIsZero(j)) {
                                graphics.setColor(Color.BLUE);
                                graphics.setStroke(new BasicStroke(3));
                                graphics.drawLine((int)first.getX(), (int)first.getY(),
                                    (int)second.getX(), (int)second.getY());
                                graphics.setStroke(new BasicStroke(1));
                                graphics.setColor(Color.BLACK);
                            }
                        case DialogPanel.TRIANGULATION_RESULT_DIALOG:
                            graphics.setColor(Color.BLACK);
                            graphics.drawLine((int)first.getX(), (int)first.getY(),
                                    (int)second.getX(), (int)second.getY());
                            break;
                        case DialogPanel.RESULT_DIALOG:
                            if(showInitial){
                                graphics.setColor(Color.RED);
                                graphics.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{6}, 0));

                                boolean bord = !conditionIsNull(i);
                                graphics.drawLine((int)first.getX(), (int)first.getY(), 
                                        (int)(first.getX() + (bord ? borderConditions[2*i] : 0 )), 
                                        (int)(first.getY() + (bord ? borderConditions[2*i+1] : 0 )));

                                graphics.setStroke(new BasicStroke(1));
                                
                                graphics.setColor(Color.GREEN);
                                graphics.drawLine((int)first.getX(), (int)first.getY(),
                                    (int)second.getX(), (int)second.getY());
                            }

                            graphics.setColor(Color.BLACK);
                            boolean bord1 = !conditionIsNull(i);
                            boolean bord2 = !conditionIsNull(j);
                            graphics.drawLine(
                                    (int)(first.getX()+ (bord1 ? borderConditions[2*i] : 0 )), 
                                    (int)(first.getY()+ (bord1 ? borderConditions[2*i+1] : 0 )),
                                    (int)(second.getX()+(bord2 ? borderConditions[2*j] : 0 )), 
                                    (int)(second.getY()+(bord2 ? borderConditions[2*j+1] : 0 )));
                            break;
                    }
                }
            }
        }
        
        int verticeRadius = LabConstants.VERTICE_SHOW_RADIUS;
        int verticeDiameter = verticeRadius * 2;
        for(int i=0; i<matDim; i++) {
            Point point = pointsWithMatrix.getPoints()[i];
            switch(currentDialog){
                case DialogPanel.TRIANGULATION_RESULT_DIALOG:
                    graphics.fillOval((int)point.getX() - verticeRadius, (int)point.getY() - verticeRadius,
                        verticeDiameter, verticeDiameter);
                    break;
                case DialogPanel.SETTING_EDGE_CONDITIONS_DIALOG:
                    int index = 2*i;
                    if (borderConditions[index] != null) {                   
                        if(conditionIsZero(i)) {
                            graphics.setColor(Color.BLUE);
                            graphics.fillOval((int)point.getX() - verticeRadius, (int)point.getY() - verticeRadius,
                                verticeDiameter, verticeDiameter);
                        } else {
                            graphics.fillOval((int)point.getX() - verticeRadius, (int)point.getY() - verticeRadius,
                                verticeDiameter, verticeDiameter);
                            graphics.setColor(Color.YELLOW);
                            graphics.drawLine((int)point.getX(), (int)point.getY(), 
                                    (int)(point.getX() + borderConditions[index]), 
                                    (int)(point.getY() + borderConditions[index+1]));
                            graphics.fillOval((int)(point.getX() + borderConditions[index]) - verticeRadius, 
                                              (int)(point.getY() + borderConditions[index+1]) - verticeRadius,
                                              verticeDiameter, verticeDiameter);    
                        }
                    } else { 
                        graphics.setColor(Color.BLACK);
                        graphics.fillOval((int)point.getX() - verticeRadius, (int)point.getY() - verticeRadius,
                                verticeDiameter, verticeDiameter);
                    }

                    if (chosenPointIndex != -1) {
                        graphics.setColor(Color.GRAY);
                        Point chosenPoint = pointsWithMatrix.getPoints()[chosenPointIndex];
                        int radius = LabConstants.VERTICE_CAPTURE_RADIUS;
                        graphics.drawOval((int)chosenPoint.getX() - radius, (int)chosenPoint.getY() - radius, 
                                          radius*2, radius*2);
                        graphics.drawLine(cursorX, cursorY, (int)chosenPoint.getX(), (int)chosenPoint.getY());
                    }
                    break;
                case DialogPanel.RESULT_DIALOG:
                    if (!conditionIsNull(i))
                        graphics.fillOval(
                            (int)(point.getX()+borderConditions[i*2]) - verticeRadius, 
                            (int)(point.getY()+borderConditions[i*2+1]) - verticeRadius,
                            verticeDiameter, verticeDiameter);
                    else
                        graphics.fillOval((int)point.getX() - verticeRadius, (int)point.getY() - verticeRadius,
                                verticeDiameter, verticeDiameter);
                    break;
            }
                            
            graphics.setColor(Color.BLACK);
            if (!currentDialog.equals(DialogPanel.RESULT_DIALOG)){
                graphics.drawString(String.valueOf(i), 
                        (int)point.getX() + 4, (int)point.getY() - 4);
            }
        }
    }
}
