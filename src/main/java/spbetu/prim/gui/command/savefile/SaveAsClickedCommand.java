package spbetu.prim.gui.command.savefile;

import spbetu.prim.gui.command.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class SaveAsClickedCommand extends Command {

    private final String fileName;

    public SaveAsClickedCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean execute(GraphView viewModel) {
        if (viewModel == null)
            return false;
        viewModel.setFileName(fileName);
        viewModel.saveGraphToFile(fileName);
        return true;
    }
}
