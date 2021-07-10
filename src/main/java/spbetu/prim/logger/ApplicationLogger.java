package spbetu.prim.logger;

import spbetu.prim.gui.viewmodel.ScrollPaneLog;

public class ApplicationLogger implements ILogger {

    private final ScrollPaneLog scrollPaneLog;
    private StringBuilder message;

    public ApplicationLogger(ScrollPaneLog scrollPane) {
        scrollPaneLog = scrollPane;
        message = new StringBuilder();
    }

    @Override
    public <T> void info(T message) {
        scrollPaneLog.addNewMessage(message.toString());
    }

    @Override
    public <T> void append(T message) {
        this.message.append(message);
    }

    @Override
    public <T> void update() {
        info(message);
        this.message = new StringBuilder();
    }

    @Override
    public void clear() {
        scrollPaneLog.clear();
    }
}