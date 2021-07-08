package spbetu.prim.model.graph;

public class Edge { // вес может быть числом или Character
    private final Integer weight;
    private final Vertex vertexFrom;
    private final Vertex vertexTo;
    private boolean included = false;

    public Edge(Integer weight, Vertex from, Vertex to) {
        this.weight = weight;
        this.vertexFrom = from;
        this.vertexTo = to;
    }

    public Integer getWeight() {
        return weight;  //возвращает вес-число
    }

    boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean flag) {
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