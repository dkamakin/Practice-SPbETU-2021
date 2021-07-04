package spbetu.prim.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.controller.WeightWindowController;

@Slf4j
public class WeightWindow extends Application {

    WeightWindowController controller;

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/weight_window.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Enter the weight");
            Scene scene = new Scene(root);
            scene.setOnKeyPressed(this::keyPressed);
            primaryStage.setScene(scene);
            primaryStage.show();
            controller = loader.getController();
        } catch (Exception e) {
            log.error("Couldn't open the weight window: " + e.getMessage());
        }
    }

    private void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            log.info("Enter button was pressed, setting up the weight");
            controller.doneButtonPressed();
        }
    }

    public String getText() {
        return controller.getText();
    }
}
