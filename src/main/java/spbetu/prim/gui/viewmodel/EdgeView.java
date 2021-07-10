package spbetu.prim.gui.viewmodel;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class EdgeView {
    private StackPane from;
    private StackPane to;
    private Line line;
    private Text weight;

    public EdgeView(StackPane from, StackPane to, Line line, Text weight) {
        this.from = from;
        this.to = to;
        this.line = line;
        this.weight = weight;
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

    public Text getWeight() {
        return weight;
    }

    public void setWeight(Text weight) {
        this.weight = weight;
    }
}
