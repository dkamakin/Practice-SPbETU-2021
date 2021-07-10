package spbetu.prim.cli.viewmodel;

public class EdgeView {

    private int vertexFrom;
    private int vertexTo;
    private double weight;

    public EdgeView() {
    }

    public EdgeView(int vertexFrom, int vertexTo, double weight) {
        this.vertexFrom = vertexFrom;
        this.vertexTo = vertexTo;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getVertexTo() {
        return vertexTo;
    }

    public void setVertexTo(int vertexTo) {
        this.vertexTo = vertexTo;
    }

    public int getVertexFrom() {
        return vertexFrom;
    }

    public void setVertexFrom(int vertexFrom) {
        this.vertexFrom = vertexFrom;
    }
}
