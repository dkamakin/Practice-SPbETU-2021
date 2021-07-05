package spbetu.prim.model;

public class Edge { // вес может быть числом или Character
    private Integer weight;
    private boolean included = false;

    private Vertex vertexFrom;
    private Vertex vertexTo;

    Edge(Integer w, Vertex from, Vertex to) {

        weight = w;
        vertexFrom = from;
        vertexTo = to;
    }

    public Integer getWeight() {
        return weight;  //возвращает вес-число
    }

    boolean isIncluded() {
        return included;
    }

    void setIncluded(boolean flag) {
        included = flag;
    }

    public Vertex vertexTo() {
        if (!vertexTo.isVisited())
            return vertexTo;
        return null;
    }

    public Vertex getVertexFrom() {
        return vertexFrom;
    }

    public Vertex getVertexTo() {
        return vertexTo;
    }
}