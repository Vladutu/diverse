package ro.ucv.ace.graph;

/**
 * Created by Geo on 05.11.2016.
 */
public class Edge {

    private Vertex source;

    private Vertex destination;

    private Double weight;

    public Edge(Vertex source, Vertex destination, Double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public Double getWeight() {
        return weight;
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

        if (source != null ? !source.equals(edge.source) : edge.source != null) {
            return false;
        }
        if (destination != null ? !destination.equals(edge.destination) : edge.destination != null) {
            return false;
        }
        return weight != null ? weight.equals(edge.weight) : edge.weight == null;

    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                ", weight=" + weight +
                '}';
    }
}
