package spbetu.prim.model.algorithm;

import lombok.extern.slf4j.Slf4j;
import spbetu.prim.model.graph.Edge;
import spbetu.prim.model.graph.Graph;
import spbetu.prim.model.graph.Vertex;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

@Slf4j
public class PrimAlgorithm {

    private final Graph graph;  // сам граф

    private final Stack<Edge> spanningTree; // остовное дерево (вершина куда (последняя посещенная) и ребро в нее)

    public PrimAlgorithm(Graph graph) {
        this.graph = graph;
        spanningTree = new Stack<>();
    }

    public void runAlgorithm() {
        if (graph.getSize() > 0) {
            graph.getVertex(0).setVisited(true);  // посещаем первую вершину
            log.info(graph.getVertex(0).getNumber() + " vertex was visited");
        } else
            return;

        while (graph.isDisconnected()) { // пока есть не посещенная вершина (должны в итоге посетить все вершины)
            runAlgorithmByStep();
        }
    }

    public Edge runAlgorithmByStep() {
        if (graph.getSize() > 0) {
            graph.getVertex(0).setVisited(true);  // посещаем первую вершину
            log.info(graph.getVertex(0).getNumber() + " vertex was visited");
        } else
            return null;

        Edge nextMinimumEdge = new Edge(Integer.MAX_VALUE, null, null); // для минимального ребра
        // ?????? как быть с generic
        Vertex nextVertex = graph.getVertex(0);  // следующая вершина, куда перейдем (инициализируем первой, т.к. она посещена в самом начале)

        Vertex thisVertex = null;

        for (Vertex vertex : graph.getGraph()) { // рассматриваем все вершины графа

            if (vertex.isVisited()) {  // если вершина посещена
                log.info("Looking at edges of " + vertex.getNumber() + " vertex");
                Edge candidate = vertex.getMinimum();

                if (candidate.getWeight() < nextMinimumEdge.getWeight()) { // проверка на минимум
                    nextMinimumEdge = candidate;
                    nextVertex = candidate.getNextVertex();
                    thisVertex = vertex;
                }
            }

        }

        nextMinimumEdge.setIncluded(true);  // ребро включили
        nextVertex.setVisited(true); // вершину посетили

        addEdgeToSpanningTree(thisVertex, nextVertex, nextMinimumEdge.getWeight());  // добавили в остов

        if (nextMinimumEdge.getVertexTo() == null || nextMinimumEdge.getVertexFrom() == null)
            return null;

        log.info("In result added edge " + nextMinimumEdge.getVertexFrom().getNumber()
                + "-" + nextMinimumEdge.getVertexTo().getNumber() + " with weight " + nextMinimumEdge.getWeight());
        log.info(nextVertex.getNumber() + " vertex was visited");

        return nextMinimumEdge;
    }

    public void addEdgeToSpanningTree(Vertex from, Vertex to, Integer edgeWeight) {
        Edge edge = new Edge(edgeWeight, from, to);
        spanningTree.push(edge);
    }

    public Edge previousStep() {
        if (spanningTree.empty())
            return null;

        Edge lastEdge = spanningTree.pop();  // достали вершину из остова
        lastEdge.getVertexTo().setVisited(false); // отметили, что не посетили вершину to

        Vertex from = lastEdge.getVertexFrom();
        Vertex to = lastEdge.getVertexTo();

        if (from == null || to == null)
            return null;

        from.forPreviousStep(to);  // отмечаем, что ребро не посещено!
        to.forPreviousStep(from);  // в обе стороны

        return lastEdge;
    }

    public void clearGraph() {    //для очистки данных графа, полностью новый ввод
        graph.clear();
        spanningTree.clear();
    }

    public void restart() {
        for (Vertex vertex : graph.getGraph()) {
            vertex.setVisited(false); // // теперь вершина не посещенная
            Set<HashMap.Entry<Vertex, Edge>> set = vertex.getEdges().entrySet();
            for (HashMap.Entry<Vertex, Edge> me : set)
                me.getValue().setIncluded(false);    // ребра являются не включенными
        }
        spanningTree.clear();
    }

    public void writeToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false))  // файл перезаписывается
        {
            writer.write("Source graph:\n");
            for (Vertex vertex : graph.getGraph()) {
                Set<HashMap.Entry<Vertex, Edge>> set = vertex.getEdges().entrySet(); // словарь ребер вершины
                for (HashMap.Entry<Vertex, Edge> edge : set) {
                    writer.write(vertex.getNumber() +
                            "-" + edge.getKey().getNumber() + " " + edge.getValue().getWeight());
                    writer.write('\n');
                }
            }

            writer.write("Found spanning tree:\n");
            for (Edge res : spanningTree) {
                writer.write(res.getVertexFrom().getNumber() +
                        "-" + res.getVertexTo().getNumber() + " " + res.getWeight());
                writer.append('\n');
            }

            writer.flush();
        } catch (IOException e) {
            log.info("Couldn't write to file: " + e.getMessage());
        }
    }
}

