package ro.ucv.ace.graph.model;

/**
 * Created by Geo on 12.11.2016.
 */
public class Edge {

    private Vertex vertex1;

    private Vertex vertex2;

    private double weight;

    public Edge(Vertex vertex, Vertex vertex1, double w) {
        this.vertex1 = vertex;
        this.vertex2 = vertex1;
        this.weight = w;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public void setVertex1(Vertex vertex1) {
        this.vertex1 = vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public void setVertex2(Vertex vertex2) {
        this.vertex2 = vertex2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
