package spbetu.prim.gui.gui_commands.algorithm_commands;

import spbetu.prim.gui.gui_commands.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class StopAlgorithmCommand extends Command {
    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.stopAlgorithm();
        return true;
    }
}
