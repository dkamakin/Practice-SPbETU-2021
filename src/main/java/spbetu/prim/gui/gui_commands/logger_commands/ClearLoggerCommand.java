package spbetu.prim.gui.gui_commands.logger_commands;

import spbetu.prim.gui.gui_commands.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class ClearLoggerCommand extends Command {
    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.clearLogger();
        return true;
    }

}
