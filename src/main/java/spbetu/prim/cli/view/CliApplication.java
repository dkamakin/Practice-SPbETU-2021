package spbetu.prim.cli.view;

import lombok.extern.slf4j.Slf4j;
import spbetu.prim.cli.command.Command;
import spbetu.prim.cli.command.algorithm.NextStepCommand;
import spbetu.prim.cli.command.algorithm.PreviousStepCommand;
import spbetu.prim.cli.command.algorithm.RunAlgorithmCommand;
import spbetu.prim.cli.command.edge.AddEdgeCommand;
import spbetu.prim.cli.command.edge.DeleteEdgeCommand;
import spbetu.prim.cli.command.file.ReadFromFileCommand;
import spbetu.prim.cli.command.file.SaveToFileCommand;
import spbetu.prim.cli.viewmodel.ViewModel;
import spbetu.prim.exception.GraphInputException;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class CliApplication {

    private static void outputHelp() {
        System.out.println("Choose one of the following actions: ");
        System.out.print("1. Add an edge to the graph\n" +
                "2. Delete an edge from the graph\n" +
                "3. Next step\n" +
                "4. Prev step\n" +
                "5. Run algorithm\n" +
                "6. Output graph\n" +
                "7. Output the tree\n" +
                "8. Read from file\n" +
                "9. Write to file\n" +
                "0. Exit\n" +
                "Your action: ");
    }

    public static void main(String[] args) throws GraphInputException {
        ViewModel viewModel = new ViewModel();
        Scanner scanner = new Scanner(System.in);
        String action;
        Command command;

        outputHelp();
        while (!(action = scanner.next()).equals("0")) {
            scanner.nextLine();
            switch (action) {
                case "1":
                    try {
                        System.out.print("Input an edge with weight (example: 1 - 2 3): ");

                        // command
                        command = new AddEdgeCommand(scanner.nextLine());
                        command.execute(viewModel);

                    } catch (GraphInputException e) {
                        log.error(e.getMessage());
                    }

                    break;

                case "2":
                    try {
                        System.out.print("Input and edge (example: 1 - 2): ");
                        // command
                        command = new DeleteEdgeCommand(scanner.nextLine());
                        command.execute(viewModel);


                    } catch (GraphInputException e) {
                        log.error(e.getMessage());
                    }
                    break;

                case "3":
                    command = new NextStepCommand();
                    command.execute(viewModel);
                    break;

                case "4":
                    command = new PreviousStepCommand();
                    command.execute(viewModel);
                    break;

                case "5":

                    command = new RunAlgorithmCommand();
                    command.execute(viewModel);
                    break;

                case "6":
                    List<String> edgeList = viewModel.outputGraph();
                    log.info("Current graph: ");
                    for (String edge : edgeList) {
                        log.info(edge);
                    }
                    break;

                case "7":
                    List<String> edgeStack = viewModel.outputTree();
                    log.info("Current tree: ");
                    for (String string : edgeStack) {
                        log.info(string);
                    }
                    break;

                case "8":
                    System.out.print("Input path to the file: ");

                    command = new ReadFromFileCommand(scanner.nextLine());
                    command.execute(viewModel);
                    break;

                case "9":
                    System.out.print("Input path to the file: ");

                    command = new SaveToFileCommand(scanner.nextLine());
                    command.execute(viewModel);
                    break;

                default:
                    break;
            }

            System.out.println();
            outputHelp();
        }
    }
}
