package com.amm.manmlab.algorithms.cuthillmckee;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.errors.ErrorMessages;
import com.amm.manmlab.utils.containers.PointsWithAdjacencyMatrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CuthillMcKeeAlgorithm
        implements Algorithm<PointsWithAdjacencyMatrix, PointsWithAdjacencyMatrix>{

    private static final Logger LOG = LoggerFactory.getLogger(CuthillMcKeeAlgorithm.class);

    @Override
    public PointsWithAdjacencyMatrix doAlgorithm(PointsWithAdjacencyMatrix screenObjects) {
        StartVertexFinder finder = new StartVertexFinder();
        //getResult(finder.findStartVertex(screenObjects.adjacencyMatrix, 0), screenObjects.adjacencyMatrix);
        // TODO: 27.11.16 implements new interface
        return null;
    }

    private boolean[][] getResult(int startVertex, boolean[][] adjacencyMatrix){
        LOG.debug("[ (adjacencyMatrix : {}, startVertex :{})", adjacencyMatrix, startVertex);
        if (startVertex >= adjacencyMatrix.length) {
            LOG.error(ErrorMessages.START_VERTEX_NUMBER_IS_GREATER_THAN_NUMBER_OF_VERTEX
                    .toString(startVertex, adjacencyMatrix.length));
        }

        List<Integer> rightOrder = findRightVerticesOrder(startVertex, adjacencyMatrix);
        LOG.debug("rightOrder : {}", rightOrder);

        for (int newNum = 0; newNum < rightOrder.size()-1; newNum++) {
            int oldNum = rightOrder.get(newNum);
            LOG.debug("swap {} and {}", newNum, oldNum);
            if (newNum == oldNum) {continue;}
            swapVerticesInAdjacencyMatrix(adjacencyMatrix, newNum, oldNum);
            rightOrder.set(rightOrder.indexOf(newNum), oldNum);
            rightOrder.set(newNum, newNum);
            LOG.debug("rightOrder : {}", rightOrder);
        }
        LOG.debug("] (return {})", adjacencyMatrix);
        return adjacencyMatrix;
    }

    private List<Integer> findRightVerticesOrder(int startVertex, boolean[][] adjacencyMatrix) {
        LOG.debug("[ (startVertex {})", startVertex);
        List<Integer> result = new ArrayList<>();

        result.add(startVertex);
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            Integer currentVertex = result.get(i);
            result.addAll(getAllConnectedVertices(currentVertex, result, adjacencyMatrix));
        }

        Collections.reverse(result);
        LOG.debug("] (return {})", result);
        return result;
    }

    private List<Integer> getAllConnectedVertices(int currentVertex, List<Integer> vertices, boolean[][] adjacencyMatrix){
        LOG.debug("[ (currentVertex {}, vertices : {})", currentVertex, vertices);

        List<VertexWithOrder> verticesWithOrders = new LinkedList<>();
        int i = 0;
        for (boolean isPathFromVertexExist : adjacencyMatrix[currentVertex]) {
            if (isPathFromVertexExist) {
                LOG.trace("path from {} to {} exists", currentVertex, i);
                if(!vertices.contains(i)) {
                    LOG.trace("vertices doesn't contain vertex {}. Add vertex to verticesWithOrders.", i);
                    verticesWithOrders.add(new VertexWithOrder(adjacencyMatrix, i++));
                    continue;
                }
                LOG.trace("Vertex {} is already in list. Skip it.", i);
            }
            i++;
        }
        LOG.trace("verticesWithOrders before sorting : {}", verticesWithOrders);
        Collections.sort(verticesWithOrders);
        LOG.trace("verticesWithOrders after sorting : {}", verticesWithOrders);

        LinkedList<Integer> result = new LinkedList<>();
        for (VertexWithOrder v : verticesWithOrders) {
            result.add(v.vertex);
        }
        LOG.debug("] (return {})", result);
        return result;
    }

    private void swapVerticesInAdjacencyMatrix(boolean[][] adjacencyMatrix, int i, int j) {
        LOG.trace("[ (i = {}, j = {})", i, j);
        boolean pathFromItoJ = adjacencyMatrix[i][j];
        adjacencyMatrix[i][i] = false;
        adjacencyMatrix[j][j] = false;

        swapColumnsInAdjacencyMatrix(adjacencyMatrix, i, j);
        swapRowsInAdjacencyMatrix(adjacencyMatrix, i, j);

        adjacencyMatrix[i][i] = true;
        adjacencyMatrix[j][j] = true;
        if (pathFromItoJ) {
            adjacencyMatrix[i][j] = true;
            adjacencyMatrix[j][i] = true;
        }
        LOG.trace("] (return {})", adjacencyMatrix);
    }

    private void swapRowsInAdjacencyMatrix(boolean[][] adjacencyMatrix, int i, int j){
        LOG.trace("[ (i = {}, j = {})", i, j);
        for (int k = 0; k < adjacencyMatrix[i].length; k++) {
            boolean b = adjacencyMatrix[i][k];
            adjacencyMatrix[i][k] = adjacencyMatrix[j][k];
            adjacencyMatrix[j][k] = b;
        }
        LOG.trace("] (return)");
    }

    private void swapColumnsInAdjacencyMatrix(boolean[][] adjacencyMatrix, int i, int j){
        LOG.trace("[ (i = {}, j = {})", i, j);
        for (int k = 0; k < adjacencyMatrix[i].length; k++) {
            boolean b = adjacencyMatrix[k][i];
            adjacencyMatrix[k][i] = adjacencyMatrix[k][j];
            adjacencyMatrix[k][j] = b;
        }
        LOG.trace("] (return)");
    }

    private class VertexWithOrder implements Comparable<VertexWithOrder>{
        private final int vertex;
        private final int order;

        VertexWithOrder(boolean[][] adjacencyMatrix, int vertex) {
            this.vertex = vertex;
            int tempOrder = 0;
            for (boolean isPathFromVertexExist : adjacencyMatrix[vertex]) {
                if (isPathFromVertexExist) {
                    tempOrder++;
                }
            }
            order = tempOrder;
        }

        @Override
        public int compareTo(VertexWithOrder o) {
            return Integer.compare(order, o.order);
        }

        @Override
        public String toString() {
            return "VertexWithOrder{" + "vertex=" + vertex +
                    ", order=" + order +
                    '}';
        }
    }
}
