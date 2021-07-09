package spbetu.prim.viewmodel;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.logger.ConsoleLogger;
import spbetu.prim.model.algorithm.PrimAlgorithm;
import spbetu.prim.model.graph.Edge;
import spbetu.prim.model.graph.Graph;
import spbetu.prim.model.loader.FileLoader;
import spbetu.prim.window.InfoWindow;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GraphView {

    private final GraphVisualizer visualizer;
    private final PrimAlgorithm algorithm;
    private final Graph graph;
    private List<EdgeView> edges;
    private int currId;
    private StackPane prevStackPane;
    private AlgorithmTask algorithmTask;

    public GraphView() {
        this.currId = 0;
        this.prevStackPane = null;
        this.visualizer = new GraphVisualizer();
        this.edges = new ArrayList<>();
        this.graph = new Graph();
        this.algorithm = PrimAlgorithm.getInstanceWithLogger(graph, new ConsoleLogger());
        this.algorithmTask = null;
    }

    public void clear() {
        currId = 0;
        prevStackPane = null;
        edges.clear();
        graph.clear();
        algorithm.restart();
    }

    public StackPane addNode(double x, double y) {
        return visualizer.getVertex(x, y, currId++);
    }

    public List<EdgeView> readGraphFromFile(String fileName) {
        log.info("Trying to read from {}", fileName);

        try {
            FileLoader fileLoader = new FileLoader(fileName);
            graph.clear();
            fileLoader.loadGraph(graph);
        } catch (FileNotFoundException e) {
            log.error("Couldn't read from the file: " + e.getMessage());
            return null;
        }

        currId = graph.getSize();
        edges = visualizer.visualize(graph);
        return edges;
    }

    public void chooseNode(StackPane vertex) {
        prevStackPane = vertex;
    }

    public void moveVertex(double x, double y) {
        if (prevStackPane == null)
            return;

        log.info("Move");
        prevStackPane.setLayoutX(x);
        prevStackPane.setLayoutY(y);
    }

    public void removeVertexWithEdges(Node node) {
        int id = getNodeId((StackPane) node);
        graph.deleteVertex(id);

        Pane pane = (Pane) node.getParent();
        pane.getChildren().remove(node);

        for (int i = 0; i < edges.size(); i++) {
            EdgeView elem = edges.get(i);

            if (getNodeId(elem.getTo()) == id ||
                    getNodeId(elem.getFrom()) == id) {
                edges.remove(elem);
                i--;

                Pane linePane = (Pane) elem.getLine().getParent();

                if (linePane == null)
                    continue;

                linePane.getChildren().remove(elem.getLine());
                linePane.getChildren().remove(elem.getWeight());
            }
        }
    }

    public void removeEdgeByWeight(Node node) {
        for (EdgeView elem : edges) {
            if (elem.getWeight() == node) {
                log.info("Found edge");
                Pane pane = (Pane) elem.getLine().getParent();
                pane.getChildren().remove(elem.getLine());
                pane.getChildren().remove(elem.getWeight());
                graph.deleteEdge(getNodeId(elem.getFrom()), getNodeId(elem.getTo()));
                edges.remove(elem);
                return;
            }
        }
    }

    public void previousStep() {
        Edge<Double> lastEdge = algorithm.previousStep();

        if (lastEdge == null) {
            log.info("Algorithm returned prev step as null");
            return;
        }

        int numberFrom = lastEdge.getVertexFrom().getNumber();
        int numberTo = lastEdge.getVertexTo().getNumber();

        for (EdgeView elem : edges) {
            int from = getNodeId(elem.getFrom());
            int to = getNodeId(elem.getTo());

            if (numberFrom == from && numberTo == to) {
                log.info("Found the prev step");
                paintCircle(elem.getTo(), Color.ORCHID);
                paintLine(elem.getLine(), Color.BLACK, 1);
                return;
            } else if (numberFrom == to && numberTo == from) {
                log.info("Found the prev step");
                paintCircle(elem.getFrom(), Color.ORCHID);
                paintLine(elem.getLine(), Color.BLACK, 1);
                return;
            }
        }

        log.info("Didn't find the last step");
    }

    public boolean nextStep() {
        Edge<Double> edge = algorithm.runAlgorithmByStep();

        if (edge == null || edge.getVertexTo() == null || edge.getVertexFrom() == null)
            return true;

        addEdgeToTree(
                edge.getVertexFrom().getNumber(), edge.getVertexTo().getNumber(), edge.getWeight()
        );

        return false;
    }

    public void runAlgorithm() {
        algorithmTask = new AlgorithmTask(this);
        algorithmTask.setOnSucceeded(this::doneRun);
        new Thread(algorithmTask).start();
    }

    public void stopAlgorithm() {
        log.info("Stop");
        algorithmTask.setAlive(false);
    }

    private void doneRun(WorkerStateEvent workerStateEvent) {
        if (algorithmTask.isAlive())
            new InfoWindow().show("The minimum spanning tree was found");
        else
            new InfoWindow().show("The algorithm was stopped by user");
    }

    public boolean checkWeight(String weight) {
        if (weight == null || weight.isEmpty())
            return false;

        try {
            Double.parseDouble(weight);
        } catch (NumberFormatException e) {
            log.error("Wrong weight");
            return false;
        }

        return true;
    }

    public Pane addEdge(StackPane secondVertex, String weight) {
        if (!checkWeight(weight))
            return null;

        log.info("Drawing a line between nodes " +
                ((Text) prevStackPane.getChildren().get(1)).getText() +
                " and " +
                ((Text) secondVertex.getChildren().get(1)).getText());

        EdgeView edge = visualizer.getEdge(prevStackPane, secondVertex, weight);

        if (edge == null)
            return null;

        edges.add(edge);
        Pane pane = new Pane();
        pane.getChildren().addAll(
                edge.getLine(),
                edge.getFrom(),
                edge.getWeight(),
                edge.getTo()
        );

        graph.addNewEdge(
                getNodeId(prevStackPane), getNodeId(secondVertex), Double.parseDouble(weight)
        );

        prevStackPane = null;
        return pane;
    }

    public void addEdgeToTree(int firstNode, int secondNode, Double weight) {
        log.info("Adding an edge to the tree: ({}) - ({}), weight: {}",
                firstNode, secondNode, weight);

        for (EdgeView elem : edges) {
            StackPane first = elem.getFrom();
            StackPane second = elem.getTo();

            if (firstNode == getNodeId(first) && secondNode == getNodeId(second)) {
                log.info("Found the edge in the list of edgeview");
                Color color = Color.BLUE;
                paintCircle(first, color);
                paintCircle(second, color);
                paintLine(elem.getLine(), color, 3);
                return;
            }
        }

        log.info("Didn't find the edge in the list of edgeview");
    }

    public void paintCircle(StackPane stackPane, Color color) {
        Circle circle = (Circle) stackPane.getChildren().get(0);
        circle.setFill(color);
    }

    public void paintLine(Line line, Color color, int width) {
        line.setStrokeWidth(width);
        line.setStroke(color);
    }

    public int getNodeId(StackPane stackPane) {
        Text text = (Text) stackPane.getChildren().get(1);
        return Integer.parseInt(text.getText());
    }

    public void resetGraph() {
        for (EdgeView elem : edges) {
            paintCircle(elem.getFrom(), Color.ORCHID);
            paintCircle(elem.getTo(), Color.ORCHID);
            paintLine(elem.getLine(), Color.BLACK, 1);
        }

        algorithm.restart();
    }
}