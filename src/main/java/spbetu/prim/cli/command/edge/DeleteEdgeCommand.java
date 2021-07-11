package spbetu.prim.cli.command.edge;

import spbetu.prim.cli.command.Command;
import spbetu.prim.cli.viewmodel.ViewModel;
import spbetu.prim.exception.GraphInputException;

public class DeleteEdgeCommand extends Command {

    private final String edge;

    public DeleteEdgeCommand(String edge) {
        this.edge = edge;
    }

    @Override
    public boolean execute(ViewModel viewModel) throws GraphInputException {
        if (viewModel == null)
            return false;
        viewModel.deleteEdge(edge);
        return true;
    }
}
