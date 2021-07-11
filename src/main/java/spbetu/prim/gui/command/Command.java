package spbetu.prim.gui.command;

import spbetu.prim.gui.viewmodel.GraphView;

public abstract class Command {
    public abstract boolean execute(GraphView viewModel);
}
