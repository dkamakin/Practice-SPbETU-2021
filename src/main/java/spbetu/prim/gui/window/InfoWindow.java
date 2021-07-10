package spbetu.prim.gui.window;

import javafx.scene.control.Alert;

public class InfoWindow {

    public void show(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Info");
        alert.setContentText(msg);
        alert.show();
    }
}
