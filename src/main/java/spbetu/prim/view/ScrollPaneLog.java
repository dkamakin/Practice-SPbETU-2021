package spbetu.prim.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ScrollPaneLog {

    private Text text;
    private ScrollPane scrollPaneLog;

    public ScrollPaneLog(ScrollPane scrollPane){
        text = new Text();
        text.setFont(Font.font("Times new roman", FontWeight.NORMAL,16));
        scrollPaneLog = scrollPane;
        scrollPaneLog.setContent(text);
    }

    public void addNodeMessage(String value){
        text.setText(text.getText() + "New Node " + value + '\n');
    }

    public void addEdgeMessage(String node1, String node2){
        text.setText(text.getText() + "New Edge between Node " + node1 + " and Node " + node2 + '\n');
    }

    public void clearingGraphMessage(){
        text.setText(text.getText() + "Clearing the Graph\n");
    }

    public void nodeClickedMessage(String node){
        text.setText(text.getText() + "Clicked Node " + node + '\n');
    }

    public void clear(){
        text.setText("");
    }

}
