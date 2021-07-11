package spbetu.prim.gui.gui_commands.savefile_commands;

import spbetu.prim.gui.gui_commands.Command;
import spbetu.prim.gui.viewmodel.GraphView;

public class SaveAsClickedCommand extends Command {
    private String fileName;

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
