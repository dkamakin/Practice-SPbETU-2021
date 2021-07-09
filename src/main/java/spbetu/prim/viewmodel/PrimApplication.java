package spbetu.prim.viewmodel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.view.View;

@Slf4j
public class PrimApplication extends Application {

    private View controller;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        primaryStage.setTitle("Prim's algorithm");
        Scene scene = new Scene(root, 1300, 900);
        scene.setOnKeyPressed(this::keyPressed);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/icon.jpeg"));
        primaryStage.show();
    }

    private void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            controller.cancelSelection();
        }
    }
}
