package spbetu.prim;

import spbetu.prim.cli.view.CliApplication;
import spbetu.prim.gui.viewmodel.GuiApplication;

public class Main {

    public static void main(String[] args) {
        for (String string : args) {
            if (string.equals("--gui")) {
                GuiApplication.main(args);
                return;
            } else if (string.equals("--cli")) {
                CliApplication.main(args);
                return;
            }
        }

        GuiApplication.main(args);
    }
}
