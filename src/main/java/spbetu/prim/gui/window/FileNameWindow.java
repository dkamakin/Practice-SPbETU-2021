package spbetu.prim.gui.window;

import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class FileNameWindow {

    public String getFileName() {
        Dialog<String> dialog = new TextInputDialog();
        dialog.setTitle("File Name");
        dialog.setHeaderText("Enter the file name: ");
        Optional<String> result = dialog.showAndWait();
        String entered = null;

        if (result.isPresent())
            entered = result.get();

        return entered;
    }
}
