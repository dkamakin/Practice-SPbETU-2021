package spbetu.prim.gui.gui_commands.node_clear_commands;

import javafx.scene.Node;
import spbetu.prim.gui.gui_commands.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class RemoveEdgeByWeightCommand extends Command {
    Node node;

    public RemoveEdgeByWeightCommand(Node node) {
        this.node = node;
    }

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.removeEdgeByWeight(node);
        return true;
    }

}
