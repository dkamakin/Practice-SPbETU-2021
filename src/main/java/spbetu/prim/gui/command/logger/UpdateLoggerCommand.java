package spbetu.prim.gui.command.logger;

import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class UpdateLoggerCommand extends Command {

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.updateLogger();
        return true;
    }
}
