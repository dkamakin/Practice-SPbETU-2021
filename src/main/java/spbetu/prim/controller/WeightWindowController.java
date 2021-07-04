package spbetu.prim.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeightWindowController {

    @FXML
    private TextField textField;

    @FXML
    private Button closeButton;

    public String getText() {
        log.info("Returning text from WeightWindowController");
        return textField.getText();
    }

    public void doneButtonPressed() {
        log.info("Done button was pressed");
        closeWindow();
    }

    public void closeButtonPressed() {
        log.info("Close button was pressed");
        closeWindow();
    }

    public void closeWindow() {
        log.info("Closing the window");
        closeButton.getScene().getWindow().hide();
    }
}
