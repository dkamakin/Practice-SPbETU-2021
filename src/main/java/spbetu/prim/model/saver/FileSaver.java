package spbetu.prim.model.saver;

import lombok.extern.slf4j.Slf4j;
import spbetu.prim.model.algorithm.PrimAlgorithm;
import spbetu.prim.model.graph.Edge;
import spbetu.prim.model.graph.Graph;
import spbetu.prim.model.graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

@Slf4j
public class FileSaver implements ISaver {

    private final File file;

    public FileSaver(String fileName) throws FileNotFoundException {
        file = new File(fileName);

        if (!file.exists())
            throw new FileNotFoundException("Something went wrong during opening the file");
    }

    @Override
    public void saveGraph(Graph graph, PrimAlgorithm primAlgorithm) {
        try (FileWriter writer = new FileWriter(file, false)) {
            for (Vertex vertex : graph.getVertices()) {
                Set<HashMap.Entry<Vertex, Edge<Double>>> set = vertex.getEdges().entrySet();
                for (HashMap.Entry<Vertex, Edge<Double>> edge : set) {
                    writer.write(vertex.getNumber() +
                            " - " + edge.getKey().getNumber() + " " + edge.getValue().getWeight());
                    writer.write('\n');
                }
            }

            writer.write("Found spanning tree:\n");
            for (Edge<Double> res : primAlgorithm.getSpanningTree()) {
                writer.write(res.getVertexFrom().getNumber() +
                        " - " + res.getVertexTo().getNumber() + " " + res.getWeight());
                writer.append('\n');
            }

            writer.flush();
        } catch (IOException e) {
            log.info("Couldn't write to file: " + e.getMessage());
        }

    }

}
