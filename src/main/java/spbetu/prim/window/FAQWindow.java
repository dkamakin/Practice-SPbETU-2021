package spbetu.prim.window;

import javafx.scene.control.Alert;

public class FAQWindow {

    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FAQ");
        alert.setHeaderText("FAQ");
        String content = " 1. How to add a node?\n" +
                " Just press the working space\n" +
                " 2. How to connect nodes?\n" +
                " Press them in order you want and type a weight of the rib\n" +
                " 3. How do I deselect a vertex?\n" +
                " Press ESC\n" +
                " 4. How to delete an item?\n" +
                " Choose the option in Action menu\n" +
                " 5. How to change a weight?\n" +
                " Press on the number\n" +
                " 6. How to clear the scene?\n" +
                " Choose the option in Action menu\n";
        alert.setContentText(content);
        alert.show();
    }
}