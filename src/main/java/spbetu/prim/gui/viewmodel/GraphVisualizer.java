package spbetu.prim.gui.viewmodel;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.model.graph.Edge;
import spbetu.prim.model.graph.Graph;
import spbetu.prim.model.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class GraphVisualizer {

    public List<EdgeView> visualize(Graph graph) {
        List<Vertex> vertexList = graph.getVertices();
        HashMap<Integer, StackPane> vertices = new HashMap<>();

        int radius = vertexList.size() * 15;
        int amountVertices = vertexList.size();

        for (int i = 0; i < amountVertices; i++) {
            int x = (int) (Math.cos(2 * Math.PI * i / amountVertices) * radius + 0.5);
            int y = (int) (Math.sin(2 * Math.PI * i / amountVertices) * radius + 0.5);
            int number = vertexList.get(i).getNumber();
            vertices.put(number, getVertex(x, y, number));
        }

        List<EdgeView> result = new ArrayList<>();
        for (Edge<Double> elem : graph.getEdges()) {
            EdgeView edgeView =
                    getEdge(
                            vertices.get(elem.getVertexFrom().getNumber()),
                            vertices.get(elem.getVertexTo().getNumber()),
                            String.valueOf(elem.getWeight())
                    );
            result.add(edgeView);
        }

        return result;
    }

    public EdgeView getEdge(StackPane vertexFrom, StackPane vertexTo, String weight) {
        if (vertexFrom == null || vertexFrom == vertexTo)
            return null;

        Line line = new Line();

        line.startXProperty().bind(
                vertexFrom.layoutXProperty().add(vertexFrom.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind(
                vertexFrom.layoutYProperty().add(vertexFrom.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind(
                vertexTo.layoutXProperty().add(vertexTo.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind(
                vertexTo.layoutYProperty().add(vertexTo.getBoundsInParent().getHeight() / 2.0));

        Text text = new Text(weight);
        text.xProperty().bind(
                vertexFrom.layoutXProperty().add(vertexTo.layoutXProperty()).divide(2));
        text.yProperty().bind(
                vertexFrom.layoutYProperty().add(vertexTo.layoutYProperty()).divide(2));
        styleText(text);

        return new EdgeView(vertexFrom, vertexTo, line, text);
    }

    public StackPane getVertex(double x, double y, int id) {
        StackPane stackPane = new StackPane();
        Circle circle = getCircle(20, Color.ORCHID);

        Text text = new Text(String.valueOf(id));
        styleText(text);

        stackPane.getChildren().addAll(
                circle,
                text
        );
        stackPane.setLayoutY(y - circle.getRadius());
        stackPane.setLayoutX(x - circle.getRadius());
        return stackPane;
    }

    public Circle getCircle(int size, Color color) {
        Circle circle = new Circle();
        circle.setRadius(size);
        circle.setFill(color);
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

    public void paintCircle(StackPane stackPane, Color color) {
        Circle circle = (Circle) stackPane.getChildren().get(0);
        circle.setFill(color);
    }

    public void paintLine(Line line, Color color, int width) {
        line.setStrokeWidth(width);
        line.setStroke(color);
    }
}