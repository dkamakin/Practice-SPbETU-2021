package spbetu.prim.model.algorithm;

import spbetu.prim.logger.ConsoleLogger;
import spbetu.prim.logger.ILogger;
import spbetu.prim.model.graph.Edge;
import spbetu.prim.model.graph.Graph;
import spbetu.prim.model.graph.Vertex;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class PrimAlgorithm {

    private static PrimAlgorithm instance;
    private final Graph graph;  // сам граф
    private final Stack<Edge<Double>> spanningTree; // остовное дерево (вершина куда (последняя посещенная) и ребро в нее)
    private final ILogger logger;

    public PrimAlgorithm(Graph graph) {  // можно ли использовать singleton в тестах?
        this.graph = graph;
        this.logger = new ConsoleLogger();
        this.spanningTree = new Stack<>();
    }

    private PrimAlgorithm(Graph graph, ILogger logger) {
        this.graph = graph;
        this.logger = logger;
        this.spanningTree = new Stack<>();
    }

    public static PrimAlgorithm getInstanceWithLogger(Graph graph, ILogger logger) {
        if (instance == null) {
            instance = new PrimAlgorithm(graph, logger);
        }

        return instance;
    }

    public void runAlgorithm() {
        if (graph.getSize() > 0) {
            graph.getVertex(0).setVisited(true);  // посещаем первую вершину
        } else
            return;

        while (graph.isDisconnected()) { // пока есть не посещенная вершина (должны в итоге посетить все вершины)
            runAlgorithmByStep();
        }
    }

    public Edge<Double> runAlgorithmByStep() {
        if (graph.getSize() > 0) {
            graph.getVertex(0).setVisited(true);  // посещаем первую вершину
        } else return null;

            Edge<Double> nextMinimumEdge = new Edge<>(Double.MAX_VALUE, null, null); // для минимального ребра

            Vertex nextVertex = graph.getVertex(0);  // следующая вершина, куда перейдем (инициализируем первой, т.к. она посещена в самом начале)

            Vertex thisVertex = null;

            for (Vertex vertex : graph.getVertices()) { // рассматриваем все вершины графа

                if (vertex.isVisited()) {  // если вершина посещена
                    logger.append("\nVertex № " + vertex.getNumber() + " is already visited.\n" +
                            "\tLooking at edges of " + vertex.getNumber() + " vertex" + '\n');

                    Edge<Double> candidate = vertex.getMinimum();
                    if (candidate.getVertexTo() != null && candidate.getVertexFrom() != null)
                        logger.append("The minimum not included edge of " + vertex.getNumber() + " vertex:\n "
                                + candidate.getVertexFrom().getNumber() + "-" + candidate.getVertexTo().getNumber() +
                                " " + candidate.getWeight() + '\n');
                    else {
                        logger.append("Vertex № " + vertex.getNumber() + " doesn't have not included edges.\n");
                        continue;
                    }


                    if (candidate.getWeight() < nextMinimumEdge.getWeight()) { // проверка на минимум
                        nextMinimumEdge = candidate;
                        nextVertex = candidate.getNextVertex();
                        thisVertex = vertex;
                    }
                }
            }

            if (nextMinimumEdge.getVertexTo() == null || nextMinimumEdge.getVertexFrom() == null)
                return null;

            nextMinimumEdge.setIncluded(true);  // ребро включили
            nextVertex.setVisited(true); // вершину посетили

            addEdgeToSpanningTree(thisVertex, nextVertex, nextMinimumEdge.getWeight());  // добавили в остов

            logger.append("\tADDED TO SPANNING TREE:\n " + nextMinimumEdge.getVertexFrom().getNumber()
                    + "-" + nextMinimumEdge.getVertexTo().getNumber() + " " + nextMinimumEdge.getWeight() + '\n');
            logger.append("Vertex № " + nextVertex.getNumber() + " is visited now\n");

            return nextMinimumEdge;

    }



    public void addEdgeToSpanningTree(Vertex from, Vertex to, Double edgeWeight) {
        Edge<Double> edge = new Edge<>(edgeWeight, from, to);
        spanningTree.push(edge);
    }



    public Edge<Double> previousStep() {
        if (spanningTree.empty())
            return null;

        Edge<Double> lastEdge = spanningTree.pop();  // достали вершину из остова
        lastEdge.getVertexTo().setVisited(false); // отметили, что не посетили вершину to

        Vertex from = lastEdge.getVertexFrom();
        Vertex to = lastEdge.getVertexTo();

        if (from == null || to == null)
            return null;

        from.forPreviousStep(to);  // отмечаем, что ребро не посещено!
        to.forPreviousStep(from);  // в обе стороны

        logger.info("Going back");
        return lastEdge;
    }

    public void clearGraph() {    //для очистки данных графа, полностью новый ввод
        logger.info("Clearing the graph");
        graph.clear();
        spanningTree.clear();
    }

    public void restart() {
        logger.info("Restarting the algorithm");
        for (Vertex vertex : graph.getVertices()) {
            vertex.setVisited(false); // // теперь вершина не посещенная
            Set<HashMap.Entry<Vertex, Edge<Double>>> set = vertex.getEdges().entrySet();
            for (HashMap.Entry<Vertex, Edge<Double>> me : set)
                me.getValue().setIncluded(false);    // ребра являются не включенными
        }
        spanningTree.clear();
    }

    public void writeToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false))  // файл перезаписывается
        {
            writer.write("Source graph:\n");
            for (Vertex vertex : graph.getVertices()) {
                Set<HashMap.Entry<Vertex, Edge<Double>>> set = vertex.getEdges().entrySet(); // словарь ребер вершины
                for (HashMap.Entry<Vertex, Edge<Double>> edge : set) {
                    writer.write(vertex.getNumber() +
                            "-" + edge.getKey().getNumber() + " " + edge.getValue().getWeight());
                    writer.write('\n');
                }
            }

            writer.write("Found spanning tree:\n");
            for (Edge<Double> res : spanningTree) {
                writer.write(res.getVertexFrom().getNumber() +
                        "-" + res.getVertexTo().getNumber() + " " + res.getWeight());
                writer.append('\n');
            }

            writer.flush();
        } catch (IOException e) {
            logger.info("Couldn't write to file: " + e.getMessage());
        }
    }

    public Stack<Edge<Double>> getSpanningTree() {
        return spanningTree;
    }

    public Graph getGraph() {
        return graph;
    }
}

