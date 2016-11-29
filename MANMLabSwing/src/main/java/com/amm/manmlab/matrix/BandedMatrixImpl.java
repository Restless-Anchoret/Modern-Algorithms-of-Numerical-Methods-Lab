package com.amm.manmlab.matrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BandedMatrixImpl implements BandedMatrix
{

    private static final Logger LOG = LoggerFactory.getLogger(BandedMatrixImpl.class);

    private final int rowOfMatrix;
    private final int bandOfMatrix;

    private final double[][] matrix;

    @Override
    public void setElement(int row, int col, double element)
    {
        if (row == col)
        {
            matrix[row][bandOfMatrix + 1] = element;
        }
        else
        {
            if (col > row) //Мы всегда работаем с нижней половиной матрицы
            {
                int tmp = col;
                col = row;
                row = tmp;
            }
            if ((row > rowOfMatrix + 1) && (col <= row - (bandOfMatrix + 1)))
            {
                LOG.error("Out of band");
            }
            else
            {
                matrix[row][row - col] = element;
            }
        }
    }

    @Override
    public double getElement(int row, int col)
    {
        if (row == col)
        {
            return matrix[row][bandOfMatrix + 1];
        }
        else
        {
            if (col > row) //Мы всегда работаем с нижней половиной матрицы
            {
                int tmp = col;
                col = row;
                row = tmp;
            }
            if ((row > rowOfMatrix + 1) && (col <= row - (bandOfMatrix + 1)))
            {
                LOG.error("Out of band");
                return 0;
            }

            return matrix[row][row - col];
        }
    }

    @Override
    public double[][] getFullMatrix()
    {
        return matrix;
    }

    BandedMatrixImpl(int rowOfMatrix, int bandOfMatrix)
    {
        this.rowOfMatrix = rowOfMatrix;
        this.bandOfMatrix = bandOfMatrix;
        this.matrix = new double[rowOfMatrix][bandOfMatrix + 1];
    }

}
