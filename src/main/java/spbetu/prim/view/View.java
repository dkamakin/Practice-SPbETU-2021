package spbetu.prim.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.loggers.GraphLogger;
import spbetu.prim.viewmodel.GraphView;
import spbetu.prim.viewmodel.ScrollPaneLog;
import spbetu.prim.window.AboutWindow;
import spbetu.prim.window.FAQWindow;
import spbetu.prim.window.InfoWindow;
import spbetu.prim.window.WeightWindow;

import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class View implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public ScrollPane logTextArea;

    @FXML
    public AnchorPane secondAnchorPane;

    private GraphView viewModel;
    private ActionType actionType;
    private boolean scrollPaneClickedFlag;
    private ScrollPaneLog scrollPaneLog;
    private GraphLogger graphLogger;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = new GraphView();
        actionType = ActionType.ADD_NODE;

        scrollPaneLog = new ScrollPaneLog(logTextArea); //ScrollPane - Вывод информации на окно с логами
        graphLogger = new GraphLogger(scrollPaneLog);   // Добавление через ScrollPane информации о графе
    }

    public void anchorPaneClicked(MouseEvent mouseEvent) {
        log.info("anchorPane was clicked");
        if (scrollPaneClickedFlag) {
            scrollPaneClickedFlag = false;
        } else if (actionType == ActionType.ADD_NODE) {
            log.info("Adding a node");
            StackPane stackPane = viewModel.addNode(mouseEvent);
            anchorPane.getChildren().add(stackPane);
            stackPane.setOnMouseClicked(this::stackPaneClicked);
        } else if (actionType == ActionType.CHANGE_WEIGHT) {
            actionType = ActionType.ADD_NODE;
        }
    }

    public void stackPaneClicked(MouseEvent mouseEvent) {
        if (actionType == ActionType.DELETE) {
            log.info("Removing the stackPane");
            viewModel.removeVertexWithEdges((StackPane) mouseEvent.getSource());
            return;
        } else if (actionType != ActionType.CONNECT_NODES) {
            log.info("StackPane clicked. The node was chosen");
            actionType = ActionType.CONNECT_NODES;
            viewModel.chooseNode(mouseEvent);
            return;
        }

        log.info("The second node was chosen");
        Pane pane = viewModel.addEdge(mouseEvent, askWeight());

        if (pane == null)
            return;

        pane.getChildren().get(2).setOnMouseClicked(this::weightClicked);
        pane.setPickOnBounds(false);
        anchorPane.getChildren().add(pane);
    }

    public void weightClicked(MouseEvent mouseEvent) {
        if (actionType == ActionType.DELETE) {
            log.info("Removing the weight");
            viewModel.removeEdgeByWeight((Text) mouseEvent.getSource());
            return;
        }

        log.info("Weight was clicked");
        Text weightText = (Text) mouseEvent.getSource();
        String newWeight = askWeight();
        if (viewModel.checkWeight(newWeight))
            weightText.setText(newWeight);
        else
            log.warn("Wrong weight");
    }

    public String askWeight() {
        log.info("Showing the weight window");
        actionType = ActionType.CHANGE_WEIGHT;
        return new WeightWindow().getWeight();
    }

    public void clearClicked() {
        log.info("Clearing the scene");
        anchorPane.getChildren().remove(2, anchorPane.getChildren().size());
        viewModel.clear();
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
        scrollPaneLog.clear();      //Очистка окна с логами
    }

    public void cancelSelection() {
        if (actionType != ActionType.CONNECT_NODES)
            return;

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

    public void nextStepClicked() {
        log.info("Next step in algorithm");
        if (viewModel.nextStep())
            new InfoWindow().show("The minimum spanning tree was found");
    }

    public void runClicked() {
        log.info("Run algorithm");
        viewModel.runAlgorithm();
    }

    public void resetClicked() {
        log.info("Reset the algorithm");
        viewModel.resetGraph();
    }

    public void prevStepClicked() {
        log.info("Prev step clicked");
        viewModel.previousStep();
    }

    public void stopPressed() {
        log.info("Stop algorithm");
        viewModel.stopAlgorithm();
    }
}