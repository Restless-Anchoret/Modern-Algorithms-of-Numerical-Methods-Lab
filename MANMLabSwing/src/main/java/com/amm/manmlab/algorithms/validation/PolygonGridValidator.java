package com.amm.manmlab.algorithms.validation;

import com.amm.manmlab.algorithms.Algorithm;
import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import java.util.Arrays;

public class PolygonGridValidator implements Algorithm<PointsWithEdges, Boolean> {

    @Override
    public Boolean doAlgorithm(PointsWithEdges input) {
        int n = input.getPoints().size();
        int[][] adjucentMatrix = new int[n][n];
        for (Edge edge: input.getEdges()) {
            adjucentMatrix[edge.getFirstIndex()][edge.getSecondIndex()] = 1;
            adjucentMatrix[edge.getSecondIndex()][edge.getFirstIndex()] = 1;
        }
        if (Arrays.stream(adjucentMatrix).anyMatch(
                row -> Arrays.stream(row).sum() != 2)) {
            return false;
        }
        
        int[] labeledByDfs = new int[n];
        dfs(0, adjucentMatrix, labeledByDfs);
        return Arrays.stream(labeledByDfs).sum() == n;
    }
    
    private void dfs(int v, int[][] adjucentMatrix, int[] labeledByDfs) {
        labeledByDfs[v] = 1;
        for (int i = 0; i < adjucentMatrix[v].length; i++) {
            if (labeledByDfs[i] == 0 && adjucentMatrix[v][i] == 1) {
                dfs(i, adjucentMatrix, labeledByDfs);
            }
        }
    }

}