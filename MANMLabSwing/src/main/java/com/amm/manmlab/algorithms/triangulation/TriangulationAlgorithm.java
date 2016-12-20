package com.amm.manmlab.algorithms.triangulation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Алгоритм трингуляции - выемка-вырезка + центрирование звезд
 */
public class TriangulationAlgorithm implements Algorithm<PointsWithEdges, PointsWithEdges> {

    private static final Logger LOG = LoggerFactory.getLogger(TriangulationAlgorithm.class);
    private static final int NUMBER_STAR_CENTERING = 3;

    @Override
    public PointsWithEdges doAlgorithm(PointsWithEdges screenObjects) {
        LOG.debug("[ (screenObjects : {})", screenObjects);
        PointsWithEdges objectsAfterTriangulation = new BasicTriangulationAlgorithm().doAlgorithm(screenObjects);
        LOG.trace("objectsAfterTriangulation : {}", objectsAfterTriangulation);
        for (int i = 0; i < NUMBER_STAR_CENTERING; i++) 
        {
            objectsAfterTriangulation = starCentering(objectsAfterTriangulation);
            LOG.trace("objects after star centering iteration - {} : {}", i, objectsAfterTriangulation);
        }
        LOG.debug("] (return {})", objectsAfterTriangulation);
        return objectsAfterTriangulation;
    }

    //центрирование звёзд
    public PointsWithEdges starCentering(PointsWithEdges data) {

        List<Edge> edges = data.getEdges();
        List<Point> points = data.getPoints();
        
        ArrayList<Integer> neighbor=new ArrayList<>();
        ArrayList<Integer> circle=new ArrayList<>();
        
        ArrayList<Point> newPoint=new ArrayList<>();
        ArrayList<Integer> newPointIndex=new ArrayList<>();
        
        Edge ed = null;
        
        int count=0;
        
        ArrayList<Point> gran=new ArrayList<>();
        ArrayList<Point> internal=new ArrayList<>();
        ArrayList<Integer> internalIndex=new ArrayList<>();
        ArrayList<Integer> granIndex=new ArrayList<>();
        for (int i=0;i<points.size();i++)
        {
            neighbor.clear();
        for (int j=0;j<edges.size();j++)
        {
            //находим соседние точки
            ed=edges.get(j);
            if(ed.getFirstIndex()==i) neighbor.add(ed.getSecondIndex());
            else if (ed.getSecondIndex()==i) neighbor.add(ed.getFirstIndex()); 
        }
        
         //определеяем тип точки: граничная или внутренняя
        for (int k=0;k<neighbor.size();k++)
        {
            
            for (int l=k+1; l<neighbor.size(); l++)
            {
            for (int j=0;j<edges.size();j++)
            {
                ed=edges.get(j);
                if(((ed.getFirstIndex()==neighbor.get(k)) &&  (ed.getSecondIndex()==neighbor.get(l))) || ((ed.getFirstIndex()==neighbor.get(l)) &&  (ed.getSecondIndex()==neighbor.get(k)))) 
                {
                    circle.add(neighbor.get(k));
                    circle.add(neighbor.get(l));
                    
                }
            }
            }
             
       
            }
        count=0;
              for (int v=0;v<circle.size();v++)
            {
                for (int u=0;u<circle.size();u++)
                {
                    if(Objects.equals(circle.get(u), circle.get(v))) count++;
                }
                
        }

               if (count<circle.size()*2) 
               {
                   gran.add(points.get(i));
                   granIndex.add(i);
               }
                else {
                   internal.add(points.get(i));
                   internalIndex.add(i);
               }
               circle.clear();
        }
        
        
        double sredX=0, sredY=0;
        Point p=new Point(-1,-1);
        Point currentPoint=new Point(-1,-1);
        Point movepoint=new Point(-1,-1);
        Point sred = null;
        
        //перемещение внутренних точек
        neighbor.clear();

         ArrayList <Point>movePoint=new ArrayList<Point>(); 
        for (int i=0;i<internal.size();i++)
        {
            neighbor.clear();
        for (int j=0;j<edges.size();j++)
        {
            //находим соседние точки
            ed=edges.get(j);
            if(ed.getFirstIndex()==internalIndex.get(i)) neighbor.add(ed.getSecondIndex());
            else if (ed.getSecondIndex()==internalIndex.get(i)) neighbor.add(ed.getFirstIndex()); 
        }
        
        //выполняем перемещение
        sredX=0;sredY=0;
        
       boolean flag=false;
        
        for (int l=0;l<neighbor.size();l++)
        {
            flag=false;
            for (int k=0;k<movePoint.size();k++)
            {
                if(movePoint.get(k).equals(points.get(neighbor.get(l))))
                {
                    movepoint=points.get(neighbor.get(l));
                    sredX+=movepoint.getX();
                    sredY+=movepoint.getY();
                    flag=true;
                }
             
        }
            if (!flag){
             currentPoint=points.get(neighbor.get(l));
             sredX+=currentPoint.getX();
             sredY+=currentPoint.getY();
            }
           
        }
           sredX/=neighbor.size();
           sredY/=neighbor.size();
           sred=new Point(sredX,sredY);
           movePoint.add(internal.get(i));
           newPoint.add(sred);
           newPointIndex.add(internalIndex.get(i));
        }
       
        ArrayList<Point> resultPoint=new ArrayList<>();
      
        for (int i=0;i<points.size();i++)
        {
            for(int j=0;j<gran.size();j++)
            {
                if(i==granIndex.get(j)) 
                resultPoint.add(gran.get(j));
            }
            for (int k=0;k<newPoint.size();k++)
            {
               if (i==newPointIndex.get(k))
                    resultPoint.add(newPoint.get(k)); 
            }
        }
        
        PointsWithEdges result=new PointsWithEdges(edges,resultPoint);
        return result;
    //           return data.clone();
    }
}
