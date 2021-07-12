package spbetu.prim.gui.command.algorithm;

import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class NextStepCommand extends Command {

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null || !viewModel.nextStep())
            return false;
        return viewModel.nextStep();
    }
}
