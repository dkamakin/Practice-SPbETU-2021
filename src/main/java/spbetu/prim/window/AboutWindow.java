package spbetu.prim.window;

import javafx.scene.control.Alert;

public class AboutWindow {

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FAQ");
        alert.setHeaderText("FAQ");
        String content = """
                The program is designed to visualize the Prim algorithm. To find the minimum spanning tree, the
                following selection rule is applied: at each step, the edge of the lowest weight is selected from all
                the suitable edges. This edge along with one new vertex is added to the tree.\s
                The implementation was performed in the Java programming language using the JavaFX framework.\s
                Authors: Kamakin Daniil, Moskalenko Elizaveta, Aukhadiev Alexander""";

        alert.setContentText(content);
        alert.show();
    }
}

