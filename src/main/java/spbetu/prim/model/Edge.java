package spbetu.prim.model;

class Edge  { // вес может быть числом или Character
    private Integer weight;
    private boolean included = false;

    private Vertex vertexFrom;
    private Vertex vertexTo;

    Edge(Integer w, Vertex from, Vertex to) {

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

    Vertex getVertexTo() {
        if (!vertexTo.isVisited())
            return vertexTo;
        return null;
    }
}