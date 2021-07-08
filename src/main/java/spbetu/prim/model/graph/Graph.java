package spbetu.prim.model.graph;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
public class Graph {

    private final List<Vertex> vertices; // массив вершин (у каждой вершины номер и словарь ребер)

    public Graph() {
        vertices = new ArrayList<>();
    }

    public List<Edge<Double>> getEdges() {
        List<Edge<Double>> result = new ArrayList<>();

        for (Vertex vertex : vertices) {
            HashMap<Vertex, Edge<Double>> edges = vertex.getEdges();
            Set<HashMap.Entry<Vertex, Edge<Double>>> set = edges.entrySet();

            for (HashMap.Entry<Vertex, Edge<Double>> elem : set) {
                if (!result.contains(elem.getValue()))
                    result.add(elem.getValue());
            }
        }

        return result;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void clear() {
        vertices.clear();
    }

    public int getSize() {
        return vertices.size();
    }

    public Vertex getVertex(int index) {
        return vertices.get(index);
    }

    public Graph cloneGraph() {                                // клонирование графа (на всякий случай)))
        Graph clone = new Graph();

        for (Vertex vertex : vertices) {
            Vertex vertexClone = new Vertex(vertex.getNumber());
            clone.vertices.add(vertexClone);
        }

        return clone;
    }

    public void deleteVertex(int indexVertex) {
        for (Vertex vertex : vertices) {
            vertex.deleteEdgeFromDictionary(indexVertex);
        }
    }

    public void deleteEdge(int indexVertexFrom, int indexVertexTo) {
        for (Vertex vertex : vertices) { // пробегаемся по вершинам графа
            if (vertex.getNumber() == indexVertexFrom) {  // если номер искомой вершины найден
                vertex.deleteEdgeFromDictionary(indexVertexTo);
            } else if (vertex.getNumber() == indexVertexTo) {  // и в обратную сторону
                vertex.deleteEdgeFromDictionary(indexVertexFrom);
            }
        }
    }

    public Edge<Double> addNewEdge(int indexVertex1, int indexVertex2, Double edge12) { // две вершины и вес
        log.info("Adding edge from {} to {} with weight {}", indexVertex1, indexVertex2, edge12);

        checkIndex(indexVertex1); // добавляем вершину в граф, если она встречается первый раз
        checkIndex(indexVertex2);

        Vertex to = vertices.get(indexVertex(indexVertex1));
        Vertex from = vertices.get(indexVertex(indexVertex2));
        Edge<Double> edge = new Edge(edge12, to, from); // в принципе, не важно откуда куда, так как граф не направленный
        to.vertexAddEdge(from, edge);
        from.vertexAddEdge(to, edge);

        log.info("Created edge");
        return edge;
    }


    public boolean isDisconnected() { // возвращает true, если есть не посещенная вершина
        for (Vertex vertex : vertices)
            if (!vertex.isVisited())
                return true;

        return false;
    }

    private int indexVertex(int number) {
        for (Vertex vertex : vertices)
            if (vertex.getNumber() == number)
                return vertices.indexOf(vertex);

        return -1;
    }

    private void checkIndex(int indexVertex) {
        if (indexVertex(indexVertex) < 0) {
            vertices.add(new Vertex(indexVertex));
            log.info("Added new vertex with number " + indexVertex);
        }
    }

    public int getCountOfEdges() {
        int count = 0;
        for(Vertex vertice : vertices){
            count += vertice.getEdges().size();
        }
        return count;
    }

    public int size(){
        return vertices.size();
    }
}
