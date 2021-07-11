package spbetu.prim.gui.gui_commands.logger_commands;

import spbetu.prim.gui.gui_commands.Command;
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
