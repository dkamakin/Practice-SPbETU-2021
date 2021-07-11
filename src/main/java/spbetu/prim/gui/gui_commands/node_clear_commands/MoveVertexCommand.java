package spbetu.prim.gui.gui_commands.node_clear_commands;


import spbetu.prim.gui.gui_commands.Command;
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

