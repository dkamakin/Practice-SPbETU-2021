package spbetu.prim.gui.viewmodel;

import javafx.application.Platform;
import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;
import spbetu.prim.logger.ILogger;

@Slf4j
public class AlgorithmTask extends Task<Void> {

    private final GraphView graphView;
    private final ILogger logger;
    private boolean isAlive;

    public AlgorithmTask(GraphView graphView, ILogger logger) {
        this.graphView = graphView;
        this.logger = logger;
        this.isAlive = true;
    }

    @Override
    protected Void call() {
        while (!graphView.nextStep() && isAlive) {
            try {
                Thread.sleep(1000);
                Platform.runLater(logger::update);
            } catch (InterruptedException e) {
                log.info("Couldn't stop the thread: " + e.getMessage());
                return null;
            }
        }

        return null;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
