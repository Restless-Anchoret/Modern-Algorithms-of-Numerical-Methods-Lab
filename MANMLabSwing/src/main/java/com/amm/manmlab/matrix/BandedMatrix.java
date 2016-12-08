package com.amm.manmlab.matrix;

public interface BandedMatrix
{

    void setElement(int row, int col, double element);

    double getElement(int row, int col);

    /**
     *
     * @return Возвращает всю матрицу в виде NxN.
     */
    double[][] getFullMatrix();

    int getRowSize();

    int getBandSize();
}
