package com.amm.manmlab.matrix;

public interface BandedMatrix
{

    public void setElement(int row, int col, double element);

    public double getElement(int row, int col);

    public double[][] getFullMatrix();
}
