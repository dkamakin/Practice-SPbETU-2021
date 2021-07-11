package spbetu.prim.cli.cli_commands.add_delete_commands;

import spbetu.prim.cli.cli_commands.Command;
import spbetu.prim.cli.viewmodel.ViewModel;
import spbetu.prim.exception.GraphInputException;

public class AddEdgeCommand extends Command {
    private final String edge;

    public AddEdgeCommand(String edge) {
        this.edge = edge;
    }

    @Override
    public boolean execute(ViewModel viewModel) throws GraphInputException {
        if (viewModel == null)
            return false;
        viewModel.addEdge(edge);
        return true;
    }
}
