package spbetu.prim.model;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Graph {
    ArrayList<Vertex> graph; // массив вершин (у каждой вершины номер и словарь ребер)

    public Graph() {
        graph = new ArrayList<>();
    }

    public void clearGraph() {    //для очистки данных графа, полностью новый ввод
        graph.clear();
    }

    public void graphStartAgain() {
        for (Vertex vertex : graph) {
            vertex.setVisited(false); // // теперь вершина не посещенная
            Set<HashMap.Entry<Vertex, Edge>> set = vertex.edges.entrySet();
            for (HashMap.Entry<Vertex, Edge> me : set)
                me.getValue().setIncluded(false);    // ребра являются не включенными
        }
    }

    public Graph cloneGraph() {                                // клонирование графа (на всякий случай)))
        Graph clone = new Graph();
        for (Vertex vertex : graph) {
            Vertex vertexClone = new Vertex(vertex.number);
            clone.graph.add(vertexClone);
        }
        return clone;
    }

    public Edge addNewEdge(int indexVertex1, int indexVertex2, int edge12) { // две вершинки и вес
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

    public void runAlgorithm() {
        if (graph.size() > 0) {
            graph.get(0).setVisited(true);  // посещаем первую вершину
            log.info(graph.get(0).number + " vertex was visited");
        } else
            return;

        while (isDisconnected()) { // пока есть не посещенная вершина (должны в итоге посетить все вершины)
            runAlgorithmByStep();
        }
    }


    public Edge runAlgorithmByStep() {
        if (graph.size() > 0) {
            graph.get(0).setVisited(true);  // посещаем первую вершину
            log.info(graph.get(0).number + " vertex was visited");
        } else
            return null;

        Edge nextMinimumEdge = new Edge(Integer.MAX_VALUE, null, null); // для минимального ребра
        // ?????? как быть с generic
        Vertex nextVertex = graph.get(0);  // следующая вершина, куда перейдем (инициализируем первой, т.к. она посещена в самом начале)

        int nextVertexNumber = 0;

        for (Vertex vertex : graph) { // рассматриваем все вершины графа

            if (vertex.isVisited()) {  // если вершина не посещена
                log.info("Looking at edges of " + vertex.number + " vertex");
                Pair candidate = vertex.getMinimum();  // выбираем вершину с минимальным ребром
                if (candidate.edge.getWeight() < nextMinimumEdge.getWeight()) {
                    nextMinimumEdge = candidate.edge;
                    nextVertex = candidate.vertex;
                    nextVertexNumber = vertex.number;
                }
            }

            try {                                   // здесь таймер для отображения
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        nextMinimumEdge.setIncluded(true);  // ребро включили
        nextVertex.setVisited(true); // вершину посетили
        log.info("In result added edge " + nextVertexNumber + " to " + nextVertex.number + " with weight " + nextMinimumEdge.getWeight());
        log.info(nextVertex.number + " vertex was visited");

        return nextMinimumEdge;
    }


    private boolean isDisconnected() { // возвращает true, если есть не посещенная вершина
        for (Vertex vertex : graph) {
            if (!vertex.isVisited()) {
                return true;
            }
        }
        return false;
    }

    private int indexVertex(int number) {
        for (Vertex vertex : graph) {
            if (vertex.number == number)
                return graph.indexOf(vertex);
        }
        return -1;
    }

    private void checkIndex(int indexVertex) {
        if (indexVertex(indexVertex) < 0) {
            graph.add(new Vertex(indexVertex));
            log.info("Added new vertex with number " + indexVertex);
        }
    }

    void fillGraph() {
        Scanner console = new Scanner(System.in);
        Edge newEdge = null;

        while (true) {
            // но такая штука не актуальна, если вводим через интерфейс
            newEdge = addNewEdgeFromConsole();
            if (newEdge == null) {
                log.info("Mistake of entering!");
                return;
            }
        }
    }

    public Edge addNewEdgeFromConsole() {
        Scanner console = new Scanner(System.in);
        int indexVertex1 = -1;
        int indexVertex2 = -1;
        int edge12 = -1;
        String dash;

        if (console.hasNextInt()) {
            indexVertex1 = console.nextInt();
            dash = console.next();

            if (!dash.equals("-")) {
                log.info("Between vertexes you need to enter \"-\"");
                return null;
            }

            if (console.hasNextInt()) {
                indexVertex2 = console.nextInt();
            } else {
                log.info("Mistake of reading the second vertex");
                return null;
            }

            // System.out.println("Enter the value of edge:");
            if (console.hasNextInt()) {
                edge12 = console.nextInt();
                if (edge12 > 32000)   // костыль
                    return null;
            } else {
                console.next();
                log.info("Wrong weight");
                return null;
            }

            return addNewEdge(indexVertex1, indexVertex2, edge12);
        }
        return null;
    }

}