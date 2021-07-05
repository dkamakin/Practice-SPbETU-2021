package spbetu.prim.view;

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

@Slf4j
public class GraphView {

    private int currId;
    private ScrollPaneLog scrollPaneLog;
    private StackPane prevStackPane;

    public GraphView(ScrollPaneLog scrollPane) {
        scrollPaneLog = scrollPane;
        currId = 0;
        prevStackPane = null;
    }

    public void clear() {
        scrollPaneLog.clearingGraphMessage();
        currId = 0;
        prevStackPane = null;
    }

    public StackPane addNode(MouseEvent mouseEvent) {
        scrollPaneLog.addNodeMessage(Integer.toString(currId));

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
        scrollPaneLog.nodeClickedMessage(((Text)prevStackPane.getChildren().get(1)).getText());
    }

    public void removeNode(Node node) {
        Pane pane = (Pane) node.getParent();
        pane.getChildren().remove(node);
    }

    public Pane addEdge(MouseEvent mouseEvent) {
        if (prevStackPane == null || prevStackPane == mouseEvent.getSource())
            return null;

        StackPane currStackPane = (StackPane) mouseEvent.getSource();

        scrollPaneLog.addEdgeMessage(((Text)prevStackPane.getChildren().get(1)).getText(),
                ((Text)currStackPane.getChildren().get(1)).getText());

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

        Text text = new Text();
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

        prevStackPane = null;
        return pane;
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
                        "-fx-font-size: 26px;"
        );
    }

    public void clearLogging(){
        scrollPaneLog.clear();
    }
}