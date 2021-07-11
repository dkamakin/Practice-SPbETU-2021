package spbetu.prim.gui.gui_commands.algorithm_commands;

import spbetu.prim.gui.gui_commands.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class NextStepCommand extends Command {

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null || !viewModel.nextStep())
            return false;
        return viewModel.nextStep();
    }
}
