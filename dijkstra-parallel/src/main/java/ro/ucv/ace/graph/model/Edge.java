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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Edge edge = (Edge) o;

        if (Double.compare(edge.weight, weight) != 0) {
            return false;
        }
        if (vertex1 != null ? !vertex1.equals(edge.vertex1) : edge.vertex1 != null) {
            return false;
        }
        return vertex2 != null ? vertex2.equals(edge.vertex2) : edge.vertex2 == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = vertex1 != null ? vertex1.hashCode() : 0;
        result = 31 * result + (vertex2 != null ? vertex2.hashCode() : 0);
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
