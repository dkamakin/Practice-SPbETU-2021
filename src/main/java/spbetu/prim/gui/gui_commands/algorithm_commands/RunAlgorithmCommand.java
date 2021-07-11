package spbetu.prim.gui.gui_commands.algorithm_commands;

import spbetu.prim.gui.gui_commands.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class RunAlgorithmCommand extends Command {
    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.runAlgorithm();
        return true;
    }
}
