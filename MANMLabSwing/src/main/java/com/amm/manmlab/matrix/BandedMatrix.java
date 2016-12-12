package com.amm.manmlab.matrix;

public interface BandedMatrix
{

    void setElement(int row, int col, double element);

    void addValueToElement(int row, int col, double value);

    double getElement(int row, int col);

    /**
     *
     * @return Возвращает всю матрицу в виде NxN.
     */
    double[][] getFullMatrix();

    int getRowSize();

    int getBandSize();
    
    BandedMatrix clone();
}
