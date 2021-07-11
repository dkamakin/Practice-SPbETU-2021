package spbetu.prim.gui.command.node;

import javafx.scene.layout.StackPane;
import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class ChooseNodeCommand extends Command {

    StackPane vertex;

    public ChooseNodeCommand(StackPane vertex) {
        this.vertex = vertex;
    }

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.chooseNode(vertex);
        return true;
    }
}
