package spbetu.prim.model;

public class Edge  { // вес может быть числом или Character
    private Integer weight;
    private boolean included = false;

    private Vertex vertexFrom;
    private Vertex vertexTo;

    public Edge(Integer w, Vertex from, Vertex to) {
        weight = w;
        vertexFrom = from;
        vertexTo = to;
    }

    Integer getWeight() {
        return weight;  //возвращает вес-число
    }

    boolean isIncluded() {
        return included;
    }

    void setIncluded(boolean flag) {
        included = flag;
    }

    public Vertex getVertexFrom() {
        return vertexFrom;
    }

    public Vertex getVertexTo() {
        return vertexTo;
    }


    public Vertex getNextVertex() {  // т.к. граф ненаправленный, возвращает ту вершину, в которой мы еще не были
        if (vertexFrom.isVisited() && !vertexTo.isVisited()) // если были в vertexFrom, но не были в vertexTo
            return vertexTo;
        else if (vertexTo.isVisited() && !vertexFrom.isVisited()) // если были в vertexTo, но не были в vertexFrom
            return vertexFrom;
        return null;
    }
}