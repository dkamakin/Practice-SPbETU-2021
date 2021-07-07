package spbetu.prim.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class PrimAlgorithm {
    Graph prim;  // сам граф

    Stack<Edge> spanningTree; // остовное дерево (вершина куда (последняя посещенная) и ребро в нее)

    public PrimAlgorithm(Graph graph) {
        prim = graph;
        spanningTree = new Stack<>();
    }

    public void runAlgorithm() {
        if (prim.graph.size() > 0) {
            prim.graph.get(0).setVisited(true);  // посещаем первую вершину
            System.out.println(prim.graph.get(0).number + " vertex was visited");
        }
        else
            return;

        while (prim.isDisconnected()) { // пока есть не посещенная вершина (должны в итоге посетить все вершины)
            runAlgorithmByStep();
        }
    }

    public Edge runAlgorithmByStep() {
        if (prim.graph.size() > 0) {
            prim.graph.get(0).setVisited(true);  // посещаем первую вершину
            System.out.println(prim.graph.get(0).number + " vertex was visited");
        }
        else
            return null;

        Edge nextMinimumEdge = new Edge(Integer.MAX_VALUE, null, null); // для минимального ребра
        // ?????? как быть с generic
        Vertex nextVertex = prim.graph.get(0);  // следующая вершина, куда перейдем (инициализируем первой, т.к. она посещена в самом начале)

        Vertex thisVertex = null;

        for (Vertex vertex : prim.graph) { // рассматриваем все вершины графа

            if (vertex.isVisited()) {  // если вершина посещена

                System.out.println("Looking at edges of " + vertex.number + " vertex");
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

        System.out.println("In result added edge " + nextMinimumEdge.getVertexFrom().number + "-" +  nextMinimumEdge.getVertexTo().number + " with weight " + nextMinimumEdge.getWeight());
        System.out.println(nextVertex.number + " vertex was visited");

        return nextMinimumEdge;
    }

    public void addEdgeToSpanningTree(Vertex from, Vertex to, Integer edgeWeight) {
        Edge edge = new Edge(edgeWeight, from, to);
        spanningTree.push(edge);
    }

    public Edge previousStep() {
        Edge lastEdge = spanningTree.pop();  // достали вершину из остова
        lastEdge.getVertexTo().setVisited(false); // отметили, что не посетили вершину to

        Vertex from = lastEdge.getVertexFrom();
        Vertex to = lastEdge.getVertexTo();
        from.forPreviousStep(to);  // отмечаем, что ребро не посещено!
        to.forPreviousStep(from);  // в обе стороны

        return lastEdge;
    }

    public void clearGraph() {    //для очистки данных графа, полностью новый ввод
        prim.graph.clear();
        spanningTree.clear();
    }

    public void graphStartAgain() {
        for (Vertex vertex: prim.graph) {
            vertex.setVisited(false); // // теперь вершина не посещенная
            Set<HashMap.Entry<Vertex, Edge>> set = vertex.edges.entrySet();
            for (HashMap.Entry<Vertex, Edge> me : set)
                me.getValue().setIncluded(false);    // ребра являются не включенными
        }
        spanningTree.clear();
    }

    public void writeToFile(String fileName) {
        try(FileWriter writer = new FileWriter(fileName, false))  // файл перезаписывается
        {
            writer.write("Source graph:\n");
            for (Vertex vertex: prim.graph) {
                Set<HashMap.Entry<Vertex, Edge>> set = vertex.edges.entrySet(); // словарь ребер вершины
                for (HashMap.Entry<Vertex, Edge> edge : set) {
                    writer.write(vertex.number + "-" + edge.getKey().number + " " + edge.getValue().getWeight());
                    writer.write('\n');
                }
            }

            writer.write("Found spanning tree:\n");
            for (Edge res: spanningTree) {
                writer.write(res.getVertexFrom().number + "-" + res.getVertexTo().number + " " + res.getWeight());
                writer.append('\n');
            }

            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}

