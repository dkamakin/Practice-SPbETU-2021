package spbetu.prim.model.graph.builder.pattern;

public class Director {
    public void constructMaxEdge(MaxEdgeBuilder builder) {
        builder.setVertexFrom(null);
        builder.setVertexTo(null);
        builder.setWeight(Double.MAX_VALUE);
    }
}

