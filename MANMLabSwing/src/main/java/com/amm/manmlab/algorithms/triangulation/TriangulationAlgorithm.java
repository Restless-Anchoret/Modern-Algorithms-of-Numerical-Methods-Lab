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
        for (int i = 0; i < NUMBER_STAR_CENTERING; i++) {
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
        
        data.toString();
        ArrayList<Integer> neighbor=new ArrayList<Integer>();
        ArrayList<Integer> circle=new ArrayList<Integer>();
        
        Edge ed=new Edge(-1,-1);
        
        int count=0;
        
        ArrayList<Point> gran=new ArrayList<Point>();
        ArrayList<Point> internal=new ArrayList<Point>();

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
              //if(count<2) gran.add(points.get(neighbor.get(v)));
               // else neigh.add(points.get(i));
               if (count<circle.size()*2) gran.add(points.get(i));
                else internal.add(points.get(i));
                circle.clear();
        }
        
        
        double sredX=0, sredY=0;
        Point p=new Point(-1,-1);
        Point currentPoint=new Point(-1,-1);
        Point movepoint=new Point(-1,-1);
        Point sred = null;
        ArrayList <Point> newPoint=new ArrayList<Point>();
        //перемещение внутренних точек
        neighbor.clear();
        double previousX=-1, previousY=-1;
        for (int i=0;i<internal.size();i++)
        {
        for (int j=0;j<edges.size();j++)
        {
            //находим соседние точки
            ed=edges.get(j);
            if(ed.getFirstIndex()==i) neighbor.add(ed.getSecondIndex());
            else if (ed.getSecondIndex()==i) neighbor.add(ed.getFirstIndex()); 
        }
        
        //выполняем перемещение
        sredX=0;sredY=0;
        p=internal.get(i);
        previousX=p.getX();
        previousY=p.getY();
        ArrayList <Point>movePoint=new ArrayList<Point>();        
       boolean flag=false;
        
        for (int l=0;l<neighbor.size();l++)
        {
            flag=false;
            for (int k=0;k<movePoint.size();k++)
            {
                movepoint=points.get(neighbor.get(k));
                sredX+=movepoint.getX();
                sredY+=movepoint.getY();
                flag=true;
             
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
        }
        for (int i=0;i<gran.size();i++)
        {
            newPoint.add(gran.get(i));
        }
               PointsWithEdges pwe=new PointsWithEdges(edges,newPoint);
        return data.clone();
    }
}
