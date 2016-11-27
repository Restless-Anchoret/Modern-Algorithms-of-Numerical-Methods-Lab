package com.amm.manmlab.interfaces;

import java.util.List;

public class FileInput {

    private List<Pair<Double, Double>> verticesCoordinates;

    public FileInput(List<Pair<Double, Double>> verticesCoordinates) {
        this.verticesCoordinates = verticesCoordinates;
    }

    public List<Pair<Double, Double>> getVerticesCoordinates() {
        return verticesCoordinates;
    }

    @Override
    public String toString() {
        return "FileInput{" + "verticesCoordinates=" + verticesCoordinates + '}';
    }
    
}