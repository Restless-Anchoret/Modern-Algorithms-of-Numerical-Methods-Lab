package com.amm.manmlab.utils.containers;

public class FiniteElementMethodInput {

    private PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix;
    private Double poissonsRatio;
    private Double youngsModulus;
    private Double[] borderConditions;

    public FiniteElementMethodInput(PointsWithAdjacencyMatrix pointsWithAdjacencyMatrix,
                                    Double poissonsRatio,
                                    Double youngsModulus,
                                    Double[] borderConditions) {
        this.pointsWithAdjacencyMatrix = pointsWithAdjacencyMatrix;
        this.poissonsRatio = poissonsRatio;
        this.youngsModulus = youngsModulus;
        this.borderConditions = borderConditions;
    }

    public PointsWithAdjacencyMatrix getPointsWithAdjacencyMatrix() {
        return pointsWithAdjacencyMatrix;
    }

    public Double getPoissonsRatio() {
        return poissonsRatio;
    }

    public Double getYoungsModulus() {
        return youngsModulus;
    }

    public Double[] getBorderConditions() {
        return borderConditions;
    }

    @Override
    public String toString() {
        return "FiniteElementMethodInput{" + "pointsWithAdjacencyMatrix=" + pointsWithAdjacencyMatrix + ", poissonsRatio=" + poissonsRatio + ", youngsModulus=" + youngsModulus + ", borderConditions=" + borderConditions + '}';
    }
    
}
