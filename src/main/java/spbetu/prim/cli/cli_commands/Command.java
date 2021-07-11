package spbetu.prim.cli.cli_commands;

import spbetu.prim.cli.viewmodel.ViewModel;
import spbetu.prim.exception.GraphInputException;

public abstract class Command {
    public abstract boolean execute(ViewModel viewModel) throws GraphInputException;
}
