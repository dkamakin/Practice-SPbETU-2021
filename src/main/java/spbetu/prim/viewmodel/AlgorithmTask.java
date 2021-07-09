package spbetu.prim.viewmodel;

import javafx.concurrent.Task;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlgorithmTask extends Task<Void> {

    private final GraphView graphView;
    private boolean isAlive;

    public AlgorithmTask(GraphView graphView) {
        this.graphView = graphView;
        this.isAlive = true;
    }

    @Override
    protected Void call() {
        while (!graphView.nextStep() && isAlive) {
            try {
                Thread.sleep(1000);
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
