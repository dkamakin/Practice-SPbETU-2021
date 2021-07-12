package spbetu.prim.gui.command.node;

import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class ResetGraphCommand extends Command {

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.resetGraph();
        return true;
    }
}
