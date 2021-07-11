package spbetu.prim.model.graph.builder.pattern;

import spbetu.prim.model.graph.Vertex;
import spbetu.prim.model.graph.Edge;

public class MaxEdgeBuilder <T> implements Builder{
    private T weight;
    private Vertex from;
    private Vertex to;

    @Override
    public void setVertexFrom(Vertex from) {
        this.from = from;
    };

    @Override
    public void setVertexTo(Vertex to) {
        this.to = to;
    };

    @Override
    public <V> void setWeight(V weight) {
        this.weight = (T)weight;
    };

    public Edge<T> getResult() {
        return new Edge<T>(weight, from, to);
    }
}
