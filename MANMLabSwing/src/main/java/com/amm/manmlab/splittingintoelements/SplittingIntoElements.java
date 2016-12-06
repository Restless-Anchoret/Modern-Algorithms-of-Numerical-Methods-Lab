package com.amm.manmlab.splittingintoelements;

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
    private int deep = 1;
    private Point[] points;

    public List<Element> splitting(PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix)
    {
        points = pointsWithAdjacencyMatrix.getPoints();
        adjacencyMatrix = pointsWithAdjacencyMatrix.getAdjacencyMatrix();
        length = adjacencyMatrix.length;
        for (int i = 0; i < length; i++)
        {
            for (int j = 0; j <= i; j++)
            {
                if (adjacencyMatrix[i][j])
                {
                    goDeeper(i, j, i);
                }
                deep = 1;
            }

        }
        return listOfElements;
    }

    private void goDeeper(int i, int j, int stopNumber)
    {
        if (deep < 3)
        {
            deep++;
            for (int k = 0; k < length; k++)
            {
                if (k != i)
                {
                    if (adjacencyMatrix[k][j])
                    {
                        if (k == stopNumber)
                        {
                            checkDirection(stopNumber, i, j);
                        }
                        else
                        {
                            goDeeper(j, k, stopNumber);
                        }
                    }
                }
            }
        }
    }

    private void checkDirection(int i, int j, int k)//Проверяем против часовой стрелке у нас выбраны точки или нет
    {
        Point iPoint = points[i];
        Point jPoint = points[j];
        Point kPoint = points[k];
        if (((jPoint.getX() - iPoint.getX()) * (kPoint.getY() - jPoint.getY())
                - (jPoint.getY() - iPoint.getY()) * (kPoint.getX() - jPoint.getX()))
                / 2 > 0)
        {
            LOG.info("New element: []", new Element(i, j, k));
            listOfElements.add(new Element(i, j, k));
        }
        else//Если по то меняем местами j и i
        {
            LOG.info("New element: []", new Element(j, i, k));
            listOfElements.add(new Element(j, i, k));
        }
    }
}
