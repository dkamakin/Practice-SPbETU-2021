package spbetu.prim.gui.window;

import javafx.scene.control.Alert;

public class AboutWindow {

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FAQ");
        alert.setHeaderText("FAQ");
        String content = "The program is designed to visualize the Prim algorithm. To find the minimum spanning tree, the\n" +
                "following selection rule is applied: at each step, the edge of the lowest weight is selected from all\n" +
                "the suitable edges. This edge along with one new vertex is added to the tree. \n" +
                "Authors: Kamakin Daniil, Moskalenko Elizaveta, Aukhadiev Alexander\n";

        alert.setContentText(content);
        alert.show();
    }
}

