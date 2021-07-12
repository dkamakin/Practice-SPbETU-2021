package spbetu.prim.gui.command.algorithm;

import spbetu.prim.gui.command.Command;
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
