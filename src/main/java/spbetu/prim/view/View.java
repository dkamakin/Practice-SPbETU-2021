package spbetu.prim.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.logger.ApplicationLogger;
import spbetu.prim.logger.ILogger;
import spbetu.prim.viewmodel.EdgeView;
import spbetu.prim.viewmodel.GraphView;
import spbetu.prim.viewmodel.ScrollPaneLog;
import spbetu.prim.window.AboutWindow;
import spbetu.prim.window.FAQWindow;
import spbetu.prim.window.InfoWindow;
import spbetu.prim.window.WeightWindow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Slf4j
public class View implements Initializable {

    @FXML
    public ScrollPane logTextArea;

    @FXML
    public AnchorPane secondAnchorPane;

    @FXML
    public AnchorPane anchorPane;
    public ScrollPaneLog scrollPaneLog;
    private GraphView viewModel;
    private ActionType actionType;
    private ILogger logger;
    private boolean scrollPaneClickedFlag;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.scrollPaneLog = new ScrollPaneLog(logTextArea);
        this.logger = new ApplicationLogger(new ScrollPaneLog(logTextArea));
        this.viewModel = new GraphView();
        this.actionType = ActionType.ADD_NODE;
    }

    public void anchorPaneClicked(MouseEvent mouseEvent) {
        log.info("anchorPane was clicked, action = " + actionType);

        if (scrollPaneClickedFlag) {
            scrollPaneClickedFlag = false;
            return;
        }

        switch (actionType) {
            case ADD_NODE:
                log.info("Adding a node");
                StackPane stackPane = viewModel.addNode(
                        mouseEvent.getX(), mouseEvent.getY()
                );
                anchorPane.getChildren().add(stackPane);
                stackPane.setOnMouseClicked(this::stackPaneClicked);
                break;
            case CHANGE_WEIGHT:
                actionType = ActionType.ADD_NODE;
                break;
            case MOVE:
                viewModel.moveVertex(mouseEvent.getX(), mouseEvent.getY());
                break;
            default:
                break;
        }
    }

    public void stackPaneClicked(MouseEvent mouseEvent) {
        if (actionType == ActionType.DELETE) {
            log.info("Removing the stackPane");
            viewModel.removeVertexWithEdges((StackPane) mouseEvent.getSource());
            return;
        } else if (actionType == ActionType.MOVE_CHOOSE ||
                actionType == ActionType.MOVE) {
            log.info("Chose the vertex to move");
            actionType = ActionType.MOVE;
            viewModel.chooseNode((StackPane) mouseEvent.getSource());
            return;
        } else if (actionType != ActionType.CONNECT_NODES) {
            log.info("StackPane clicked. The node was chosen");
            actionType = ActionType.CONNECT_NODES;
            viewModel.chooseNode((StackPane) mouseEvent.getSource());
            return;
        }

        log.info("The second node was chosen");
        Pane pane = viewModel.addEdge((StackPane) mouseEvent.getSource(), askWeight());

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
        logger.clear();      //Очистка окна с логами
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

    public String getFileName(Window ownerWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");

        return fileChooser.showOpenDialog(ownerWindow).getPath();
    }

    public void openClicked() {
        String fileName = getFileName(anchorPane.getScene().getWindow());

        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        List<EdgeView> graphFromFile = viewModel.readGraphFromFile(
                fileName
        );

        for (EdgeView elem : graphFromFile) {
            elem.getWeight().setOnMouseClicked(this::weightClicked);
            elem.getFrom().setOnMouseClicked(this::stackPaneClicked);
            elem.getTo().setOnMouseClicked(this::stackPaneClicked);

            Pane pane = new Pane();

            pane.getChildren().addAll(
                    elem.getLine(),
                    elem.getFrom(),
                    elem.getWeight(),
                    elem.getTo()
            );

            pane.setLayoutY(anchorPane.getScene().getHeight() / 2);
            pane.setLayoutX(anchorPane.getScene().getWidth() / 2);

            anchorPane.getChildren().add(pane);
        }
    }

    public void moveClicked() {
        log.info("Move was clicked");
        if (actionType == ActionType.MOVE || actionType == ActionType.MOVE_CHOOSE) {
            actionType = ActionType.ADD_NODE;
            return;
        }

        actionType = ActionType.MOVE_CHOOSE;
    }
}