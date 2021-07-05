package spbetu.prim.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.view.*;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class Controller implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public ScrollPane logTextArea;

    @FXML
    public AnchorPane secondAnchorPane;

    private GraphView view;
    private ActionType actionType;
    private boolean scrollPaneClickedFlag;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        view = new GraphView(new ScrollPaneLog(logTextArea));
        actionType = ActionType.ADD_NODE;
    }

    public void anchorPaneClicked(MouseEvent mouseEvent) {
        log.info("anchorPane was clicked");
        if (scrollPaneClickedFlag) {
            scrollPaneClickedFlag = false;
        } else if (actionType == ActionType.ADD_NODE) {
            log.info("Adding a node");
            StackPane stackPane = view.addNode(mouseEvent);
            anchorPane.getChildren().add(stackPane);
            stackPane.setOnMouseClicked(this::stackPaneClicked);
        } else if (actionType == ActionType.CHANGE_WEIGHT) {
            actionType = ActionType.ADD_NODE;
        }
    }

    public void stackPaneClicked(MouseEvent mouseEvent) {
        if (actionType == ActionType.DELETE) {
            log.info("Removing the stackPane");
            view.removeNode((StackPane) mouseEvent.getSource());
            return;
        } else if (actionType != ActionType.CONNECT_NODES) {
            log.info("StackPane clicked. The node was chosen");
            actionType = ActionType.CONNECT_NODES;
            view.chooseNode(mouseEvent);
            return;
        }

        log.info("The second node was chosen");
        Pane pane = view.addEdge(mouseEvent);

        if (pane == null)
            return;

        pane.getChildren().get(2).setOnMouseClicked(this::weightClicked);
        pane.getChildren().get(0).setOnMouseClicked(this::lineClicked);
        pane.setPickOnBounds(false);
        anchorPane.getChildren().add(pane);
        Text weightText = (Text) pane.getChildren().get(2);
        weightText.setText(askWeight());
    }

    public void lineClicked(MouseEvent mouseEvent) {
        log.info("Line was clicked");
        if (actionType == ActionType.DELETE) {
            log.info("Removing the line");
            view.removeNode((Line) mouseEvent.getSource());
        }
    }

    public void weightClicked(MouseEvent mouseEvent) {
        if (actionType == ActionType.DELETE) {
            log.info("Removing the weight");
            view.removeNode((Text) mouseEvent.getSource());
            return;
        }

        log.info("Weight was clicked");
        Text weightText = (Text) mouseEvent.getSource();
        weightText.setText(askWeight());
    }

    public String askWeight() {
        log.info("Showing the weight window");
        actionType = ActionType.CHANGE_WEIGHT;
        return new WeightWindow().getWeight();
    }

    public void clearClicked() {
        System.out.println("Clearing the scene");
        anchorPane.getChildren().remove(1, anchorPane.getChildren().size());
        view.clear();
    }

    public void deleteClicked() {
        log.info("Delete an item was clicked");
        if (actionType == ActionType.DELETE) {
            actionType = ActionType.ADD_NODE;
            return;
        }

        actionType = ActionType.DELETE;
    }

    public void scrollPaneClicked() {
        log.info("Scroll pane was clicked");
        scrollPaneClickedFlag = true;
    }

    public void clearLoggingArea() {
        view.clearLogging();
    }

    public void cancelSelection() {
        log.info("Escape button was pressed, clearing the choose of the node");
        actionType = ActionType.ADD_NODE;
    }

    public void aboutClicked() {
        log.info("About clicked");
        new AboutWindow().show();
    }

    public void faqClicked() {
        log.info("FAQ clicked");
        new FAQWindow().show();
    }
}