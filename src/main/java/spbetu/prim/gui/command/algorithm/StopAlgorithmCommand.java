package spbetu.prim.gui.command.algorithm;

import spbetu.prim.gui.command.Command;
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
