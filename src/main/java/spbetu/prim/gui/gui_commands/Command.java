package spbetu.prim.gui.gui_commands;

import spbetu.prim.gui.viewmodel.GraphView;

public abstract class Command {
    public abstract boolean execute(GraphView viewModel);
}
