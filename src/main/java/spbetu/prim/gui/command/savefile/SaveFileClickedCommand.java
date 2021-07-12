package spbetu.prim.gui.command.savefile;

import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class SaveFileClickedCommand extends Command {


    private final String fileName;

    public SaveFileClickedCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.saveGraphToFile(fileName);
        return true;
    }
}
