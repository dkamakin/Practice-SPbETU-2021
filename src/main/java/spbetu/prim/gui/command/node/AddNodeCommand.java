package spbetu.prim.gui.command.node;

import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class AddNodeCommand extends Command {

    private final double xCoordinate;
    private final double yCoordinate;

    public AddNodeCommand(double x, double y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null || xCoordinate < 0 || yCoordinate < 0)
            return false;
        viewModel.addNode(xCoordinate, yCoordinate);
        return true;
    }
}
