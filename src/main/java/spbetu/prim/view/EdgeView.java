package spbetu.prim.view;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;

public class EdgeView {
    private StackPane from;
    private StackPane to;
    private Line line;

    public EdgeView(StackPane from, StackPane to, Line line) {
        this.from = from;
        this.to = to;
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public StackPane getTo() {
        return to;
    }

    public void setTo(StackPane to) {
        this.to = to;
    }

    public StackPane getFrom() {
        return from;
    }

    public void setFrom(StackPane from) {
        this.from = from;
    }
}
