package spbetu.prim.view;

import javafx.scene.control.Alert;

public class FAQWindow {

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FAQ");
        alert.setHeaderText("FAQ");
        String content = """
                 1. How to add a node?
                 Just press the working space
                 2. How to connect nodes?
                 Press them in order you want and type a weight of the rib
                 3. How do I deselect a vertex?
                 Press ESC
                 4. How to delete an item?
                 Choose the option in Action menu
                 5. How to change a weight?
                 Press on the number
                 6. How to clear the scene?
                 Choose the option in Action menu
                """;
        alert.setContentText(content);
        alert.show();
    }
}