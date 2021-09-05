package spbetu.prim.model.loader;

import lombok.extern.slf4j.Slf4j;
import spbetu.prim.model.graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Slf4j
public class FileLoader implements ILoader {

    private final File file;

    public FileLoader(String fileName) throws FileNotFoundException {
        this.file = new File(fileName);

        if (!file.exists())
            throw new FileNotFoundException("Something went wrong during opening the file");
    }

    @Override
    public void loadGraph(Graph graph) {
        int vertexFrom;
        int vertexTo;
        double edge;
        String dash;

        try (Scanner scanner = new Scanner(file)) {
            log.info("Start reading");

            while (scanner.hasNextInt()) {
                vertexFrom = scanner.nextInt();
                dash = scanner.next();

                if (!dash.equals("-")) {
                    log.info("Between vertexes you need to enter \"-\"");
                    return;
                }

                if (scanner.hasNextInt()) {
                    vertexTo = scanner.nextInt();
                } else {
                    log.info("Mistake of reading the second vertex");
                    return;
                }

                if (scanner.hasNext()) {
                    dash = scanner.next();
                    edge = Double.parseDouble(dash);
                    if (edge > 32000)   // костыль
                        return;
                } else {
                    log.info("Wrong weight");
                    return;
                }

                graph.addNewEdge(vertexFrom, vertexTo, edge);
            }

            log.info("Done reading");
        } catch (FileNotFoundException e) {
            log.error("Couldn't read the file:  " + e.getMessage());
        }
    }
}