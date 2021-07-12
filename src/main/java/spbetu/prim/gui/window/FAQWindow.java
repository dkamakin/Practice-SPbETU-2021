package spbetu.prim.gui.window;

import javafx.scene.control.Alert;

public class FAQWindow {

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FAQ");
        alert.setHeaderText("FAQ");
        String content = " 1. How to add a node?\n" +
                " Just press on any available space\n" +
                " 2. How to connect nodes?\n" +
                " Press them in order you want and type a weight of the rib\n" +
                " 3. How do I cancel a selection?\n" +
                " Press ESC\n" +
                " 4. How to change a weight?\n" +
                " Press on the weight and choose a new value\n";
        alert.setContentText(content);
        alert.show();
    }
}