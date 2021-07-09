package spbetu.prim.logger;

import spbetu.prim.viewmodel.ScrollPaneLog;

public class ApplicationLogger implements ILogger {

    private final ScrollPaneLog scrollPaneLog;

    public ApplicationLogger(ScrollPaneLog scrollPane) {
        scrollPaneLog = scrollPane;
    }

    public <T> void info(T message) {
        scrollPaneLog.addNewMessage(message.toString());
    }

    public void clear() {
        scrollPaneLog.clear();
    }
}