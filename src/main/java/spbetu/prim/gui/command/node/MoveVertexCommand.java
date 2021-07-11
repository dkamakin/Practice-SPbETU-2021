package spbetu.prim.gui.command.node;


import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class MoveVertexCommand extends Command {

    private final double xCoordinate;
    private final double yCoordinate;

    public MoveVertexCommand(double x, double y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.moveVertex(xCoordinate, yCoordinate);
        return true;
    }
}

