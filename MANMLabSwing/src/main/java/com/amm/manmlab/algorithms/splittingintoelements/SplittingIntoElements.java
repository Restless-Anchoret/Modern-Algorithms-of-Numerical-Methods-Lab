package com.amm.manmlab.algorithms.splittingintoelements;

import com.amm.manmlab.utils.containers.PointsWithAdjacencyMatrix;
import com.amm.manmlab.utils.primitives.Element;
import com.amm.manmlab.utils.primitives.Point;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplittingIntoElements
{

    private static final Logger LOG = LoggerFactory.getLogger(SplittingIntoElements.class);
    private final List<Element> listOfElements = new LinkedList<>();
    private int length;
    private boolean[][] adjacencyMatrix;
    private Point[] points;
    private final LinkedList<Integer> connects = new LinkedList<>();

    public List<Element> splitting(PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix)
    {
        PointsWithAdjacencyMatrix innerMatrix = pointsWithAdjacencyMatrix.clone();
        points = innerMatrix.getPoints();
        adjacencyMatrix = innerMatrix.getAdjacencyMatrix();
        length = adjacencyMatrix.length;
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < length; j++)
            {
                if (adjacencyMatrix[i][j] && i != j)
                {
                    connects.add(j);
                }

            }
            for (int j = 0; j < connects.size(); j++)
            {
                for (int k = 0; k < connects.size(); k++)
                {
                    if (adjacencyMatrix[connects.get(j)][connects.get(k)] && k != j)
                    {
                        checkDirection(i, connects.get(j), connects.get(k));

                    }
                }

            }
            for (int j = 0; j < length; j++)
            {
                adjacencyMatrix[i][j] = false;
                adjacencyMatrix[j][i] = false;
            }
            connects.clear();
        }
        return listOfElements;
    }

    public List<Element> splittingBad(PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix)
    {
        PointsWithAdjacencyMatrix innerMatrix = pointsWithAdjacencyMatrix.clone();
        points = innerMatrix.getPoints();
        adjacencyMatrix = innerMatrix.getAdjacencyMatrix();
        length = adjacencyMatrix.length;
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (adjacencyMatrix[i][j])
                {
                    secondStep(i, j, i);
                }
            }

        }
        return listOfElements;
    }

    private void secondStep(int i, int j, int stopNumber)
    {
        for (int k = 0; k < length; k++)
        {
            if (k != i && k != j)
            {
                if (adjacencyMatrix[k][j])
                {
                    stopNumber = thirdStep(j, k, stopNumber);
                }
            }
        }
    }

    private int thirdStep(int i, int j, int stopNumber)
    {
        for (int k = 0; k < length; k++)
        {
            if (k != i && k != j && k == stopNumber
                    && adjacencyMatrix[k][j])
            {
                checkDirection(stopNumber, i, j);
                return stopNumber;
            }
        }
        return 0;
    }

    private void checkDirection(int i, int j, int k)//Проверяем против часовой стрелке у нас выбраны точки или нет
    {
        if (i != j && i != k && j != k)
        {
            Point iPoint = points[i];
            Point jPoint = points[j];
            Point kPoint = points[k];
            if (((jPoint.getX() - iPoint.getX()) * (kPoint.getY() - jPoint.getY())
                    - (jPoint.getY() - iPoint.getY()) * (kPoint.getX() - jPoint.getX()))
                    / 2 < 0)
            {
                Element tmp = new Element(i, j, k);
                if (!checkExistenceOfElement(tmp))
                {
                    listOfElements.add(tmp);
                    LOG.debug("New element: " + tmp.toString());
                }
            }
            else//Если по то меняем местами j и i
            {
                Element tmp = new Element(j, i, k);
                if (!checkExistenceOfElement(tmp))
                {
                    listOfElements.add(tmp);
                    LOG.debug("New element: " + tmp.toString());
                }
            }
        }
    }

    private boolean checkExistenceOfElement(Element tmp)
    {
        for (Element element : listOfElements)
        {
            int elI = element.getI();
            int elJ = element.getJ();
            int elK = element.getK();

            int tmpI = tmp.getI();
            int tmpJ = tmp.getJ();
            int tmpK = tmp.getK();

            if ((elI == tmpI || elI == tmpJ || elI == tmpK)
                    && (elJ == tmpI || elJ == tmpJ || elJ == tmpK)
                    && (elK == tmpI || elK == tmpJ || elK == tmpK))
            {
                return true;
            }
        }
        return false;
    }

}
