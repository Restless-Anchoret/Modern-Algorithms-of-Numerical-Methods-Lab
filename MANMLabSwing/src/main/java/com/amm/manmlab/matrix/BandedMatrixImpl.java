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
            matrix[row][bandOfMatrix] = element;
        }
        else
        {
            if (col > row) //Мы всегда работаем с нижней половиной матрицы
            {
                int tmp = col;
                col = row;
                row = tmp;
            }
            if ((row >= bandOfMatrix + 1) && (col <= row - (bandOfMatrix + 1)))
            {
                LOG.error("Out of band");
            }
            else
            {
                matrix[row][row - col - 1] = element;
            }
        }
    }

    @Override
    public double getElement(int row, int col)
    {
        if (row == col)
        {
            return matrix[row][bandOfMatrix];
        }
        else
        {
            if (col > row) //Мы всегда работаем с нижней половиной матрицы
            {
                int tmp = col;
                col = row;
                row = tmp;
            }
            if ((row >= bandOfMatrix + 1) && (col <= row - (bandOfMatrix + 1)))
            {
                LOG.error("Out of band");
                return 0;
            }

            return matrix[row][row - col - 1];
        }
    }

    @Override
    public double[][] getFullMatrix()
    {
        double[][] fullMatrix = new double[rowOfMatrix][rowOfMatrix];
        for (int i = 0; i < rowOfMatrix; i++)
        {
            for (int j = 0; j < rowOfMatrix; j++)
            {
                fullMatrix[i][j] = getElement(i, j);
            }

        }
        return fullMatrix;
    }

    @Override
    public int getRowSize() {
        return rowOfMatrix;
    }

    @Override
    public int getBandSize() {
        return bandOfMatrix;
    }

    /**
     *
     * @param rowOfMatrix Размерность матрицы.
     * @param bandOfMatrix Количество побочных диагоналей в нижней половине
     * матрицы.(Без учёта главной диагонали!).
     */
    public BandedMatrixImpl(int rowOfMatrix, int bandOfMatrix)
    {
        this.rowOfMatrix = rowOfMatrix;
        this.bandOfMatrix = bandOfMatrix;
        this.matrix = new double[rowOfMatrix][bandOfMatrix + 1];
    }

}
