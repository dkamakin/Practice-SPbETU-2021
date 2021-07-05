package spbetu.prim.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.view.GraphView;
import spbetu.prim.view.ScrollPaneLog;
import spbetu.prim.view.WeightWindow;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    public ScrollPane logTextArea;
    public AnchorPane secondAnchorPane;

    private GraphView view;
    private ScrollPaneLog scrollPaneLog;
    private ActionType actionType;
    private WeightWindow weightWindow;
    private Text weightText;
    private boolean scrollPaneClickedFlag;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPaneLog = new ScrollPaneLog(logTextArea);
        view = new GraphView(scrollPaneLog);
        actionType = ActionType.ADD_NODE;
        weightWindow = null;
        weightText = null;
    }

    public void anchorPaneClicked(MouseEvent mouseEvent) {
        if(scrollPaneClickedFlag){
            scrollPaneClickedFlag = false;
            return;
        }
        if (actionType == ActionType.ADD_NODE) {
            StackPane stackPane = view.addNode(mouseEvent);
            anchorPane.getChildren().add(stackPane);
            stackPane.setOnMouseClicked(this::stackPaneClicked);
        }
    }

    public void stackPaneClicked(MouseEvent mouseEvent) {
        if (actionType == ActionType.DELETE) {
            return;
        } else if (actionType != ActionType.CONNECT_NODES) {
            actionType = ActionType.CONNECT_NODES;
            view.stackPaneClicked(mouseEvent);
            return;
        }

        Group group = view.addEdge(mouseEvent);

        if (group == null)
            return;

        group.getChildren().get(2).setOnMouseClicked(this::weightClicked);

        anchorPane.getChildren().add(group);
        weightText = (Text) group.getChildren().get(2);
        askWeight();
    }

    public void weightClicked(MouseEvent mouseEvent) {
        System.out.println("Weight clicked");
        actionType = ActionType.CHANGE_WEIGHT;
        weightText = (Text) mouseEvent.getSource();
        askWeight();
    }

    public void askWeight() {
        Stage stage = new Stage();
        stage.setOnHiding(this::weightWindowClosed);
        weightWindow = new WeightWindow();
        weightWindow.start(stage);
    }

    private void weightWindowClosed(WindowEvent windowEvent) {
        weightText.setText(weightWindow.getText());
        System.out.println("The weight is " + weightText.getText());
        actionType = ActionType.ADD_NODE;
    }

    public void clearClicked() {
        System.out.println("Clearing the scene");
        anchorPane.getChildren().remove(1, anchorPane.getChildren().size());
        view.clear();
    }

    public void escapePressed() {
        actionType = ActionType.ADD_NODE;
    }

    public void deleteClicked() {
        if (actionType == ActionType.DELETE) {
            actionType = ActionType.ADD_NODE;
            return;
        }

        actionType = ActionType.DELETE;
    }

    public void scrollPaneClicked(){
        scrollPaneClickedFlag = true;
    }

    public void clearLoggingArea(){
        view.clearLogging();
    }

    public void cancelSelection() {
        log.info("Escape button was pressed, clearing the choose of the node");
        actionType = ActionType.ADD_NODE;
    }
}