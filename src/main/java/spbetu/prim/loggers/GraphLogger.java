package spbetu.prim.loggers;

import spbetu.prim.view.ScrollPaneLog;

public class GraphLogger {

    private final ScrollPaneLog scrollPaneLog;

    public GraphLogger(ScrollPaneLog scrollPane){
        scrollPaneLog = scrollPane;
    }

    public <T> void addNodeMessage(T value) {
        scrollPaneLog.addNewMessage("New Node " + value);
    }

    public <T1, T2> void addEdgeMessage(T1 node1, T1 node2, T2 weight) {
        scrollPaneLog.addNewMessage("New Edge between Node " + node1 + " and Node " + node2
                + " with weight " + weight);
    }

    public void clearingGraphMessage() {
        scrollPaneLog.addNewMessage("Clearing the Graph");
    }

    public <T> void nodeClickedMessage(T node) {
        scrollPaneLog.addNewMessage("Clicked Node " + node);
    }

    public <T> void setUnvisitedNodeMessage(T node){
        scrollPaneLog.addNewMessage("Setting an unvisited Node " + node);
    }

    public <T> void setVisitedNodeMessage(T node){
        scrollPaneLog.addNewMessage("Setting an visited Node " + node);
    }

    public <T> void lookingEdgesMessage(T node){
        scrollPaneLog.addNewMessage("Looking at edges of " + node + " vertex");
    }

    public <T1, T2> void setVisitedEdgeMessage(T1 node1, T1 node2, T2 weight){
        scrollPaneLog.addNewMessage("Add edge " + node1 + " to " + node2 + " with weight " + weight);
    }

    public void startAlgorithmAgainMessage(){
        scrollPaneLog.addNewMessage("Start Algorithm again");
    }

}