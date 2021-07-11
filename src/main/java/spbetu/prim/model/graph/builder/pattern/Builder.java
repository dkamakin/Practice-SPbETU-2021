package spbetu.prim.model.graph.builder.pattern;

import spbetu.prim.model.graph.Vertex;

public interface Builder {
    <T> void setWeight(T weight);

    void setVertexFrom(Vertex from);

    void setVertexTo(Vertex to);
}
