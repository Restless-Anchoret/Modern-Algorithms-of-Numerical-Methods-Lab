package com.amm.manmlab.algorithms.cuthillmckee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class StartVertexFinder {

    private static final Logger LOG = LoggerFactory.getLogger(StartVertexFinder.class);

    int findStartVertex(boolean[][] adjacencyMatrix, int startVertex){
        LOG.debug("[ (adjacencyMatrix : {}, startVertex : {})", adjacencyMatrix, startVertex);;

        List<List<Integer>> verticesByLevelsForRoot;
        List<List<Integer>> verticesByLevelsForVariant = getVerticesByLevels(adjacencyMatrix, startVertex);
        do {
            verticesByLevelsForRoot = verticesByLevelsForVariant;

            LOG.trace("verticesByLevelsForRoot : {}", verticesByLevelsForRoot);
            LOG.trace("lengthFromRoot : {}", verticesByLevelsForRoot.size());
            List<Integer> newVariants = findVerticesWithMinConnections(
                    adjacencyMatrix,
                    verticesByLevelsForRoot.get(verticesByLevelsForRoot.size() - 1)
            );
            LOG.trace("newVariants : {}",newVariants);

            verticesByLevelsForVariant = findVerticesByLevelsForMaxLength(adjacencyMatrix, newVariants);
            LOG.trace("lengthFromVariant : {}", verticesByLevelsForVariant.size());
        } while (verticesByLevelsForVariant.size() > verticesByLevelsForRoot.size());
        return verticesByLevelsForVariant.get(0).get(0);
    }

    private List<List<Integer>> findVerticesByLevelsForMaxLength(boolean[][] adjacencyMatrix, List<Integer> vertices){
        LOG.trace("[ (adjacencyMatrix : {}, vertices : {})", adjacencyMatrix, vertices);
        List<List<Integer>> maxLevels = Collections.emptyList();
        for (Integer vertex : vertices) {
            List<List<Integer>> levels = getVerticesByLevels(adjacencyMatrix, vertex);
            if (levels.size() > maxLevels.size()) {
                maxLevels = levels;
            }
        }
        LOG.trace("] (return {})", maxLevels);
        return maxLevels;
    }

    private List<List<Integer>> getVerticesByLevels(boolean[][] adjacencyMatrix, int startVertex){
        LOG.trace("[ (adjacencyMatrix : {}, startVertex : {})", adjacencyMatrix, startVertex);
        List<Integer> freeVertex = new LinkedList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) { freeVertex.add(i);}

        List<List<Integer>> levels = new LinkedList<>();
        levels.add(Collections.singletonList(startVertex));
        freeVertex.remove(Integer.valueOf(startVertex));
        int level = 1;
        while (!freeVertex.isEmpty()) {
            LOG.trace("level = {}, freeVertex = {}", level, freeVertex);
            List<Integer> currentLevel = new LinkedList<>();
            levels.get(level-1).forEach(vertex->{
                for (int i = 0; i < adjacencyMatrix[vertex].length; i++) {
                    if (!adjacencyMatrix[vertex][i]) continue;
                    LOG.trace("Path from vertex {} to {} exists", vertex, i);
                    if (!freeVertex.contains(i)) { continue; }
                    LOG.trace("i = {} is not used before. add i", i);
                    currentLevel.add(i);
                    freeVertex.remove(Integer.valueOf(i));
                }
            });
            LOG.trace("currentLevel : {}",currentLevel);
            levels.add(currentLevel);
            if (currentLevel.isEmpty()) {
                LOG.warn("graph with more than one connected component!");
                break;
            }
            level++;
        }

        LOG.trace("] (return {})", levels);
        return levels;
    }

    private List<Integer> findVerticesWithMinConnections(boolean[][] adjacencyMatrix, List<Integer> endVertices) {
        LOG.trace("[ (endVertices : {})", endVertices);
        List<Integer> numberOfConnections = new ArrayList<>(endVertices.size());
        endVertices.forEach(vertex -> numberOfConnections.add(getNumberOfConnections(adjacencyMatrix, vertex)));
        LOG.trace("endVertices : {}", endVertices);
        LOG.trace("numberOfConnections : {}", numberOfConnections);
        int minNumber = Collections.min(numberOfConnections);
        LOG.trace("minNumber : {}", minNumber);

        Iterator<Integer> vertex = endVertices.iterator();
        Iterator<Integer> number = numberOfConnections.iterator();

        while (vertex.hasNext()) {
            vertex.next();
            if (number.next() > minNumber) {
                vertex.remove();
                number.remove();
            }
        }

        LOG.trace("] (return {})", endVertices);
        return endVertices;
    }

    private int getNumberOfConnections(boolean[][] adjacencyMatrix, Integer vertex){
        LOG.trace("[ (vertex : {})", vertex);
        int i = 0;
        for (boolean b : adjacencyMatrix[vertex]) {
            if (b) i++;
        }
        LOG.trace("] (return {})", i);
        return i;
    }

}

