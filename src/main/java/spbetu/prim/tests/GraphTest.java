package spbetu.prim.tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spbetu.prim.model.graph.Graph;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class GraphTest {

    Graph graph;
    String fileName;

    public GraphTest() {
        graph = new Graph();
        graph.addNewEdge(0, 1, (double) 2);
        graph.addNewEdge(0, 2, (double) 2);
        graph.addNewEdge(1, 2, (double) 1);
    }

    @Before
    public void setUp() {

    }

    @Test
    public void deleteEdge() {

        int oldCount = graph.getCountOfEdges();

        graph.deleteEdge(0, 1);

        int newCount = graph.getCountOfEdges();

        Assert.assertEquals(oldCount - 2, newCount);

        graph.addNewEdge(0, 1, (double) 2);
    }

    @Test
    public void addNewEdge() {
        int oldCount = graph.getCountOfEdges();

        graph.addNewEdge(2, 3, (double) 5);

        int newCount = graph.getCountOfEdges();

        Assert.assertEquals(oldCount + 2, newCount);

        graph.deleteEdge(2, 3);
    }
}