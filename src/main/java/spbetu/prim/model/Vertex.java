package spbetu.prim.model;

import java.util.HashMap;
import java.util.Set;

class Pair {  // класс пары вершина-ребро
    Vertex vertex;
    Edge edge;

    Pair(Vertex v, Edge e) {
        vertex = v;
        edge = e;
    }
}

class Vertex {  // класс ребра
    int number;
    HashMap<Vertex, Edge> edges;
    private boolean visited;

    Vertex(int number) {
        this.number = number;
        edges = new HashMap<>();
        visited = false;
    }

    void setVisited(boolean flag) {
        visited = flag;
    }

    boolean isVisited() {
        return visited;
    }

    void vertexAddEdge(Vertex nextVertex, Edge edge) { // функция добавления ребра
        edges.put(nextVertex, edge);
    }

    public Pair getMinimum() {  // возвращает пару вершина-ребро (
        Edge nextMinimum = new Edge(Integer.MAX_VALUE, null, null);  /// ???? generic
        Vertex nextVertex = this;

        Set<HashMap.Entry<Vertex, Edge>> set = edges.entrySet();
        for (HashMap.Entry<Vertex, Edge> me : set) {
            if (!me.getKey().isVisited() && (!me.getValue().isIncluded())) {
                if (me.getValue().getWeight() < nextMinimum.getWeight()) {
                    nextMinimum = me.getValue();
                    nextVertex = me.getKey();
                }
            }
        }

        return new Pair(nextVertex, nextMinimum);
    }
}
