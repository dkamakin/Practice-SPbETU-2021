package spbetu.prim.cli.cli_commands.algorithm_commands;

import spbetu.prim.cli.cli_commands.Command;
import spbetu.prim.cli.viewmodel.ViewModel;
import spbetu.prim.exception.GraphInputException;

public class NextStepCommand extends Command {

    @Override
    public boolean execute(ViewModel viewModel) throws GraphInputException {
        if (viewModel == null)
            return false;
        viewModel.nextStep();
        return true;
    }
}
