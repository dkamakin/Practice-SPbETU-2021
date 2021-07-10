package spbetu.prim.gui.window;

import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class WeightWindow {

    public String getWeight() {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("Weight");
        dialog.setHeaderText("Enter the weight: ");
        Optional<String> result = dialog.showAndWait();
        String entered = null;

        if (result.isPresent())
            entered = result.get();

        return entered;
    }
}