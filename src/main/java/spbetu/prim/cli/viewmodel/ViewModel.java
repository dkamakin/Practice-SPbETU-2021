package spbetu.prim.cli.viewmodel;

import lombok.extern.slf4j.Slf4j;
import spbetu.prim.exception.GraphInputException;
import spbetu.prim.logger.ConsoleLogger;
import spbetu.prim.logger.ILogger;
import spbetu.prim.model.algorithm.PrimAlgorithm;
import spbetu.prim.model.graph.Edge;
import spbetu.prim.model.graph.Graph;
import spbetu.prim.model.loader.FileLoader;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

@Slf4j
public class ViewModel {

    private final Graph graph;
    private final PrimAlgorithm algorithm;
    private final ILogger logger;

    public ViewModel() {
        this.graph = new Graph();
        this.logger = new ConsoleLogger();
        this.algorithm = PrimAlgorithm.getInstanceWithLogger(graph, logger);
    }

    public EdgeView stringToEdge(String input) throws GraphInputException {
        EdgeView edge = new EdgeView();
        String dash;
        Scanner scanner = new Scanner(input);

        while (scanner.hasNextInt()) {
            edge.setVertexFrom(scanner.nextInt());
            dash = scanner.next();

            if (!dash.equals("-")) {
                throw new GraphInputException("Between vertexes you need to enter \"-\"");
            }

            if (scanner.hasNextInt()) {
                edge.setVertexTo(scanner.nextInt());
            } else {
                throw new GraphInputException("Mistake of reading the second vertex");
            }

            if (scanner.hasNextInt()) {
                edge.setWeight(scanner.nextDouble());
                if (edge.getWeight() > 32000)   // костыль
                    throw new GraphInputException("Edge weight > 32000");
            } else {
                scanner.next();
                throw new GraphInputException("Wrong weight");
            }
        }

        return edge;
    }

    public void addEdge(String input) throws GraphInputException {
        EdgeView edgeView = stringToEdge(input);

        if (edgeView != null)
            graph.addNewEdge(
                    edgeView.getVertexFrom(), edgeView.getVertexTo(), edgeView.getWeight()
            );
    }

    public void deleteEdge(String input) throws GraphInputException {
        int vertexFrom, vertexTo;
        String dash;
        Scanner scanner = new Scanner(input);

        vertexFrom = scanner.nextInt();
        dash = scanner.next();

        if (!dash.equals("-")) {
            throw new GraphInputException("Between vertexes you need to enter \"-\"");
        }

        if (scanner.hasNextInt()) {
            vertexTo = scanner.nextInt();
        } else {
            throw new GraphInputException("Mistake of reading the second vertex");
        }

        if (vertexFrom > 0 && vertexTo > 0)
            graph.deleteEdge(vertexFrom, vertexTo);
    }

    public boolean nextStep() {
        Edge<Double> edge = algorithm.runAlgorithmByStep();
        logger.update();
        return edge == null || edge.getVertexTo() == null || edge.getVertexFrom() == null;
    }

    public void runAlgorithm() {
        while (!nextStep()) {
        }
    }

    public void prevStep() {
        algorithm.previousStep();
        logger.update();
    }

    public List<String> outputGraph() {
        List<Edge<Double>> edgeList = graph.getEdges();
        List<String> stringGraph = new ArrayList<>();

        for (Edge<Double> edge : edgeList) {
            stringGraph.add(edge.getVertexFrom().getNumber() +
                    " - " +
                    edge.getVertexTo().getNumber() +
                    ' ' +
                    edge.getWeight());
        }

        return stringGraph;
    }

    public List<String> outputTree() {
        Stack<Edge<Double>> edgeStack = algorithm.getSpanningTree();
        List<String> spanningTree = new ArrayList<>();

        for (Edge<Double> edge : edgeStack) {
            spanningTree.add(edge.getVertexFrom().getNumber() +
                    " - " +
                    edge.getVertexTo().getNumber() +
                    ' ' +
                    edge.getWeight());
        }

        return spanningTree;
    }

    public void readGraphFromFile(String fileName) {
        log.info("Trying to read from {}", fileName);

        try {
            FileLoader fileLoader = new FileLoader(fileName);
            graph.clear();
            fileLoader.loadGraph(graph);
        } catch (FileNotFoundException e) {
            log.error("Couldn't read from the file: " + e.getMessage());
        }
    }
}
