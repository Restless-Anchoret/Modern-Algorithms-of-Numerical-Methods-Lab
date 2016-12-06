package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.matrix.BandedMatrix;
import com.amm.manmlab.matrix.BandedMatrixImpl;
import com.amm.manmlab.splittingintoelements.SplittingIntoElements;
import com.amm.manmlab.utils.primitives.Element;
import com.amm.manmlab.utils.containers.FiniteElementMethodInput;
import com.amm.manmlab.utils.containers.PointsWithAdjacencyMatrix;
import com.amm.manmlab.utils.primitives.Point;
import java.util.Arrays;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiniteElementMethodAlgorithm implements Algorithm<FiniteElementMethodInput, Double[]> {

    private static final Logger LOG = LoggerFactory.getLogger(FiniteElementMethodAlgorithm.class);
    
    @Override
    public Double[] doAlgorithm(FiniteElementMethodInput finiteElementMethodInput)
    {
        LOG.debug("borderConditions = {}", Arrays.toString(finiteElementMethodInput.getBorderConditions()));
        SplittingIntoElements splittingIntoElements = new SplittingIntoElements();
        List<Element> elements = splittingIntoElements.splitting(finiteElementMethodInput.getPointsWithAdjacencyMatrix());
        LOG.debug("elements = {}", elements);
        
        //Антон -
        // input :
        //  finiteElementMethodInput.pointsWithAdjacencyMatrix.points
        //  elements
        //  finiteElementMethodInput.poissonsRatio
        //  finiteElementMethodInput.youngsModulus
        // output :
        //  общая матрица. Класс для неё напишет Лёша.

        BandedMatrix bandedMatrix = doMagic(finiteElementMethodInput, elements);
        LOG.debug("bandedMatrix = {}", Arrays.deepToString(bandedMatrix.getFullMatrix()));
        LOG.debug("borderConditions = {}", finiteElementMethodInput.getBorderConditions());
        LOG.debug("bandedMatrix rowSize = {}", bandedMatrix.getRowSize());
        
        BorderConditionProcessorInput borderConditionProcessorInput = new BorderConditionProcessorInput(
                bandedMatrix, finiteElementMethodInput.getBorderConditions());
        
        BorderConditionsProcessor borderConditionsProcessor = new BorderConditionsProcessor();
        BorderConditionsProcesorOutput borderConditionsProcesorOutput = borderConditionsProcessor.doAlgorithm(borderConditionProcessorInput);
        LOG.debug("result banded matrix = {}", borderConditionsProcesorOutput);
        
        LOG.debug("result bandedMatrix = {}", Arrays.deepToString(borderConditionsProcesorOutput.getProcessedMatrix().getFullMatrix()));
        LOG.debug("result borderConditions = {}", borderConditionsProcesorOutput.getRightHandSide());
        
        //Саша П.-
        // input:
        //   общая матрица. Класс для неё напишет Лёша.
        //   finiteElementMethodInput.borderConditions - Double[] - предположительно, null, где не заданы условия
        // output:
        //   общая матрица (Класс для неё напишет Лёша) с учётом граничных условий,
        //   Double[] изменённая правая часть. - вектор заполнен полностью

        //Андрей
        // input:
        //   общая матрица(Класс для неё напишет Лёша) с учётом граничных условий,
        //   изменённая правая часть. Double[] - вектор заполнен полностью
        // output:
        //   Double[] - вектор решения.

        return null;
    }
    
    public BandedMatrix doMagic(FiniteElementMethodInput input, List<Element> elements) {
        double poissonsRatio = input.getPoissonsRatio();
        double youngsModulus = input.getYoungsModulus();

        PointsWithAdjacencyMatrix pts = input.getPointsWithAdjacencyMatrix();

        double coeff = youngsModulus / (1 - poissonsRatio * poissonsRatio);

        double[][] D = new double[3][3];
        D[0][2] = D[1][2] = D[2][0] = D[2][1] = 0;
        D[0][0] = D[1][1] = 1;
        D[0][1] = D[1][0] = poissonsRatio;
        D[2][2] = (1 - poissonsRatio) / 2;

        D = scale(D, coeff);

        BandedMatrix bm = new BandedMatrixImpl(pts.getPoints().length * 2, pts.getPoints().length * 2);
        
        for (Element el : elements) {
            Point i = pts.getPoints()[el.getI()];
            Point j = pts.getPoints()[el.getJ()];
            Point k = pts.getPoints()[el.getK()];

            double S = Math.abs(i.getX() * (j.getY() - k.getY()) + j.getX() * (k.getY() - i.getY()) + k.getX() * (i.getY() - j.getY())) / 2;

            double[][] B = new double[3][6];
            B[0][1] = B[0][3] = B[0][5] = B[1][0] = B[1][2] = B[1][4] = 0;
            B[0][0] = B[2][1] = j.getY() - k.getY();
            B[0][2] = B[2][3] = k.getY() - i.getY();
            B[0][4] = B[2][5] = i.getY() - j.getY();
            B[1][1] = B[2][0] = k.getX() - j.getX();
            B[1][3] = B[2][2] = i.getX() - k.getX();
            B[1][5] = B[2][4] = j.getX() - i.getX();
            B = scale(B, 1 / (2 * S));

            double[][] K = scale(mul(transpose(B), mul(D, B)), S);

            
            move(0, 0, el.getI(), el.getI(), K, bm);
            move(0, 2, el.getI(), el.getJ(), K, bm);
            move(0, 4, el.getI(), el.getK(), K, bm);
            move(2, 0, el.getJ(), el.getI(), K, bm);
            move(2, 2, el.getJ(), el.getJ(), K, bm);
            move(2, 4, el.getJ(), el.getK(), K, bm);
            move(4, 0, el.getK(), el.getI(), K, bm);
            move(4, 2, el.getK(), el.getJ(), K, bm);
            move(4, 4, el.getK(), el.getK(), K, bm);
        }

        return bm;
    }

    public static double[][] scale(double[][] a, double coeff) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] *= coeff;
            }
        }

        return a;
    }

    public static void move(int x, int y, int i, int j, double[][] a, BandedMatrix bm) {
        if (2 * i < 2 * j) {
            bm.setElement(2 * i, 2 * j, bm.getElement(2 * i, 2 * j) + a[x][y]);
        }
        if (2 * i + 1 < 2 * j) {
            bm.setElement(2 * i, 2 * j, bm.getElement(2 * i + 1, 2 * j) + a[x + 1][y]);
        }
        if (2 * i < 2 * j + 1) {
            bm.setElement(2 * i, 2 * j, bm.getElement(2 * i, 2 * j + 1) + a[x][y + 1]);
        }
        if (2 * i + 1 < 2 * j + 1) {
            bm.setElement(2 * i + 1, 2 * j + 1, bm.getElement(2 * i + 1, 2 * j + 1) + a[x + 1][y + 1]);
        }

    }

    public static double[][] transpose(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                temp[j][i] = m[i][j];
            }
        }
        return temp;
    }

    public static double[][] mul(double[][] A, double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }
    
}
