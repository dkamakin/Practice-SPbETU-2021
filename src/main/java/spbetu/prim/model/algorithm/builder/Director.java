package spbetu.prim.model.algorithm.builder;

public class Director {
    public void constructMaxEdge(MaxEdgeBuilder builder) {
        builder.setVertexFrom(null);
        builder.setVertexTo(null);
        builder.setWeight(Double.MAX_VALUE);
    }
}

