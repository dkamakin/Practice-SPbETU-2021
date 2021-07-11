package spbetu.prim.cli.command.file;

import spbetu.prim.cli.command.Command;
import spbetu.prim.cli.viewmodel.ViewModel;
import spbetu.prim.exception.GraphInputException;

public class SaveToFileCommand extends Command {

    private final String fileName;

    public SaveToFileCommand(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean execute(ViewModel viewModel) throws GraphInputException {
        if (viewModel == null)
            return false;
        viewModel.saveGraphToFile(fileName);
        return true;
    }
}
