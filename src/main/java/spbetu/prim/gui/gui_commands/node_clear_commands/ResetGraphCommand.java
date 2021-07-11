package spbetu.prim.gui.gui_commands.node_clear_commands;

import spbetu.prim.gui.gui_commands.Command;
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
