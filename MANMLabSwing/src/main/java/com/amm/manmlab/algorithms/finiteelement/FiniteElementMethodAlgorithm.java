package com.amm.manmlab.algorithms.finiteelement;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.splittingintoelements.SplittingIntoElements;
import com.amm.manmlab.utils.primitives.Element;
import com.amm.manmlab.utils.containers.FiniteElementMethodInput;

import java.util.List;

public class FiniteElementMethodAlgorithm implements Algorithm<FiniteElementMethodInput, Double[]> {

    @Override
    public Double[] doAlgorithm(FiniteElementMethodInput finiteElementMethodInput)
    {
        SplittingIntoElements splittingIntoElements = new SplittingIntoElements();
        List<Element> elements = splittingIntoElements.splitting(finiteElementMethodInput.getPointsWithAdjacencyMatrix());
        //Антон -
        // input :
        //  finiteElementMethodInput.pointsWithAdjacencyMatrix.points
        //  elements
        //  finiteElementMethodInput.poissonsRatio
        //  finiteElementMethodInput.youngsModulus
        // output :
        //  общая матрица. Класс для неё напишет Лёша.

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
}
