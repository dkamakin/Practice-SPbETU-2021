package spbetu.prim.gui.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.exception.GraphInputException;
import spbetu.prim.gui.viewmodel.EdgeView;
import spbetu.prim.gui.viewmodel.GraphView;
import spbetu.prim.gui.window.*;

import java.io.File;
import java.io.IOException;
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

    private GraphView viewModel;
    private ActionType actionType;
    private boolean scrollPaneClickedFlag;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.viewModel = new GraphView(logTextArea);
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
        Pane pane;
        try {
            pane = viewModel.addEdge((StackPane) mouseEvent.getSource(), askWeight());
        } catch (GraphInputException e) {
            new InfoWindow().show(e.getMessage());
            return;
        }

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
        viewModel.clearLogger();
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

        viewModel.updateLogger();
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
        fileChooser.setTitle("Choose file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = fileChooser.showOpenDialog(ownerWindow);

        if (file == null)
            return null;

        return file.getPath();
    }

    public void openClicked() {
        String fileName = getFileName(anchorPane.getScene().getWindow());

        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        clearClicked();

        List<EdgeView> graphFromFile = viewModel.readGraphFromFile(
                fileName,
                (int) (anchorPane.getScene().getWidth() / 2.5),
                (int) (anchorPane.getScene().getHeight() / 2.3)
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
            pane.setPickOnBounds(false);

            anchorPane.getChildren().add(pane);
        }
    }

    public void saveClicked(){

        if(viewModel.getFileName() == null){
            saveAsClicked();
            return;
        }

        String fileName = viewModel.getFileName();
        viewModel.saveGraphToFile(fileName);
    }

    public void saveAsClicked(){
        String fileName = getFileName(anchorPane.getScene().getWindow());
        viewModel.setFileName(fileName);
        viewModel.saveGraphToFile(fileName);
    }

    public String getDirectoryName(Window ownerWindow){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory");
        File file = directoryChooser.showDialog(ownerWindow);

        if (file == null)
            return null;

        return file.getPath();
    }

    public String askFileName() {
        log.info("Showing the weight window");
        return new FileNameWindow().getFileName();
    }

    public void newFileClicked() throws IOException {
        StringBuilder fileName = new StringBuilder(getDirectoryName(anchorPane.getScene().getWindow()));

        if(fileName.isEmpty()){
            log.info("Couldn't open the directory");
            return;
        }

        fileName.append("/" + askFileName() + ".txt");
        File file = new File(fileName.toString());
        if(file.createNewFile()){
            log.info("Create new file");
        }else {
            log.info("File already exists");
        }

        viewModel.setFileName(fileName.toString());
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