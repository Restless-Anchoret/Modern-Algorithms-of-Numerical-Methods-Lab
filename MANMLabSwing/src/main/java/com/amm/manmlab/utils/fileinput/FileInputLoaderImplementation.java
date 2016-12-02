package com.amm.manmlab.utils.fileinput;

import com.amm.manmlab.utils.containers.PointsWithEdges;
import com.amm.manmlab.utils.primitives.Edge;
import com.amm.manmlab.utils.primitives.Point;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileInputLoaderImplementation implements FileInputLoader {

    private static final Logger LOG = LoggerFactory.getLogger(FileInputLoaderImplementation.class);
    
    private static final String FILE_NAME = "input.txt";
    
    @Override
    public PointsWithEdges loadInputFromFile() {
        try {
            LOG.trace("Before loading from input file.");
            Scanner scanner = new Scanner(Files.newInputStream(Paths.get(FILE_NAME)));
            int n = scanner.nextInt();
            LOG.trace("n = {}", n);
            List<Point> points = new ArrayList<>(n);
            List<Edge> edges = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                LOG.trace("x = {}, y = {}", x, y);
                points.add(new Point(x, y));
                edges.add(new Edge(i, (i + 1) % n));
            }
            LOG.trace("Input was loaded.");
            return new PointsWithEdges(edges, points);
        } catch (Exception ex) {
            LOG.error("Exception while loading from input file", ex);
            return new PointsWithEdges(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        }
    }

}