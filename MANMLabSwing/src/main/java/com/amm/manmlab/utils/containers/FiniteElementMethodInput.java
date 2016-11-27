package com.amm.manmlab.utils.containers;

public class FiniteElementMethodInput {

    public PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix;
    public Double poissonsRatio;
    public Double youngsModulus;
    public Double[] borderConditions;

    public FiniteElementMethodInput(PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix,
                                    Double poissonsRatio,
                                    Double youngsModulus,
                                    Double[] borderConditions) {
        this.pointsWithAdjacencyMatrix = pointsWithAdjacencyMatrix;
        this.poissonsRatio = poissonsRatio;
        this.youngsModulus = youngsModulus;
        this.borderConditions = borderConditions;
    }
}
