package com.amm.manmlab.matrix;

public interface BandedMatrix
{

    void setElement(int row, int col, double element);

    double getElement(int row, int col);

    double[][] getFullMatrix();

    int getRowSize();

    int getBand();
}
