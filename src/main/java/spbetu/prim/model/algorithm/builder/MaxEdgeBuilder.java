package spbetu.prim.model.algorithm.builder;

import spbetu.prim.model.algorithm.builder.Builder;
import spbetu.prim.model.graph.Edge;
import spbetu.prim.model.graph.Vertex;

public class MaxEdgeBuilder<T> implements Builder {
    private T weight;
    private Vertex from;
    private Vertex to;

    @Override
    public void setVertexFrom(Vertex from) {
        this.from = from;
    }

    @Override
    public void setVertexTo(Vertex to) {
        this.to = to;
    }

    @Override
    public <V> void setWeight(V weight) {
        this.weight = (T) weight;
    }

    public Edge<T> getResult() {
        return new Edge<>(weight, from, to);
    }
}
