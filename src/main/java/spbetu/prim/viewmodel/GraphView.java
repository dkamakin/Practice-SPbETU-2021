package spbetu.prim.viewmodel;

import javafx.concurrent.WorkerStateEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.model.Edge;
import spbetu.prim.model.Graph;
import spbetu.prim.window.InfoWindow;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GraphView {

    private int currId;
    private StackPane prevStackPane;

    private final List<EdgeView> edges;
    private final Graph graph;
    private AlgorithmTask algorithmTask;

    public GraphView() {
        currId = 0;
        prevStackPane = null;
        edges = new ArrayList<>();
        graph = new Graph();
        algorithmTask = null;
    }

    public void clear() {
        currId = 0;
        prevStackPane = null;
        graph.graphStartAgain();
    }

    public StackPane addNode(MouseEvent mouseEvent) {
        StackPane stackPane = new StackPane();
        Circle circle = getCircle();

        Text text = new Text(String.valueOf(currId++));
        styleText(text);

        stackPane.getChildren().addAll(
                circle,
                text
        );
        stackPane.setLayoutY(mouseEvent.getY() - circle.getRadius());
        stackPane.setLayoutX(mouseEvent.getX() - circle.getRadius());
        return stackPane;
    }

    public void chooseNode(MouseEvent mouseEvent) {
        prevStackPane = (StackPane) mouseEvent.getSource();
    }

    public void removeNode(Node node) {
        Pane pane = (Pane) node.getParent();
        pane.getChildren().remove(node);
    }

    public boolean nextStep() {
        Edge edge = graph.runAlgorithmByStep();

        if (edge == null || edge.getVertexTo() == null || edge.getVertexFrom() == null)
            return true;

        addEdgeToTree(edge.getVertexFrom().getNumber(), edge.getVertexTo().getNumber(), edge.getWeight());
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
        return !weight.isEmpty();
    }

    public Pane addEdge(MouseEvent mouseEvent, String weight) {
        if (prevStackPane == null || prevStackPane == mouseEvent.getSource() || !checkWeight(weight))
            return null;

        StackPane currStackPane = (StackPane) mouseEvent.getSource();

        log.info("Drawing a line between nodes " +
                ((Text) prevStackPane.getChildren().get(1)).getText() +
                " and " +
                ((Text) currStackPane.getChildren().get(1)).getText());

        Pane pane = new Pane();
        Line line = new Line();

        line.startXProperty().bind(
                prevStackPane.layoutXProperty().add(prevStackPane.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind(
                prevStackPane.layoutYProperty().add(prevStackPane.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind(
                currStackPane.layoutXProperty().add(currStackPane.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind(
                currStackPane.layoutYProperty().add(currStackPane.getBoundsInParent().getHeight() / 2.0));

        Text text = new Text(weight);
        text.xProperty().bind(
                prevStackPane.layoutXProperty().add(currStackPane.layoutXProperty()).divide(2));
        text.yProperty().bind(
                prevStackPane.layoutYProperty().add(currStackPane.layoutYProperty()).divide(2));
        styleText(text);

        pane.getChildren().addAll(
                line,
                prevStackPane,
                text,
                currStackPane
        );

        edges.add(new EdgeView(prevStackPane, currStackPane, line));
        graph.addNewEdge(getNodeId(prevStackPane), getNodeId(currStackPane), Integer.parseInt(text.getText()));
        prevStackPane = null;
        return pane;
    }

    public void addEdgeToTree(int firstNode, int secondNode, int weight) {
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

        graph.graphStartAgain();
    }

    public Circle getCircle() {
        Circle circle = new Circle();
        circle.setRadius(20);
        circle.setFill(Color.ORCHID);
        return circle;
    }

    public void styleText(Text text) {
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setStyle(
                "-fx-font-family: \"Times New Roman\";" +
                        "-fx-font-style: italic;" +
                        "-fx-font-size: 22px;"
        );
    }
}