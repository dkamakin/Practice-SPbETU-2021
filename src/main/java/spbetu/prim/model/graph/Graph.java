package spbetu.prim.model.graph;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Graph {

    private final ArrayList<Vertex> graph; // массив вершин (у каждой вершины номер и словарь ребер)

    public Graph() {
        graph = new ArrayList<>();
    }

    public List<Vertex> getGraph() {
        return graph;
    }

    public void clear() {
        graph.clear();
    }

    public int getSize() {
        return graph.size();
    }

    public Vertex getVertex(int index) {
        return graph.get(index);
    }

    public Graph cloneGraph() {                                // клонирование графа (на всякий случай)))
        Graph clone = new Graph();

        for (Vertex vertex : graph) {
            Vertex vertexClone = new Vertex(vertex.getNumber());
            clone.graph.add(vertexClone);
        }

        return clone;
    }

    public void deleteVertex(int indexVertex) {
        for (Vertex vertex : graph) {
            vertex.deleteEdgeFromDictionary(indexVertex);
        }
    }

    public void deleteEdge(int indexVertexFrom, int indexVertexTo) {
        for (Vertex vertex : graph) { // пробегаемся по вершинам графа
            if (vertex.getNumber() == indexVertexFrom) {  // если номер искомой вершины найден
                vertex.deleteEdgeFromDictionary(indexVertexTo);
            } else if (vertex.getNumber() == indexVertexTo) {  // и в обратную сторону
                vertex.deleteEdgeFromDictionary(indexVertexFrom);
            }
        }
    }

    public Edge addNewEdge(int indexVertex1, int indexVertex2, int edge12) { // две вершины и вес
        log.info("Adding edge from {} to {} with weight {}", indexVertex1, indexVertex2, edge12);

        checkIndex(indexVertex1); // добавляем вершину в граф, если она встречается первый раз
        checkIndex(indexVertex2);

        Vertex to = graph.get(indexVertex(indexVertex1));
        Vertex from = graph.get(indexVertex(indexVertex2));
        Edge edge = new Edge(edge12, to, from); // в принципе, не важно откуда куда, так как граф не направленный
        to.vertexAddEdge(from, edge);
        from.vertexAddEdge(to, edge);

        log.info("Created edge");
        return edge;
    }


    public boolean isDisconnected() { // возвращает true, если есть не посещенная вершина
        for (Vertex vertex : graph)
            if (!vertex.isVisited())
                return true;

        return false;
    }

    private int indexVertex(int number) {
        for (Vertex vertex : graph)
            if (vertex.getNumber() == number)
                return graph.indexOf(vertex);

        return -1;
    }

    private void checkIndex(int indexVertex) {
        if (indexVertex(indexVertex) < 0) {
            graph.add(new Vertex(indexVertex));
            log.info("Added new vertex with number " + indexVertex);
        }
    }

    void fillGraphFromFile(String fileName) throws FileNotFoundException {
        int indexVertex1;
        int indexVertex2;
        int edge12;
        String dash;
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextInt()) {
            indexVertex1 = scanner.nextInt();
            dash = scanner.next();

            System.out.print(indexVertex1);

            if (!dash.equals("-")) {
                log.info("Between vertexes you need to enter \"-\"");
                return;
            }

            System.out.print(dash);

            if (scanner.hasNextInt()) {
                indexVertex2 = scanner.nextInt();
            } else {
                log.info("Mistake of reading the second vertex");
                return;
            }

            System.out.print(indexVertex2);

            if (scanner.hasNextInt()) {
                edge12 = scanner.nextInt();
                if (edge12 > 32000)   // костыль
                    return;
            } else {
                scanner.next();
                log.info("Wrong weight");
                return;
            }

            System.out.println(" " + edge12);
            addNewEdge(indexVertex1, indexVertex2, edge12);
        }
    }
}
