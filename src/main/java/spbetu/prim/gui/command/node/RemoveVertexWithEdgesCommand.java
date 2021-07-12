package spbetu.prim.gui.command.node;

import javafx.scene.Node;
import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class RemoveVertexWithEdgesCommand extends Command {

    Node node;

    public RemoveVertexWithEdgesCommand(Node node) {
        this.node = node;
    }

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.removeVertexWithEdges(node);
        return true;
    }

}
